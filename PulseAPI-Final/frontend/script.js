/* =====================================================
   PULSE API FRONTEND
   This file controls login, register and dashboard.
   ===================================================== */


/* ================= GET HTML ELEMENTS ================= */

const loginPage = document.getElementById("loginPage");
const registerPage = document.getElementById("registerPage");
const dashboardPage = document.getElementById("dashboardPage");

const loginForm = document.getElementById("loginForm");
const registerForm = document.getElementById("registerForm");

const showRegister = document.getElementById("showRegister");
const showLogin = document.getElementById("showLogin");

const forgotPassword = document.getElementById("forgotPassword");

const logoutButton = document.getElementById("logoutButton");


/* ================= SAMPLE DATA ================= */

/*
   API data is currently stored in browser localStorage.

   Later we will replace this with
   Spring Boot + Hibernate + MySQL data.
*/

let apis = JSON.parse(localStorage.getItem("pulseApis")) || [

    {
        id: 1,
        name: "Google API",
        url: "https://google.com",
        method: "GET",
        interval: "5 Minutes",
        status: "UP"
    },

    {
        id: 2,
        name: "YouTube API",
        url: "https://youtube.com",
        method: "GET",
        interval: "10 Minutes",
        status: "UP"
    },

    {
        id: 3,
        name: "Test API",
        url: "https://example.com",
        method: "GET",
        interval: "1 Minute",
        status: "DOWN"
    }
];


let monitoringResults = [

    {
        id: 1,
        api: "Google API",
        code: 200,
        response: 250,
        status: "UP",
        checked: "Today 10:30 AM"
    },

    {
        id: 2,
        api: "YouTube API",
        code: 200,
        response: 245,
        status: "UP",
        checked: "Today 10:25 AM"
    },

    {
        id: 3,
        api: "Test API",
        code: 500,
        response: 0,
        status: "DOWN",
        checked: "Today 10:20 AM"
    }
];


let alerts = [

    {
        id: 1,
        api: "Test API",
        type: "DOWN",
        message: "API is not responding",
        status: "SENT",
        sent: "Today 10:20 AM"
    },

    {
        id: 2,
        api: "Test API",
        type: "ERROR",
        message: "Server returned status code 500",
        status: "SENT",
        sent: "Today 10:20 AM"
    }
];


/* ================= SAVE API DATA ================= */

function saveApis() {

    localStorage.setItem(
        "pulseApis",
        JSON.stringify(apis)
    );
}


/* ================= LOGIN PAGE ================= */

showRegister.addEventListener("click", function () {

    loginPage.classList.add("hidden");

    registerPage.classList.remove("hidden");

});


/* ================= REGISTER PAGE ================= */

showLogin.addEventListener("click", function () {

    registerPage.classList.add("hidden");

    loginPage.classList.remove("hidden");

});


/* ================= REGISTER ================= */

registerForm.addEventListener("submit", async function (event) {

    event.preventDefault();


    const name =
        document.getElementById("registerName").value.trim();

    const email =
        document.getElementById("registerEmail").value.trim();

    const password =
        document.getElementById("registerPassword").value;

    const confirmPassword =
        document.getElementById("confirmPassword").value;


    const message =
        document.getElementById("registerMessage");


    /* Check password */

    if (password !== confirmPassword) {

        message.textContent =
            "Passwords do not match.";

        message.style.color = "#dc2626";

        return;
    }


    /* Create user object */

    const user = {

        name: name,
        email: email,
        password: password

    };


    try {

        /* Send user data to Spring Boot */

        const response = await fetch(
            "http://localhost:8080/api/users/register",
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(user)
            }
        );


        const result = await response.text();


        if (response.ok) {

            message.textContent =
                "Registration successful! Please login.";

            message.style.color = "#16a34a";


            /* Clear form */

            registerForm.reset();


            /* Go to login after short time */

            setTimeout(function () {

                registerPage.classList.add("hidden");

                loginPage.classList.remove("hidden");

            }, 1200);

        } else {

            message.textContent =
                "Registration failed.";

            message.style.color = "#dc2626";

            console.log(result);
        }


    } catch (error) {

        console.error(error);

        message.textContent =
            "Cannot connect to server.";

        message.style.color = "#dc2626";
    }

});


/* ================= LOGIN ================= */

