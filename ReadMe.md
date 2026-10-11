# EcomApp Automation Testing Framework

An enterprise-grade End-to-End (E2E) Test Automation Framework designed for e-commerce workflows covering **UI**, **API**, and **Database (DB)** validation.

## 🚀 Key Features

- **Design Pattern**: Page Object Model (POM) with Cucumber BDD.
- **UI Automation**: Selenium WebDriver 4 with multi-browser support, custom options, and headless mode execution.
- **API Testing**: REST Assured integration for backend validation and order verification.
- **Database Testing**: MySQL / MongoDB queries for data persistence and transactional assertions.
- **Reporting**: ExtentReports & Cucumber HTML reports for detailed test execution results.
- **CI/CD Ready**: Jenkins Declarative Pipeline integration with parallel test execution.

## 🛠️ Tech Stack

- **Language**: Java
- **UI Engine**: Selenium WebDriver
- **BDD Framework**: Cucumber 7
- **Test Runner**: TestNG (Parallel data-provider execution)
- **API Engine**: REST Assured & Jackson
- **Build Tool**: Maven
- **CI/CD**: Jenkins

## 📁 Framework Structure

```text
src/
 ├── main/java/com/sdet/framework/
 │    ├── api/         # REST Assured API clients & request specifications
 │    ├── pages/       # Page Object classes (POM)
 │    └── utils/       # Drivers, Config, Database & Extent Helpers
 └── test/java/com/sdet/framework/
      ├── hooks/       # Cucumber Setup/Teardown hooks
      ├── runners/     # TestNG Test Runner configuration
      └── stepdef/     # BDD Feature Step Definitions (UI & API)
src/test/resources/
 ├── features/         # BDD Feature Files (.feature)
 └── testData/         # Excel DataSheets & Test Cases