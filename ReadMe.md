# EcomApp Automation Testing Framework

An enterprise-grade End-to-End (E2E) Test Automation Framework designed for e-commerce workflows covering **UI**, **API**, and **Database (DB)** validation.

## 🚀 Key Features

* **Design Pattern**: Page Object Model (POM) with Cucumber BDD.
* **UI Automation**: Selenium WebDriver with Page Factory and dynamic waits/polling.
* **API Testing**: REST Assured integration for backend validation and order verification.
* **Database Testing**: MySQL / MongoDB queries for database assertions.
* **Reporting**: ExtentReports for detailed HTML test execution reports.

## 🛠️ Tech Stack

* **Language**: Java
* **UI Engine**: Selenium WebDriver
* **BDD Framework**: Cucumber
* **Test Runner**: TestNG
* **API Engine**: REST Assured
* **Build Tool**: Maven

## 📁 Framework Structure

```text
src/
 ├── main/java/com/sdet/framework/
 │    ├── pages/       # Page Object classes (POM)
 │    └── utils/       # Drivers, Config, Database & Extent Helpers
 └── test/java/com/sdet/framework/
      ├── hooks/       # Cucumber Setup/Teardown
      ├── runners/     # TestNG Test Runner
      └── stepdef/     # Step Definitions
src/test/resources/
 ├── features/         # BDD Feature Files
 └── testData/         # Excel DataSheets