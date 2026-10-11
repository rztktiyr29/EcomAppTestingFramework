```groovy
pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-21'
    }

    parameters {
        choice(
            name: 'CUCUMBER_TAGS',
            choices: [
                '@UI_API',
                '@login',
                '@Smoke',
                '@Regression',
                '@E2E',
                '@OrderManagement'
            ],
            description: 'Select the Cucumber tag to execute'
        )
    }

    environment {
        MONGO_URI = credentials('jenkins-mongo-uri')
        ADMIN_EMAIL = credentials('jenkins-admin-email')
        ADMIN_PASSWORD = credentials('jenkins-admin-password')
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/rztktiyr29/EcomAppTestingFramework.git'
            }
        }

        stage('Build and Execute Tests') {
            steps {
                bat '''
                    mvn clean test ^
                    -DsuiteXmlFile=testng.xml ^
                    -Dcucumber.filter.tags="%CUCUMBER_TAGS%" ^
                    -Dmongo.uri="%MONGO_URI%" ^
                    -Dadmin.email="%ADMIN_EMAIL%" ^
                    -Dadmin.password="%ADMIN_PASSWORD%"
                '''
            }

            post {
                always {
                    script {
                        echo 'Publishing test reports...'

                        if (fileExists('target/surefire-reports/testng-results.xml')) {
                            step([
                                $class: 'TestNGPublisher',
                                reportFilenamePattern: '**/testng-results.xml',
                                showFailedBuilds: true
                            ])
                        } else {
                            echo 'TestNG XML report not found.'
                        }
                    }

                    publishHTML(target: [
                        allowMissing: true,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'test-output',
                        reportFiles: 'ExtentReport.html',
                        reportName: 'Extent Test Report'
                    ])

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
    }
}
