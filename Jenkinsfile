pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-21'
    }

    parameters {
        choice(
            name: 'CUCUMBER_TAGS',
            choices: ['@login', '@Smoke', '@Regression', '@E2E', '@OrderManagement'],
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
                // Passes selected tag from Jenkins UI to Maven execution
                bat "mvn test -DsuiteXmlFile=testng.xml -Dcucumber.filter.tags=\"${params.CUCUMBER_TAGS}\""
            }
        }
    }

    post {
        always {
            // Parses TestNG XML results for trends
            testNG showFailedBuild: true, testResultsPattern: '**/target/surefire-reports/testng-results.xml'

            // Publishes Extent HTML execution report
            publishHTML(target: [
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'test-output',
                reportFiles: 'index.html',
                reportName: 'Extent Test Report'
            ])
        }
    }
}