loginForm.addEventListener("submit", function (event) {

    event.preventDefault();


    const email =
        document.getElementById("loginEmail").value.trim();

    const password =
        document.getElementById("loginPassword").value;


    const message =
        document.getElementById("loginMessage");


    /* Get registered user */

    const savedUser =
        JSON.parse(localStorage.getItem("pulseUser"));


    if (!savedUser) {

        message.textContent =
            "Please register first.";

        message.style.color = "#dc2626";

        return;
    }


    /* Check email and password */

    if (
        email === savedUser.email &&
        password === savedUser.password
    ) {

        /* Save login status */

        localStorage.setItem(
            "isLoggedIn",
            "true"
        );


        loginPage.classList.add("hidden");

        dashboardPage.classList.remove("hidden");


        /* Show user name */

        loadUser();


        /* Load dashboard */

        loadDashboard();


        message.textContent =
            "Login successful!";

        message.style.color = "#16a34a";

    } else {

        message.textContent =
            "Invalid email or password.";

        message.style.color = "#dc2626";

    }

});


/* ================= FORGOT PASSWORD ================= */

forgotPassword.addEventListener("click", function () {

    alert(
        "Password reset will be connected with backend later."
    );

});


/* ================= LOAD USER ================= */

function loadUser() {

    const user =
        JSON.parse(localStorage.getItem("pulseUser"));


    if (!user) {
        return;
    }


    document.getElementById("userName").textContent =
        user.name;


    /* Show first letter of name */

    document.getElementById("userAvatar").textContent =
        user.name.charAt(0).toUpperCase();

}


/* ================= SIDEBAR MENU ================= */

const menuItems =
    document.querySelectorAll(".menu-item");


menuItems.forEach(function (item) {

    item.addEventListener("click", function () {

        const section =
            item.getAttribute("data-section");


        showSection(section);


        /* Remove active class */

        menuItems.forEach(function (menu) {

            menu.classList.remove("active");

        });


        /* Add active class */

        item.classList.add("active");

    });

});


/* ================= SHOW SECTION ================= */

function showSection(sectionId) {

    const sections =
        document.querySelectorAll(".content-section");


    sections.forEach(function (section) {

        section.classList.add("hidden");

    });


    const selectedSection =
        document.getElementById(sectionId);


    if (selectedSection) {

        selectedSection.classList.remove("hidden");

    }


    /* Change page title */

    const pageTitle =
        document.getElementById("pageTitle");


    if (sectionId === "dashboardSection") {

        pageTitle.textContent = "Dashboard";

    } else if (sectionId === "apiSection") {

        pageTitle.textContent = "API Management";

    } else if (sectionId === "monitoringSection") {

        pageTitle.textContent = "Monitoring Results";

    } else if (sectionId === "alertsSection") {

        pageTitle.textContent = "Alerts";

    }

}


/* ================= LOAD DASHBOARD ================= */

function loadDashboard() {

    updateStatistics();

    displayRecentActivity();

    displayApis();

    displayMonitoringResults();

    displayAlerts();

}


/* ================= UPDATE STATISTICS ================= */

function updateStatistics() {

    const total =
        apis.length;


    const up =
        apis.filter(function (api) {

            return api.status === "UP";

        }).length;


    const down =
        apis.filter(function (api) {

            return api.status === "DOWN";

        }).length;


    document.getElementById("totalApis").textContent =
        total;


    document.getElementById("upApis").textContent =
        up;


    document.getElementById("downApis").textContent =
        down;


    document.getElementById("totalAlerts").textContent =
        alerts.length;

}


/* ================= RECENT ACTIVITY ================= */

function displayRecentActivity() {

    const container =
        document.getElementById("recentActivity");


    container.innerHTML = "";


    if (apis.length === 0) {

        container.innerHTML =
            "<p>No API added yet.</p>";

        return;
    }


    /* Show only latest 5 APIs */

    const latestApis =
        apis.slice(-5).reverse();


    latestApis.forEach(function (api) {

        const item =
            document.createElement("div");


        item.className =
            "activity-item";


        const statusClass =
            api.status === "UP"
                ? "up"
                : "down";


        item.innerHTML = `

            <div class="activity-left">

                <div class="status-dot ${statusClass}">
                </div>

                <div>

                    <strong>${api.name}</strong>

                    <p style="font-size:12px;color:#64748b;">
                        ${api.url}
                    </p>

                </div>

            </div>

            <span class="status-badge
                ${api.status === "UP"
            ? "status-up"
            : "status-down"}">

                ${api.status}

            </span>
        `;


        container.appendChild(item);

    });

}


/* ================= SHOW API FORM ================= */

document
    .getElementById("showApiFormButton")
    .addEventListener("click", function () {

        document
            .getElementById("apiFormBox")
            .classList.remove("hidden");

    });


