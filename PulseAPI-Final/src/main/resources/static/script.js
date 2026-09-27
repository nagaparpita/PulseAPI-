/* =====================================================
   PULSE API - FRONTEND JAVASCRIPT
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


/* ================= SAMPLE API DATA ================= */

let apis = JSON.parse(
    localStorage.getItem("pulseApis")
) || [

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


/* ================= MONITORING DATA ================= */

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


/* ================= ALERT DATA ================= */

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


/* ================= SAVE APIs ================= */

function saveApis() {

    localStorage.setItem(
        "pulseApis",
        JSON.stringify(apis)
    );

}


/* =====================================================
   REGISTER PAGE
   ===================================================== */


/* ================= SHOW REGISTER PAGE ================= */

showRegister.addEventListener("click", function () {

    loginPage.classList.add("hidden");

    registerPage.classList.remove("hidden");

});


/* ================= SHOW LOGIN PAGE ================= */

showLogin.addEventListener("click", function () {

    registerPage.classList.add("hidden");

    loginPage.classList.remove("hidden");

});


/* ================= REGISTER USER ================= */

registerForm.addEventListener(
    "submit",
    async function (event) {

        event.preventDefault();


        /* Get form values */

        const name =
            document.getElementById(
                "registerName"
            ).value.trim();


        const email =
            document.getElementById(
                "registerEmail"
            ).value.trim();


        const password =
            document.getElementById(
                "registerPassword"
            ).value;


        const confirmPassword =
            document.getElementById(
                "confirmPassword"
            ).value;


        const message =
            document.getElementById(
                "registerMessage"
            );


        /* Check empty fields */

        if (
            name === "" ||
            email === "" ||
            password === "" ||
            confirmPassword === ""
        ) {

            message.textContent =
                "Please fill all fields.";

            message.style.color =
                "#dc2626";

            return;
        }


        /* Check password */

        if (password !== confirmPassword) {

            message.textContent =
                "Passwords do not match.";

            message.style.color =
                "#dc2626";

            return;
        }


        /* Create user object */

        const user = {

            name: name,

            email: email,

            password: password

        };


        try {

            /*
             * Send registration data
             * to Spring Boot backend.
             *
             * Frontend and backend are both
             * running on localhost:8080.
             */

            const response =
                await fetch(
                    "/api/users/register",
                    {

                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/json"
                        },

                        body:
                            JSON.stringify(user)

                    }
                );


            const result =
                await response.text();


            console.log(
                "Server response:",
                result
            );


            /* Check response */

            if (response.ok) {

                message.textContent =
                    "Registration successful! Please login.";

                message.style.color =
                    "#16a34a";


                /* Reset form */

                registerForm.reset();


                /*
                 * Store user temporarily
                 * for the current login code.
                 *
                 * This will be removed when
                 * Login is connected to backend.
                 */

                localStorage.setItem(
                    "pulseUser",
                    JSON.stringify(user)
                );


                /* Go to login page */

                setTimeout(
                    function () {

                        registerPage
                            .classList
                            .add("hidden");

                        loginPage
                            .classList
                            .remove("hidden");

                    },
                    1200
                );

            } else {

                message.textContent =
                    "Registration failed.";

                message.style.color =
                    "#dc2626";

                console.log(
                    "Registration error:",
                    result
                );

            }

        } catch (error) {

            console.error(
                "Connection error:",
                error
            );


            message.textContent =
                "Cannot connect to server.";

            message.style.color =
                "#dc2626";

        }

    }
);

/* =====================================================
   LOGIN
   ===================================================== */

loginForm.addEventListener(
    "submit",
    async function (event) {

        event.preventDefault();

        const email =
            document.getElementById(
                "loginEmail"
            ).value.trim();

        const password =
            document.getElementById(
                "loginPassword"
            ).value;

        const message =
            document.getElementById(
                "loginMessage"
            );


        /* Check empty fields */

        if (email === "" || password === "") {

            message.textContent =
                "Please enter email and password.";

            message.style.color =
                "#dc2626";

            return;
        }


        /* Create login object */

        const user = {

            email: email,

            password: password

        };


        try {

            /* Send login data to backend */

            const response =
                await fetch(
                    "/api/users/login",
                    {

                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/json"
                        },

                        body:
                            JSON.stringify(user)

                    }
                );


            const result =
                await response.json();


            console.log(
                "Login server response:",
                result
            );


            /* Check login */

            if (response.ok) {

                message.textContent =
                    "Login successful!";

                message.style.color =
                    "#16a34a";


                /*
                 * Save actual logged-in user
                 * received from database.
                 */

                const loggedInUser = {

                    userId:
                    result.userId,

                    name:
                    result.name,

                    email:
                    result.email

                };


                localStorage.setItem(
                    "pulseUser",
                    JSON.stringify(
                        loggedInUser
                    )
                );


                localStorage.setItem(
                    "isLoggedIn",
                    "true"
                );


                /* Open dashboard */

                setTimeout(
                    function () {

                        loginPage
                            .classList
                            .add("hidden");

                        dashboardPage
                            .classList
                            .remove("hidden");

                        loadUser();

                        loadDashboard();

                    },
                    500
                );


            } else {

                message.textContent =
                    result.message;

                message.style.color =
                    "#dc2626";

            }


        } catch (error) {

            console.error(
                "Login connection error:",
                error
            );

            message.textContent =
                "Cannot connect to server.";

            message.style.color =
                "#dc2626";

        }

    }
);



