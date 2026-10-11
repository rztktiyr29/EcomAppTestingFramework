pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-21'
    }

   parameters {
           choice(
               name: 'CUCUMBER_TAGS',
               choices: ['@UI_API', '@login', '@Smoke', '@Regression', '@E2E', '@OrderManagement'],
               description: 'Select Cucumber tag to run from UI'
           )
       }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/rztktiyr29/EcomAppTestingFramework.git'
            }
        }

        stage('Compile Framework') {
            steps {
                bat 'mvn clean test-compile'
            }
        }

        stage('Execute TestNG Suite') {
            steps {
                bat "mvn test -DsuiteXmlFile=testng.xml -Dcucumber.filter.tags=\"${params.CUCUMBER_TAGS}\""
            }
        }
    }

    post {
        always {
            // 1. TestNG XML Processing
            testNG showFailedBuilds: true, reportFilenamePattern: '**/target/surefire-reports/testng-results.xml'

            // 2. Extent HTML Report Publishing
            publishHTML(target: [
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'test-output',
                reportFiles: 'ExtentReport.html',
                reportName: 'Extent Test Report'
            ])

            // 3. Native Cucumber HTML Report Publishing
            publishHTML(target: [
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/cucumber-reports',
                reportFiles: 'cucumber-pretty.html',
                reportName: 'Cucumber HTML Report'
            ])
        }
    }
}