/* ================= CANCEL API FORM ================= */

document
    .getElementById("cancelApiButton")
    .addEventListener("click", function () {

        document
            .getElementById("apiFormBox")
            .classList.add("hidden");


        document
            .getElementById("apiForm")
            .reset();

    });


/* ================= ADD API ================= */

document
    .getElementById("apiForm")
    .addEventListener("submit", function (event) {

        event.preventDefault();


        const name =
            document.getElementById("apiName").value.trim();

        const url =
            document.getElementById("apiUrl").value.trim();

        const method =
            document.getElementById("httpMethod").value;

        const interval =
            document.getElementById("monitoringInterval").value;


        /* Create new API */

        const newApi = {

            id: apis.length + 1,

            name: name,

            url: url,

            method: method,

            interval: interval,

            status: "UP"

        };


        apis.push(newApi);


        /* Save data */

        saveApis();


        /* Refresh UI */

        displayApis();

        updateStatistics();

        displayRecentActivity();


        /* Clear form */

        document
            .getElementById("apiForm")
            .reset();


        document
            .getElementById("apiFormBox")
            .classList.add("hidden");


        alert("API added successfully.");

    });


/* ================= DISPLAY APIs ================= */

function displayApis() {

    const table =
        document.getElementById("apiTableBody");


    table.innerHTML = "";


    apis.forEach(function (api) {

        const row =
            document.createElement("tr");


        row.innerHTML = `

            <td>${api.id}</td>

            <td>
                <strong>${api.name}</strong>
            </td>

            <td>${api.url}</td>

            <td>
                <span class="method-badge">
                    ${api.method}
                </span>
            </td>

            <td>

                <span class="status-badge
                    ${api.status === "UP"
            ? "status-up"
            : "status-down"}">

                    ${api.status}

                </span>

            </td>

            <td>${api.interval}</td>

            <td>

                <button
                    class="delete-button"
                    onclick="deleteApi(${api.id})">

                    Delete

                </button>

            </td>

        `;


        table.appendChild(row);

    });

}


/* ================= DELETE API ================= */

function deleteApi(id) {

    const confirmDelete =
        confirm("Are you sure you want to delete this API?");


    if (!confirmDelete) {
        return;
    }


    apis =
        apis.filter(function (api) {

            return api.id !== id;

        });


    saveApis();


    displayApis();

    updateStatistics();

    displayRecentActivity();


    alert("API deleted successfully.");

}


/* ================= MONITORING TABLE ================= */

function displayMonitoringResults() {

    const table =
        document.getElementById("monitoringTableBody");


    table.innerHTML = "";


    monitoringResults.forEach(function (result) {

        const row =
            document.createElement("tr");


        row.innerHTML = `

            <td>${result.id}</td>

            <td>
                <strong>${result.api}</strong>
            </td>

            <td>${result.code}</td>

            <td>${result.response} ms</td>

            <td>

                <span class="status-badge
                    ${result.status === "UP"
            ? "status-up"
            : "status-down"}">

                    ${result.status}

                </span>

            </td>

            <td>${result.checked}</td>

        `;


        table.appendChild(row);

    });

}


/* ================= ALERT TABLE ================= */

function displayAlerts() {

    const table =
        document.getElementById("alertsTableBody");


    table.innerHTML = "";


    alerts.forEach(function (alertItem) {

        const row =
            document.createElement("tr");


        row.innerHTML = `

            <td>${alertItem.id}</td>

            <td>${alertItem.api}</td>

            <td>
                <span class="status-badge status-down">
                    ${alertItem.type}
                </span>
            </td>

            <td>${alertItem.message}</td>

            <td>
                <span class="status-badge status-up">
                    ${alertItem.status}
                </span>
            </td>

            <td>${alertItem.sent}</td>

        `;


        table.appendChild(row);

    });

}


/* ================= LOGOUT ================= */

logoutButton.addEventListener("click", function () {

    localStorage.removeItem("isLoggedIn");


    dashboardPage.classList.add("hidden");

    loginPage.classList.remove("hidden");


    loginForm.reset();


    document.getElementById("loginMessage").textContent =
        "You have been logged out.";

});


/* ================= PAGE LOAD ================= */

window.addEventListener("load", function () {

    const isLoggedIn =
        localStorage.getItem("isLoggedIn");


    if (isLoggedIn === "true") {

        loginPage.classList.add("hidden");

        dashboardPage.classList.remove("hidden");

        loadUser();

        loadDashboard();

    }

});