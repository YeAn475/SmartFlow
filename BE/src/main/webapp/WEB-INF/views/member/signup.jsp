<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SmartFlow - 회원가입</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-4 card p-4 shadow">
            <h3 class="text-center mb-4">회원가입</h3>
            <div class="mb-3">
                <label class="form-label">이메일</label>
                <input type="email" id="email" class="form-control" placeholder="example@test.com">
            </div>
            <div class="mb-3">
                <label class="form-label">이름</label>
                <input type="text" id="name" class="form-control">
            </div>
            <div class="mb-3">
                <label class="form-label">비밀번호</label>
                <input type="password" id="password" class="form-control">
            </div>
            <div class="mb-3">
                <label class="form-label">비밀번호 확인</label>
                <input type="password" id="passwordConfirm" class="form-control">
            </div>
            <button class="btn btn-primary w-100" onclick="handleSignUp()">가입하기</button>
            <div class="text-center mt-3">
                <a href="/login" class="text-decoration-none">이미 계정이 있나요? 로그인</a>
            </div>
        </div>
    </div>
</div>

<script>
    async function handleSignUp() {
        const data = {
            email: document.getElementById('email').value,
            name: document.getElementById('name').value,
            password: document.getElementById('password').value,
            passwordConfirm: document.getElementById('passwordConfirm').value
        };

        const response = await fetch('/api/auth/signup', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            alert('회원가입 성공! 로그인 페이지로 이동합니다.');
            location.href = '/login';
        } else {
            const err = await response.text();
            alert('가입 실패: ' + err);
        }
    }
</script>
</body>
</html>