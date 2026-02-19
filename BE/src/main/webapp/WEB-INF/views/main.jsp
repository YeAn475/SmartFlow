<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SmartFlow - 메인 대시보드</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        :root { --sidebar-width: 240px; --friend-list-width: 280px; }
        body { background-color: #f0f2f5; font-family: 'Pretendard', sans-serif; overflow-x: hidden; }
        
        /* 좌측 사이드바 */
        .sidebar { 
            width: var(--sidebar-width); height: 100vh; position: fixed; 
            top: 0; left: 0; background: #1e293b; color: white; padding-top: 20px; z-index: 1000;
        }
        .sidebar .nav-link { color: #cbd5e1; padding: 12px 20px; border-radius: 8px; margin: 4px 15px; text-decoration: none; display: block; }
        .sidebar .nav-link:hover, .sidebar .nav-link.active { background: #334155; color: white; }

        /* 중앙 메인 컨텐츠 */
        .main-content { 
            margin-left: var(--sidebar-width); 
            margin-right: var(--friend-list-width); 
            padding: 30px; min-height: 100vh; 
        }

        /* 우측 친구 목록 */
        .friend-sidebar { 
            width: var(--friend-list-width); height: 100vh; position: fixed; 
            top: 0; right: 0; background: white; border-left: 1px solid #e2e8f0; padding: 20px; z-index: 1000;
        }
        
        /* 알람 드롭다운 커스텀 */
        #alarmList { width: 350px; max-height: 500px; overflow-y: auto; border-radius: 12px; }
        .alarm-item { cursor: pointer; transition: background 0.2s; border-bottom: 1px solid #f1f1f1; list-style: none; }
        .unread { border-left: 4px solid #0d6efd; background-color: #f0f7ff; }

        .glass-card { background: white; border: none; border-radius: 16px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.1); }
        .status-dot { width: 10px; height: 10px; border-radius: 50%; display: inline-block; background: #22c55e; }

        /* 검색 결과 드롭다운 스타일 추가 */
        #searchResultWrapper { position: relative; }
        #searchDropdown { 
            position: absolute; top: 100%; left: 0; right: 0; 
            background: white; border-radius: 8px; z-index: 1100; 
            max-height: 300px; overflow-y: auto; display: none;
        }
    </style>
</head>
<body>

<div class="sidebar shadow">
    <div class="px-4 mb-4"><h4 class="fw-bold text-white">SmartFlow</h4></div>
    <nav class="nav flex-column">
        <a class="nav-link active" href="/main"><i class="bi bi-speedometer2 me-2"></i> 대시보드</a>
        <a class="nav-link" href="#"><i class="bi bi-calendar-check me-2"></i> 연차 신청</a>
        <a class="nav-link" href="#"><i class="bi bi-chat-dots me-2"></i> 메신저</a>
        <a class="nav-link" href="#"><i class="bi bi-people me-2"></i> 인사 관리</a>
        <hr class="mx-3 opacity-20">
        <a class="nav-link text-danger" href="javascript:void(0)" onclick="handleLogout()">
            <i class="bi bi-box-arrow-right me-2"></i> 로그아웃
        </a>
    </nav>
</div>

<div class="main-content">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h4 class="fw-bold mb-0">워크스페이스</h4>
        <div class="d-flex align-items-center">
            <div class="dropdown me-3">
                <a href="#" class="text-dark position-relative" id="alarmDropdown" data-bs-toggle="dropdown" aria-expanded="false" onclick="loadAlarms()">
                    <i class="bi bi-bell fs-4"></i>
                    <span id="alarmBadge" class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger d-none">0</span>
                </a>
                <ul class="dropdown-menu dropdown-menu-end shadow border-0 p-0" id="alarmList">
                    <li class="p-3 border-bottom d-flex justify-content-between">
                        <span class="fw-bold">최신 알림</span>
                    </li>
                    <div id="alarmContainer">
                        <li class="text-center p-4 text-muted">새로운 알림이 없습니다.</li>
                    </div>
                </ul>
            </div>
            <span class="fw-bold"><span id="userNameDisplay"></span>님</span>
        </div>
    </div>

    <div class="card glass-card p-5 mb-4">
        <h2>환영합니다, <span id="contentName" class="text-primary"></span>님! 👋</h2>
        <p class="lead mt-3 text-secondary">SmartFlow 근태관리 시스템에 접속하셨습니다. 효율적인 하루를 시작해 보세요.</p>
        <hr class="my-4 text-muted">
        <div class="d-flex gap-3">
            <button class="btn btn-primary px-4 py-2 rounded-pill shadow-sm" type="button">
                <i class="bi bi-calendar-check me-2"></i> 연차 신청하기
            </button>
            <button class="btn btn-white border px-4 py-2 rounded-pill shadow-sm" type="button">
                <i class="bi bi-send-fill me-2"></i> 결재 요청 내역
            </button>
        </div>
    </div>
</div>

<div class="friend-sidebar shadow-sm">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h6 class="fw-bold mb-0">동료 주소록</h6>
        <button class="btn btn-sm btn-outline-primary rounded-circle" onclick="document.getElementById('colleagueSearchInput').focus()">
            <i class="bi bi-plus"></i>
        </button>
    </div>
    
    <div class="input-group input-group-sm mb-3" id="searchResultWrapper">
        <span class="input-group-text bg-light border-0"><i class="bi bi-search"></i></span>
        <input type="text" id="colleagueSearchInput" class="form-control bg-light border-0" placeholder="동료 검색(이름/부서)..." onkeyup="searchColleagues(this.value)">
        <ul id="searchDropdown" class="list-group shadow border-0 position-absolute w-100">
            </ul>
    </div>

    <div id="friendListContainer">
        <li class="text-center p-4 text-muted small" style="list-style: none;">동료를 불러오는 중...</li>
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

        document.getElementById('userNameDisplay').innerText = userName || '사용자';
        document.getElementById('contentName').innerText = userName || '사용자';

        updateAlarmBadge();
        loadColleagueList(); // 페이지 로드 시 동료 목록 불러오기 추가
    });

    /* ================= [1. 알람 기능 로직] ================= */
    async function updateAlarmBadge() {
        try {
            const response = await fetch('/api/alarm/unread-count', {
                headers: { 'Authorization': 'Bearer ' + localStorage.getItem('accessToken') }
            });
            if (response.ok) {
                const count = await response.json();
                const badge = document.getElementById('alarmBadge');
                if (count > 0) {
                    badge.innerText = count;
                    badge.classList.remove('d-none');
                } else {
                    badge.classList.add('d-none');
                }
            }
        } catch (e) { console.error("알림 배지 업데이트 실패", e); }
    }

    async function loadAlarms() {
        try {
            const response = await fetch('/api/alarm/list', {
                headers: { 'Authorization': 'Bearer ' + localStorage.getItem('accessToken') }
            });
            const alarms = await response.json();
            const container = document.getElementById('alarmContainer');

            if (!alarms || alarms.length === 0) {
                container.innerHTML = '<li class="text-center p-4 text-muted">새로운 알림이 없습니다.</li>';
                return;
            }

            container.innerHTML = alarms.map(alarm => `
                <li class="alarm-item p-3 \${alarm.read ? '' : 'unread'}" onclick="markAsRead(\${alarm.alarmId})">
                    <div class="d-flex justify-content-between align-items-center">
                        <span class="badge bg-secondary mb-1" style="font-size: 0.65rem;">
                            \${alarm.senderName ? alarm.senderName : alarm.type}
                        </span>
                        <small class="text-muted" style="font-size: 0.7rem;">\${formatDate(alarm.createdAt)}</small>
                    </div>
                    <div class="fw-bold" style="font-size: 0.85rem;">\${alarm.title}</div>
                    <div class="text-muted text-truncate" style="font-size: 0.8rem;">\${alarm.content || ''}</div>
                </li>
            `).join('');
        } catch (e) { console.error("알림 로드 실패", e); }
    }

    async function markAsRead(alarmId) {
        try {
            const response = await fetch(`/api/alarm/\${alarmId}/read`, {
                method: 'PATCH',
                headers: { 'Authorization': 'Bearer ' + localStorage.getItem('accessToken') }
            });
            if (response.ok) {
                updateAlarmBadge();
                loadAlarms();
            }
        } catch (e) { console.error("읽음 처리 실패", e); }
    }

    /* ================= [2. 동료 관리 기능 로직 (추가)] ================= */

    // 내 동료 목록 불러오기 (GET /api/colleague/list)
    async function loadColleagueList() {
        try {
            const response = await fetch('/api/colleague/list', {
                headers: { 'Authorization': 'Bearer ' + localStorage.getItem('accessToken') }
            });
            const colleagues = await response.json();
            const container = document.getElementById('friendListContainer');

            if (!colleagues || colleagues.length === 0) {
                container.innerHTML = '<div class="text-center p-4 text-muted small">등록된 동료가 없습니다.</div>';
                return;
            }

            container.innerHTML = colleagues.map(c => `
                <div class="d-flex align-items-center p-2 mb-2 rounded hover-light border-bottom shadow-sm">
                    <div class="position-relative me-3">
                        <img src="\${c.profileImg || 'https://ui-avatars.com/api/?name=' + c.name}" class="rounded-circle" width="35">
                        <span class="status-dot position-absolute bottom-0 end-0 border border-white border-2"></span>
                    </div>
                    <div class="flex-grow-1">
                        <div class="fw-bold small">\${c.name} \${c.position || ''}</div>
                        <div class="text-muted small" style="font-size: 10px;">\${c.department}</div>
                    </div>
                    <button class="btn btn-sm text-danger" onclick="removeColleague(\${c.memberId})"><i class="bi bi-x"></i></button>
                </div>
            `).join('');
        } catch (e) { console.error("동료 목록 로드 실패", e); }
    }

    // 동료 검색 (GET /api/colleague/search)
    let searchTimeout;
    function searchColleagues(query) {
        clearTimeout(searchTimeout);
        const dropdown = document.getElementById('searchDropdown');
        
        if (!query.trim()) {
            dropdown.style.display = 'none';
            return;
        }

        searchTimeout = setTimeout(async () => {
            try {
                const response = await fetch(`/api/colleague/search?query=\${encodeURIComponent(query)}`, {
                    headers: { 'Authorization': 'Bearer ' + localStorage.getItem('accessToken') }
                });
                const results = await response.json();

                if (results.length > 0) {
                    dropdown.innerHTML = results.map(r => `
                        <li class="list-group-item d-flex justify-content-between align-items-center py-2 shadow-sm">
                            <div>
                                <div class="fw-bold small">\${r.name} (\${r.department})</div>
                                <div class="text-muted" style="font-size: 0.7rem;">\${r.position || ''}</div>
                            </div>
                            <button class="btn btn-sm btn-primary" onclick="addColleague(\${r.memberId})"><i class="bi bi-plus"></i></button>
                        </li>
                    `).join('');
                    dropdown.style.display = 'block';
                } else {
                    dropdown.innerHTML = '<li class="list-group-item small text-muted">검색 결과가 없습니다.</li>';
                    dropdown.style.display = 'block';
                }
            } catch (e) { console.error("검색 실패", e); }
        }, 300);
    }

    // 동료 추가 (POST /api/colleague/add/{id})
    async function addColleague(colleagueId) {
        try {
            const response = await fetch(`/api/colleague/add/\${colleagueId}`, {
                method: 'POST',
                headers: { 'Authorization': 'Bearer ' + localStorage.getItem('accessToken') }
            });

            if (response.ok) {
                alert("동료가 추가되었습니다.");
                document.getElementById('colleagueSearchInput').value = '';
                document.getElementById('searchDropdown').style.display = 'none';
                loadColleagueList(); // 목록 갱신
            } else {
                const msg = await response.text();
                alert(msg);
            }
        } catch (e) { console.error("동료 추가 실패", e); }
    }

    // 동료 삭제 (DELETE /api/colleague/remove/{id})
    async function removeColleague(colleagueId) {
        if(!confirm("주소록에서 삭제하시겠습니까?")) return;
        try {
            const response = await fetch(`/api/colleague/remove/\${colleagueId}`, {
                method: 'DELETE',
                headers: { 'Authorization': 'Bearer ' + localStorage.getItem('accessToken') }
            });
            if (response.ok) {
                loadColleagueList();
            }
        } catch (e) { console.error("동료 삭제 실패", e); }
    }

    /* ================= [3. 공통 유틸리티 기능] ================= */

    function formatDate(dateStr) {
        if(!dateStr) return '';
        const date = new Date(dateStr);
        return `\${date.getMonth()+1}/\${date.getDate()} \${String(date.getHours()).padStart(2, '0')}:\${String(date.getMinutes()).padStart(2, '0')}`;
    }

    async function handleLogout() {
        if (!confirm("로그아웃 하시겠습니까?")) return;
        const refreshToken = localStorage.getItem('refreshToken');
        try {
            await fetch('/api/auth/logout', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ refreshToken: refreshToken })
            });
        } catch (e) { console.error("로그아웃 요청 실패", e); } finally {
            localStorage.clear();
            alert('안전하게 로그아웃 되었습니다.');
            location.href = '/login';
        }
    }

    // 드롭다운 외부 클릭 시 닫기
    document.addEventListener('click', (e) => {
        if (!e.target.closest('#searchResultWrapper')) {
            document.getElementById('searchDropdown').style.display = 'none';
        }
    });
</script>
</body>
</html>