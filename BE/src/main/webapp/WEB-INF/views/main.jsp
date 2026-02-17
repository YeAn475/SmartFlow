<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SmartFlow - 메인 대시보드</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body { background-color: #f8f9fa; }
        .navbar { margin-bottom: 30px; }
        #alarmList { width: 320px; max-height: 450px; overflow-y: auto; }
        .alarm-item { cursor: pointer; transition: background 0.2s; border-bottom: 1px solid #f1f1f1; }
        .alarm-item:hover { background-color: #f8f9fa; }
        .unread { border-left: 4px solid #0d6efd; background-color: #f0f7ff; }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand h1 mb-0" href="/main">SmartFlow</a>

        <div class="d-flex align-items-center">
            <div class="dropdown me-3">
                <a href="#" class="text-white position-relative" id="alarmDropdown" data-bs-toggle="dropdown" aria-expanded="false" onclick="loadAlarms()">
                    <i class="bi bi-bell-fill fs-4"></i>
                    <span id="alarmBadge" class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger d-none">
                        0
                    </span>
                </a>
                <ul class="dropdown-menu dropdown-menu-end shadow border-0" id="alarmList" aria-labelledby="alarmDropdown">
                    <li class="dropdown-header py-3">최신 알림</li>
                    <li><hr class="dropdown-divider m-0"></li>
                    <div id="alarmContainer">
                        <li class="text-center p-4 text-muted">새로운 알림이 없습니다.</li>
                    </div>
                </ul>
            </div>

            <span class="text-white me-3"><b id="userNameDisplay"></b>님</span>
            <button class="btn btn-outline-danger btn-sm" onclick="handleLogout()">로그아웃</button>
        </div>
    </div>
</nav>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="card p-5 shadow-sm border-0">
                <h2>환영합니다, <span id="contentName" class="text-primary"></span>님! 👋</h2>
                <p class="lead mt-3 text-secondary">SmartFlow 근태관리 시스템에 접속하셨습니다. 오늘 하루도 파이팅하세요!</p>
                <hr class="my-4">
                <div class="d-flex gap-3">
                    <button class="btn btn-primary px-4 py-2" type="button">
                        <i class="bi bi-calendar-check me-2"></i> 내 연차 조회
                    </button>
                    <button class="btn btn-success px-4 py-2" type="button">
                        <i class="bi bi-send-fill me-2"></i> 결재 요청
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    document.addEventListener('DOMContentLoaded', () => {
        const userName = localStorage.getItem('userName');
        const accessToken = localStorage.getItem('accessToken');

        if (!accessToken) {
            alert('로그인이 필요한 페이지입니다.');
            location.href = '/login';
            return;
        }

        document.getElementById('userNameDisplay').innerText = userName;
        document.getElementById('contentName').innerText = userName;

        // 페이지 로드 시 알림 개수 체크
        updateAlarmBadge();
    });

    // 1. 읽지 않은 알람 개수 가져오기
    async function updateAlarmBadge() {
        try {
            const response = await fetch('/api/alarms/unread-count', {
                headers: { 'Authorization': 'Bearer ' + localStorage.getItem('accessToken') }
            });
            if (response.ok) {
                const count = await response.json();
                const badge = document.getElementById('alarmBadge');
                if (count > 0) {
                    badge.innerText = count;
                    badge.classList.remove('d-none');
                }
            }
        } catch (e) { console.error("알림 배지 업데이트 실패", e); }
    }

    // 2. 알람 목록 가져오기
    async function loadAlarms() {
        try {
            const response = await fetch('/api/alarms', {
                headers: { 'Authorization': 'Bearer ' + localStorage.getItem('accessToken') }
            });
            const alarms = await response.json();
            const container = document.getElementById('alarmContainer');

            if (!alarms || alarms.length === 0) {
                container.innerHTML = '<li class="text-center p-4 text-muted">새로운 알림이 없습니다.</li>';
                return;
            }

            container.innerHTML = alarms.map(alarm => `
                <li class="alarm-item p-3 \${alarm.read ? '' : 'unread'}">
                    <div class="d-flex justify-content-between align-items-center">
                        <span class="badge bg-secondary mb-1" style="font-size: 0.65rem;">\${alarm.type}</span>
                        <small class="text-muted" style="font-size: 0.7rem;">\${formatDate(alarm.createdAt)}</small>
                    </div>
                    <div class="fw-bold" style="font-size: 0.9rem;">\${alarm.title}</div>
                    <div class="text-muted text-truncate" style="font-size: 0.8rem;">\${alarm.content || ''}</div>
                </li>
            `).join('');
        } catch (e) { console.error("알림 로드 실패", e); }
    }

    function formatDate(dateStr) {
        if(!dateStr) return '';
        const date = new Date(dateStr);
        return `\${date.getMonth()+1}/\${date.getDate()} \${date.getHours()}:\${date.getMinutes()}`;
    }

    async function handleLogout() {
        const refreshToken = localStorage.getItem('refreshToken');
        try {
            await fetch('/api/auth/logout', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ refreshToken: refreshToken })
            });
        } finally {
            localStorage.clear();
            alert('로그아웃 되었습니다.');
            location.href = '/login';
        }
    }
</script>
</body>
</html>