pipeline {
    agent any
    tools {
        // Maven tool configuration
        maven 'maven'
    }
    environment {
        DB_URL = credentials('DB_URL')
        DB_USER = credentials('DB_USER')
        DB_PASS = credentials('DB_PASS')
        JWT_SECRET = credentials('JWT_SECRET')
        JWT_EXPIRE_SECOND = credentials('JWT_EXPIRE_SECOND')
    }
    stages {
        stage('Build') {
            steps {
                echo 'Building the jar'
                script {
                    withEnv([
                        "DB_URL=${env.DB_URL}",
                        "DB_USER=${env.DB_USER}",
                        "DB_PASS=${env.DB_PASS}",
                        "JWT_SECRET=${env.JWT_SECRET}",
                        "JWT_EXPIRE_SECOND=${env.JWT_EXPIRE_SECOND}"
                    ]) {
                        bat '''
                            set DB_URL=%DB_URL%
                            set DB_USER=%DB_USER%
                            set DB_PASS=%DB_PASS%
                            set JWT_SECRET=%JWT_SECRET%
                            set JWT_EXPIRE_SECOND=%JWT_EXPIRE_SECOND%
                            mvn clean install -Dspring.datasource.url=%DB_URL% -Dspring.datasource.username=%DB_USER% -Dspring.datasource.password=%DB_PASS% -DJWT_SECRET=%JWT_SECRET% -DJWT_EXPIRE_SECOND=%JWT_EXPIRE_SECOND%
                        '''
                    }
                }
            }
        }
        stage('Build Docker Images') {
            steps {
                script {
                    def services = [
                        'api-gateway': 'api-gateway/Dockerfile',
                        'authentication-service': 'authentication-service/Dockerfile',
                        'discovery-server': 'discovery-server/Dockerfile',
                        'product-service': 'product-service/Dockerfile',
                        'report-service': 'report-service/Dockerfile',
                        'sale-service': 'sale-service/Dockerfile',
                        'usermanagement-service': 'usermanagement-service/Dockerfile'
                    ]
                    for (service in services.keySet()) {
                        echo "Building Docker image for ${service}"
                        bat "docker build -t ahmetalicc/${service} -f ${services[service]} ./${service}"
                    }
                }
            }
        }
        stage('Login to DockerHub') {
            steps {
                echo 'Logging in to DockerHub'
                bat 'docker login -u ahmetalicc -p Cr7aliccc'
            }
        }
        stage('Push to DockerHub') {
            steps {
                script {
                    def services = [
                        'api-gateway',
                        'authentication-service',
                        'discovery-server',
                        'product-service',
                        'report-service',
                        'sale-service',
                        'usermanagement-service'
                    ]
                    for (service in services) {
                        echo "Pushing Docker image for ${service}"
                        bat "docker push ahmetalicc/${service}"
                    }
                }
            }
        }
        stage('Docker Compose Up') {
            steps {
                echo 'Starting services with Docker Compose'
                script {
                    withEnv([
                        "DB_URL=${env.DB_URL}",
                        "DB_USER=${env.DB_USER}",
                        "DB_PASS=${env.DB_PASS}",
                        "JWT_SECRET=${env.JWT_SECRET}",
                        "JWT_EXPIRE_SECOND=${env.JWT_EXPIRE_SECOND}"
                    ]) {
                        bat '''
                            set DB_URL=%DB_URL%
                            set DB_USER=%DB_USER%
                            set DB_PASS=%DB_PASS%
                            set JWT_SECRET=%JWT_SECRET%
                            set JWT_EXPIRE_SECOND=%JWT_EXPIRE_SECOND%
                            docker-compose up -d
                        '''
                    }
                }
            }
        }
    }
}