/* =====================================================
   FORGOT PASSWORD
   ===================================================== */

forgotPassword.addEventListener(
    "click",
    function () {

        alert(
            "Password reset will be connected with backend later."
        );

    }
);


/* =====================================================
   LOAD USER
   ===================================================== */

function loadUser() {

    const user =
        JSON.parse(
            localStorage.getItem(
                "pulseUser"
            )
        );


    if (!user) {

        return;

    }


    const userName =
        document.getElementById(
            "userName"
        );


    const userAvatar =
        document.getElementById(
            "userAvatar"
        );


    if (userName) {

        userName.textContent =
            user.name;

    }


    if (userAvatar) {

        userAvatar.textContent =
            user.name
                .charAt(0)
                .toUpperCase();

    }

}


/* =====================================================
   SIDEBAR MENU
   ===================================================== */

const menuItems =
    document.querySelectorAll(
        ".menu-item"
    );


menuItems.forEach(
    function (item) {

        item.addEventListener(
            "click",
            function () {

                const section =
                    item.getAttribute(
                        "data-section"
                    );


                showSection(section);


                menuItems.forEach(
                    function (menu) {

                        menu.classList.remove(
                            "active"
                        );

                    }
                );


                item.classList.add(
                    "active"
                );

            }
        );

    }
);


/* =====================================================
   SHOW SECTION
   ===================================================== */

function showSection(sectionId) {

    const sections =
        document.querySelectorAll(
            ".content-section"
        );


    sections.forEach(
        function (section) {

            section.classList.add(
                "hidden"
            );

        }
    );


    const selectedSection =
        document.getElementById(
            sectionId
        );


    if (selectedSection) {

        selectedSection.classList.remove(
            "hidden"
        );

    }


    const pageTitle =
        document.getElementById(
            "pageTitle"
        );


    if (!pageTitle) {

        return;

    }


    if (sectionId === "dashboardSection") {

        pageTitle.textContent =
            "Dashboard";

    }

    else if (
        sectionId === "apiSection"
    ) {

        pageTitle.textContent =
            "API Management";

    }

    else if (
        sectionId === "monitoringSection"
    ) {

        pageTitle.textContent =
            "Monitoring Results";

    }

    else if (
        sectionId === "alertsSection"
    ) {

        pageTitle.textContent =
            "Alerts";

        loadAlertsFromDatabase();

    }

}


/* =====================================================
   LOAD DASHBOARD
   ===================================================== */

async function loadDashboard() {

    await loadApisFromDatabase();

    await loadMonitoringResultsFromDatabase();

    // Load alerts before updating dashboard statistics
    await loadAlertsFromDatabase();

    updateStatistics();

    displayRecentActivity();

    displayApis();
}

/* =====================================================
   LOAD APIs FROM DATABASE
   ===================================================== */

async function loadApisFromDatabase() {

    const user =
        JSON.parse(
            localStorage.getItem(
                "pulseUser"
            )
        );


    if (!user || !user.userId) {

        console.log(
            "No logged-in user found."
        );

        return;

    }


    try {

        const response =
            await fetch(
                `/api/apis?userId=${user.userId}`
            );


        if (!response.ok) {

            console.log(
                "Failed to load APIs."
            );

            return;

        }


        const databaseApis =
            await response.json();


        /*
         * Convert database API format
         * into frontend format.
         */

        apis =
            databaseApis.map(
                function (api) {

                    let intervalText =
                        api.monitoringInterval +
                        " Minute";

                    return {

                        id:
                        api.apiId,

                        userId:
                        api.userId,

                        name:
                        api.apiName,

                        url:
                        api.apiUrl,

                        method:
                        api.httpMethod,

                        interval:
                        intervalText,

                        status:
                        api.status

                    };

                }
            );


        console.log(
            "APIs loaded from database:",
            apis
        );


    } catch (error) {

        console.error(
            "Error loading APIs:",
            error
        );

    }

}


