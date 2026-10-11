# EcomApp Automation Testing Framework

An enterprise-grade End-to-End (E2E) Test Automation Framework engineered for modern e-commerce applications. It seamlessly integrates **UI validation (Selenium)**, **API verification (REST Assured)**, and **database persistence assertions (MongoDB)** into a unified Cucumber BDD pipeline.

---

## 🚀 Key Features

- **Architecture:** Clean Page Object Model (POM) combined with Cucumber BDD.
- **UI Automation:** Selenium WebDriver 4 with flexible driver management, explicit waits, and headless mode execution.
- **API Testing:** REST Assured integration for backend management, authentication tokens, and service-layer validation.
- **Database Validation:** Direct MongoDB integration using `mongodb-driver-sync` to validate data persistence and consistency between API updates and UI presentation.
- **State Management:** Centralized runtime data handling through `TestContext` and `GlobalContext` across step definitions.
- **Advanced Reporting:** ExtentReports and native Cucumber HTML reports with execution logs and screenshots.
- **CI/CD & Security:** Jenkins Declarative Pipeline integration with secure credential binding and dynamic system property injection using `-D` properties.

---

## 🛠️ Tech Stack

| Technology | Tools |
|---|---|
| Programming Language | Java 21+ |
| UI Automation | Selenium WebDriver 4 |
| BDD Framework | Cucumber 7 |
| Test Runner | TestNG |
| API Automation | REST Assured, Jackson (POJO mapping) |
| Database | MongoDB Java Driver Sync |
| Build & Dependency Management | Maven |
| CI/CD | Jenkins |
| Design Pattern | Page Object Model (POM) |
| Reporting | ExtentReports, Cucumber HTML Reports |

---

## 📁 Framework Structure

```text
EcomAppTestingFramework/
│
├── src/
│   ├── main/java/com/sdet/framework/
│   │   ├── api/          # REST Assured clients, specifications, and POJOs
│   │   ├── pages/        # Page Object classes and UI action methods
│   │   └── utils/        # Driver management, ConfigReader, MongoDB utilities, TestContext
│   │
│   └── test/java/com/sdet/framework/
│       ├── hooks/        # Cucumber setup and teardown hooks
│       ├── runners/      # TestNG runner configuration
│       └── stepdef/      # UI, API, and database step definitions
│
├── src/test/resources/
│   ├── features/                  # Cucumber feature files
│   ├── testData/                  # Excel data sheets and test assets
│   ├── config.properties          # Local configuration (Git-ignored)
│   └── config.properties.example  # Configuration template
│
├── .gitignore
├── Jenkinsfile
└── pom.xml
```

---

## ⚙️ Prerequisites

Before running the framework, ensure the following are installed:

- **Java JDK 21 or later** — Configure `JAVA_HOME`.
- **Apache Maven 3.9 or later** — Add Maven to your system `PATH`.
- **Git** — For version control.
- **IntelliJ IDEA** — Recommended IDE.
- **IntelliJ Plugins:** Cucumber for Java and TestNG.

---

## 🔐 Configuration & Security Setup

### 1. Clone the Repository

```bash
git clone https://github.com/rztktiyr29/EcomAppTestingFramework.git
cd EcomAppTestingFramework
```

### 2. Create the Configuration File

Navigate to `src/test/resources/` and create a file named `config.properties`.

This file contains local configuration and credentials. It should be excluded from Git using `.gitignore`.

### 3. Configure the Properties

Add the following properties to your local `config.properties` file:

```properties
baseUrl=https://mern-ecommerce-front-end.vercel.app/
browser=chrome
headless=true

base.url=https://mern-ecommerce-api-9e9r.onrender.com

admin.email=YOUR_ADMIN_EMAIL
admin.password=YOUR_ADMIN_PASSWORD

implicitWait=0
explicitWait=20

mongo.uri=YOUR_MONGODB_CONNECTION_STRING
mongo.db.name=MERN_E_Commerce
```

**Security note:** Replace the placeholder values with your own credentials. Never commit passwords, database connection strings, or other secrets to GitHub.

The `config.properties.example` file should contain only placeholder values so that other developers can configure the project safely.

---

## 🏃 Execution Guide

### 1. Run Through IntelliJ IDEA

1. Open the project in IntelliJ IDEA.
2. Allow Maven to download and configure dependencies.
3. Navigate to:

   `src/test/java/com/sdet/framework/runners/TestRunner.java`

4. Right-click `TestRunner.java`.
5. Select **Run 'TestRunner'**.

### 2. Run Through Maven

Run the test suite using the TestNG XML suite:

```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

Run scenarios matching a specific Cucumber tag:

```bash
mvn test -Dcucumber.filter.tags="@UI_API"
```

Other examples:

```bash
mvn test -Dcucumber.filter.tags="@E2E"
mvn test -Dcucumber.filter.tags="@Regression"
mvn test -Dcucumber.filter.tags="@Smoke"
```

**Note:** Use the tag names and Maven execution commands supported by your actual runner and `pom.xml` configuration.

---

## 🔄 CI/CD — Jenkins Pipeline Setup

The framework includes a `Jenkinsfile` intended to support parameterized test execution, automated builds, test reporting, and report publishing.

### 1. Configure Jenkins Credentials

Navigate to:

**Manage Jenkins → Credentials → System → Global credentials**

Create credentials for the required secrets. For example:

| Credential ID | Credential Type | Value |
|---|---|---|
| `jenkins-mongo-uri` | Secret text | MongoDB connection string |
| `jenkins-admin-email` | Secret text | Admin email |
| `jenkins-admin-password` | Secret text | Admin password |

Use the exact credential IDs expected by your `Jenkinsfile`.

### 2. Create a Pipeline Job

1. Open Jenkins and select **New Item**.
2. Enter a job name and select **Pipeline**.
3. Configure the pipeline to use your GitHub repository:

   https://github.com/rztktiyr29/EcomAppTestingFramework

4. Select **Pipeline script from SCM**.
5. Choose **Git** and enter the repository URL.
6. Set the script path to `Jenkinsfile`.
7. Save the configuration.

### 3. Execute the Pipeline

Run the pipeline and select the desired Cucumber tag if your Jenkins job defines a tag parameter.

Examples include:

- `@UI_API`
- `@Regression`
- `@E2E`
- `@Smoke`

The pipeline should execute the selected tests and publish the configured reports.

---

## 📊 Reporting

The framework is configured to generate test execution reports.

| Report | Location |
|---|---|
| ExtentReports | `test-output/ExtentReport.html` |
| Cucumber HTML Report | `target/cucumber-reports/cucumber-pretty.html` |
| TestNG Results | TestNG-generated XML reports |

Report paths depend on the actual configuration in the runner, reporting adapters, and `pom.xml`.

Use the generated reports to review scenario status, execution logs, failures, and screenshots where configured.

---

## 🎯 Project Objective

The goal of this project is to demonstrate a maintainable and scalable test automation framework that combines:

- End-to-end UI automation.
- API testing and backend service validation.
- Database persistence verification.
- BDD-based test organization.
- Reusable page objects and utilities.
- Centralized test context management.
- Automated execution through Jenkins.
- Detailed test reporting.

This project serves as a practical demonstration of modern QA automation practices for e-commerce applications.

---

## 👨‍💻 Author

**Rajat Katiyar**

GitHub: https://github.com/rztktiyr29

LinkedIn: https://www.linkedin.com/in/rajat-katiyar-b322bb190
