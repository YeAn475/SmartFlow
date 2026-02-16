<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SmartFlow - 메인 대시보드</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<nav class="navbar navbar-dark bg-dark">
    <div class="container-fluid">
        <span class="navbar-brand mb-0 h1">SmartFlow</span>
        <div class="d-flex">
            <span class="text-white me-3 mt-1"><b id="userNameDisplay"></b>님 환영합니다.</span>
            <button class="btn btn-outline-danger btn-sm" onclick="handleLogout()">로그아웃</button>
        </div>
    </div>
</nav>

<div class="container mt-5">
    <div class="row">
        <div class="col-md-12">
            <div class="card p-5 shadow-sm">
                <h2>환영합니다, <span id="contentName"></span>님!</h2>
                <p class="lead">SmartFlow 근태관리 시스템에 접속하셨습니다.</p>
                <hr>
                <div class="d-grid gap-2 d-md-block">
                    <button class="btn btn-primary" type="button">내 연차 조회</button>
                    <button class="btn btn-success" type="button">결재 요청</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    // 1. 페이지 로드 시 토큰 확인 및 이름 표시
    document.addEventListener('DOMContentLoaded', () => {
        const userName = localStorage.getItem('userName');
        const accessToken = localStorage.getItem('accessToken');

        // 토큰이 없으면 로그인 페이지로 튕겨내기
        if (!accessToken) {
            alert('로그인이 필요한 페이지입니다.');
            location.href = '/login';
            return;
        }

        document.getElementById('userNameDisplay').innerText = userName;
        document.getElementById('contentName').innerText = userName;
    });

    // 2. 로그아웃 함수
    async function handleLogout() {
        const refreshToken = localStorage.getItem('refreshToken');

        // 서버에 로그아웃 요청 (필요 시)
        try {
            await fetch('/api/auth/logout', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ refreshToken: refreshToken })
            });
        } catch (error) {
            console.error('로그아웃 요청 중 오류 발생:', error);
        } finally {
            // 로컬 스토리지 비우고 로그인 페이지로 이동
            localStorage.clear();
            alert('로그아웃 되었습니다.');
            location.href = '/login';
        }
    }
</script>
</body>
</html>