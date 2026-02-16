<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SmartFlow - 로그인</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; }
        .login-container { margin-top: 100px; max-width: 400px; }
    </style>
</head>
<body>
<div class="container login-container">
    <div class="card shadow p-4">
        <h3 class="text-center mb-4">SmartFlow 로그인</h3>
        <div class="mb-3">
            <label for="email" class="form-label">이메일</label>
            <input type="email" id="email" class="form-control" placeholder="example@test.com">
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">비밀번호</label>
            <input type="password" id="password" class="form-control" placeholder="비밀번호를 입력하세요">
        </div>
        <button class="btn btn-primary w-100" onclick="handleLogin()">로그인</button>
        <div class="text-center mt-3">
            <a href="/signup" class="text-decoration-none">계정이 없으신가요? 회원가입</a>
        </div>
    </div>
</div>

<script>
    async function handleLogin() {
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        if(!email || !password) {
            alert("이메일과 비밀번호를 입력해주세요.");
            return;
        }

        const data = { email, password };

        try {
            const response = await fetch('/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });

            if (response.ok) {
                const result = await response.json();
                localStorage.setItem('accessToken', result.accessToken);
                localStorage.setItem('refreshToken', result.refreshToken);
                localStorage.setItem('userName', result.name);

                alert(result.name + '님 환영합니다!');
                location.href = '/main';
            } else {
                alert('로그인 정보가 일치하지 않습니다.');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('서버 통신 오류가 발생했습니다.');
        }
    }
</script>
</body>
</html>