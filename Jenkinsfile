pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-21'
    }

    triggers {
        // Runs automatically every weekday at 8:00 AM
        cron('0 8 * * 1-5')
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
                bat 'mvn test -DsuiteXmlFile=testng.xml'
            }
        }
    }

    post {
        always {
            // Parses TestNG XML results for trend graphs
            testng defaultSelect: 'fail', testResultsPattern: '**/target/surefire-reports/testng-results.xml'

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