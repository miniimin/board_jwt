
async function fetchWithAuth(url, options = {}) {
    const accessToken = localStorage.getItem("accessToken");
    const refreshToken = localStorage.getItem("refreshToken");

    options.headers = {
        ...(options.headers || {}),
        "Authorization": "Bearer " + accessToken,
        "Content-Type": "application/json"
    };

    let response = await fetch(url, options);

    // 토큰 만료시 자동 갱신 시도
    if (response.status === 403 && refreshToken) {
        alert("access 토큰 만료되어 재발급");
        const refreshRes = await fetch("/api/auth/refresh", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ refreshToken })
        });

        if (refreshRes.ok) {
            const data = await refreshRes.json();
            localStorage.setItem("accessToken", data.accessToken);

            // 다시 원래 요청 재시도
            options.headers["Authorization"] = "Bearer " + data.accessToken;
            response = await fetch(url, options);
        } else {
            alert("세션 만료. 다시 로그인해주세요.");
            localStorage.clear();
            window.location.href = "/login";
        }
    }

    return response;
}