/* =====================================================
   UPDATE DASHBOARD STATISTICS
   ===================================================== */

function updateStatistics() {

    const total =
        apis.length;


    const up =
        apis.filter(
            function (api) {

                return api.status === "UP";

            }
        ).length;


    const down =
        apis.filter(
            function (api) {

                return api.status === "DOWN";

            }
        ).length;


    const totalApis =
        document.getElementById(
            "totalApis"
        );


    const upApis =
        document.getElementById(
            "upApis"
        );


    const downApis =
        document.getElementById(
            "downApis"
        );


    const totalAlerts =
        document.getElementById(
            "totalAlerts"
        );


    if (totalApis) {

        totalApis.textContent =
            total;

    }


    if (upApis) {

        upApis.textContent =
            up;

    }


    if (downApis) {

        downApis.textContent =
            down;

    }


    if (totalAlerts) {

        totalAlerts.textContent =
            alerts.length;

    }

}


/* =====================================================
   RECENT ACTIVITY
   ===================================================== */

function displayRecentActivity() {

    const container =
        document.getElementById(
            "recentActivity"
        );


    if (!container) {

        return;

    }


    container.innerHTML = "";


    if (apis.length === 0) {

        container.innerHTML =
            "<p>No API added yet.</p>";

        return;

    }


    const latestApis =
        apis
            .slice(-5)
            .reverse();


    latestApis.forEach(
        function (api) {

            const item =
                document.createElement(
                    "div"
                );


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

                        <strong>
                            ${api.name}
                        </strong>

                        <p style="
                            font-size:12px;
                            color:#64748b;
                        ">
                            ${api.url}
                        </p>

                    </div>

                </div>

                <span class="status-badge
                    ${
                api.status === "UP"
                    ? "status-up"
                    : "status-down"
            }">

                    ${api.status}

                </span>

            `;


            container.appendChild(
                item
            );

        }
    );

}


/* =====================================================
   SHOW API FORM
   ===================================================== */

const showApiFormButton =
    document.getElementById(
        "showApiFormButton"
    );


if (showApiFormButton) {

    showApiFormButton.addEventListener(
        "click",
        function () {

            document
                .getElementById(
                    "apiFormBox"
                )
                .classList
                .remove("hidden");

        }
    );

}


/* =====================================================
   CANCEL API FORM
   ===================================================== */

const cancelApiButton =
    document.getElementById(
        "cancelApiButton"
    );


if (cancelApiButton) {

    cancelApiButton.addEventListener(
        "click",
        function () {

            document
                .getElementById(
                    "apiFormBox"
                )
                .classList
                .add("hidden");


            document
                .getElementById(
                    "apiForm"
                )
                .reset();

        }
    );

}


/* =====================================================
   ADD API
   ===================================================== */

const apiForm =
    document.getElementById(
        "apiForm"
    );

if (apiForm) {

    apiForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();

            const name =
                document.getElementById(
                    "apiName"
                ).value.trim();

            const url =
                document.getElementById(
                    "apiUrl"
                ).value.trim();

            const method =
                document.getElementById(
                    "httpMethod"
                ).value;

            const interval =
                document.getElementById(
                    "monitoringInterval"
                ).value;

            /* Get logged-in user */

            const loggedInUser =
                JSON.parse(
                    localStorage.getItem(
                        "pulseUser"
                    )
                );

            if (!loggedInUser || !loggedInUser.userId) {

                alert(
                    "Please login again."
                );

                return;

            }

            const userId =
                loggedInUser.userId;

            /*
             * Convert interval text into minutes.
             * Example: "1 Minute" -> 1
             */
            let intervalMinutes = 1;

            if (interval.includes("5")) {
                intervalMinutes = 5;
            }
            else if (interval.includes("10")) {
                intervalMinutes = 10;
            }

            /* Create API object */

            const newApi = {

                userId: userId,

                apiName: name,

                apiUrl: url,

                httpMethod: method,

                monitoringInterval:
                intervalMinutes,

                status: "UNKNOWN"
            };

            try {

                /* Send API data to backend */

                const response =
                    await fetch(
                        "/api/apis",
                        {

                            method: "POST",

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body:
                                JSON.stringify(newApi)
                        }
                    );

                const result =
                    await response.text();

                console.log(
                    "API server response:",
                    result
                );

                if (response.ok) {

                    alert(
                        "API added successfully!"
                    );

                    apiForm.reset();

                    document
                        .getElementById(
                            "apiFormBox"
                        )
                        .classList
                        .add("hidden");

                    /*
                     * Reload APIs from MySQL.
                     * This keeps the frontend
                     * synchronized with the database.
                     */
                    await loadApisFromDatabase();

                    /*
                     * Refresh dashboard statistics
                     * after adding the API.
                     */
                    updateStatistics();

                    /*
                     * Refresh API list on screen.
                     */
                    displayApis();

                    /*
                     * Refresh recent activity.
                     */
                    displayRecentActivity();

                }
                else {

                    alert(
                        "Failed to add API."
                    );

                    console.log(
                        "API error:",
                        result
                    );
                }

            }
            catch (error) {

                console.error(
                    "Connection error:",
                    error
                );

                alert(
                    "Cannot connect to server."
                );
            }

        }
    );

}

/* =====================================================
   DISPLAY APIs
   ===================================================== */

function displayApis() {

    const table =
        document.getElementById(
            "apiTableBody"
        );


    if (!table) {

        return;

    }


    table.innerHTML = "";


    apis.forEach(
        function (api) {

            const row =
                document.createElement(
                    "tr"
                );


            row.innerHTML = `

                <td>
                    ${api.id}
                </td>

                <td>
                    <strong>
                        ${api.name}
                    </strong>
                </td>

                <td>
                    ${api.url}
                </td>

                <td>
                    <span class="method-badge">
                        ${api.method}
                    </span>
                </td>

                <td>

                    <span class="status-badge
                        ${
                api.status === "UP"
                    ? "status-up"
                    : "status-down"
            }">

                        ${api.status}

                    </span>

                </td>

                <td>
                    ${api.interval}
                </td>

                <td>

                    <button
                        class="delete-button"
                        onclick="deleteApi(${api.id})">

                        Delete

                    </button>

                </td>

            `;


            table.appendChild(
                row
            );

        }
    );

}


/* =====================================================
   DELETE API
   ===================================================== */

async function deleteApi(id) {

    const confirmDelete =
        confirm(
            "Are you sure you want to delete this API?"
        );


    if (!confirmDelete) {

        return;

    }


    try {

        /*
         * Send delete request to backend.
         */

        const response =
            await fetch(
                `/api/apis/${id}`,
                {
                    method: "DELETE"
                }
            );


        const result =
            await response.text();


        console.log(
            "Delete server response:",
            result
        );


        /* Check response */

        if (response.ok) {

            /*
             * Remove API from frontend array
             * after successful database deletion.
             */

            apis =
                apis.filter(
                    function (api) {

                        return api.id !== id;

                    }
                );


            /* Refresh API table */

            displayApis();


            /* Refresh dashboard statistics */

            updateStatistics();


            /* Refresh recent activity */

            displayRecentActivity();


            alert(
                "API deleted successfully."
            );

        }

        else {

            alert(
                "Failed to delete API."
            );

        }


    }

    catch (error) {

        console.error(
            "Delete API error:",
            error
        );


        alert(
            "Cannot connect to server."
        );

    }

}

/* =====================================================
   LOAD MONITORING RESULTS FROM DATABASE
   ===================================================== */

async function loadMonitoringResultsFromDatabase() {

    try {

        /*
         * Get all APIs so that
         * monitoring results can show
         * the actual API name.
         */

        const apiResponse =
            await fetch(
                "/api/apis/all"
            );


        if (!apiResponse.ok) {

            console.log(
                "Failed to load API names."
            );

            return;

        }


        const allApis =
            await apiResponse.json();


        /*
         * Get monitoring results
         * from database.
         */

        const user =
            JSON.parse(
                localStorage.getItem("pulseUser")
            );

        if (!user || !user.userId) {
            console.log("No logged-in user found.");
            return;
        }

        const resultResponse =
            await fetch(
                `/api/monitoring-results?userId=${user.userId}`
            );


        if (!resultResponse.ok) {

            console.log(
                "Failed to load monitoring results."
            );

            return;

        }


        const databaseResults =
            await resultResponse.json();


        /*
         * Convert database results
         * into frontend format.
         */

        monitoringResults =
            databaseResults.map(
                function (result) {

                    const api =
                        allApis.find(
                            function (item) {

                                return item.apiId ===
                                    result.apiId;

                            }
                        );


                    return {

                        id:
                        result.resultId,

                        api:
                            api
                                ? api.apiName
                                : "API ID " + result.apiId,

                        code:
                            result.statusCode !== null
                                ? result.statusCode
                                : "-",

                        response:
                            result.responseTime !== null
                                ? result.responseTime
                                : 0,

                        status:
                        result.status,

                        checked:
                            result.checkedAt
                                ? new Date(
                                    result.checkedAt
                                ).toLocaleString()
                                : "-"

                    };

                }
            );


        console.log(
            "Monitoring results loaded:",
            monitoringResults
        );


        displayMonitoringResults();


    } catch (error) {

        console.error(
            "Error loading monitoring results:",
            error
        );

    }

}


/* =====================================================
   DISPLAY MONITORING RESULTS
   ===================================================== */

function displayMonitoringResults() {

    const table =
        document.getElementById(
            "monitoringTableBody"
        );

    if (!table) {
        return;
    }

    table.innerHTML = "";

    monitoringResults.forEach(
        function (result) {

            const row =
                document.createElement(
                    "tr"
                );

            row.innerHTML = `

                <td>
                    ${result.id}
                </td>

                <td>
                    <strong>
                        ${result.api}
                    </strong>
                </td>

                <td>
                    ${result.code}
                </td>

                <td>
                    ${result.response} ms
                </td>

                <td>

                    <span class="status-badge
                        ${
                result.status === "UP"
                    ? "status-up"
                    : "status-down"
            }">

                        ${result.status}

                    </span>

                </td>

                <td>
                    ${result.checked}
                </td>

            `;

            table.appendChild(
                row
            );

        }
    );

}

/* =====================================================
   DISPLAY ALERTS
   ===================================================== */

function displayAlerts() {

    const table =
        document.getElementById(
            "alertsTableBody"
        );


    if (!table) {

        return;

    }


    table.innerHTML = "";


    alerts.forEach(
        function (alertItem) {

            const row =
                document.createElement(
                    "tr"
                );


            row.innerHTML = `

                <td>
                    ${alertItem.id}
                </td>

                <td>
                    ${alertItem.api}
                </td>

                <td>

                    <span class="status-badge status-down">
                        ${alertItem.type}
                    </span>

                </td>

                <td>
                    ${alertItem.message}
                </td>

                <td>

                    <span class="status-badge status-up">
                        ${alertItem.status}
                    </span>

                </td>

                <td>
                    ${alertItem.sent}
                </td>

            `;


            table.appendChild(
                row
            );

        }
    );

}

/* =====================================================
   GET API NAME BY ID
   ===================================================== */

function getApiNameById(apiId) {

    const api =
        apis.find(
            function (item) {

                return item.id === apiId;

            }
        );

    if (api) {

        return api.name;

    }

    return "API ID " + apiId;
}

/* =====================================================
   LOAD ALERTS FROM DATABASE
   ===================================================== */

async function loadAlertsFromDatabase() {

    try {

        const user =
            JSON.parse(
                localStorage.getItem("pulseUser")
            );

        if (!user || !user.userId) {

            console.log(
                "No logged-in user found."
            );

            return;
        }


        const response =
            await fetch(
                `/api/alerts?userId=${user.userId}`
            );


        if (!response.ok) {

            console.log(
                "Failed to load alerts."
            );

            return;
        }


        const databaseAlerts =
            await response.json();


        alerts =
            databaseAlerts.map(
                function (alertItem) {

                    return {

                        id: alertItem.alertId,

                        api:
                            getApiNameById(
                                alertItem.apiId
                            ),

                        type:
                        alertItem.alertType,

                        message:
                        alertItem.message,

                        status:
                        alertItem.alertStatus,

                        sent:
                            alertItem.sentAt
                                ? new Date(
                                    alertItem.sentAt
                                ).toLocaleString()
                                : "-"
                    };
                }
            );


        console.log(
            "Alerts loaded:",
            alerts
        );


        displayAlerts();


    } catch (error) {

        console.error(
            "Error loading alerts:",
            error
        );
    }
}


/* =====================================================
   LOGOUT
   ===================================================== */

logoutButton.addEventListener(
    "click",
    function () {

        localStorage.removeItem(
            "isLoggedIn"
        );


        dashboardPage
            .classList
            .add("hidden");


        loginPage
            .classList
            .remove("hidden");


        loginForm.reset();


        const loginMessage =
            document.getElementById(
                "loginMessage"
            );


        if (loginMessage) {

            loginMessage.textContent =
                "You have been logged out.";

        }

    }
);


/* =====================================================
   PAGE LOAD
   ===================================================== */

window.addEventListener(
    "load",
    function () {

        const isLoggedIn =
            localStorage.getItem(
                "isLoggedIn"
            );


        if (isLoggedIn === "true") {

            loginPage
                .classList
                .add("hidden");


            dashboardPage
                .classList
                .remove("hidden");


            loadUser();

            loadDashboard();

        }

    }
);