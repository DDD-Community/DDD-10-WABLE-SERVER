def repository="ghcr.io/ddd-community/ddd-10-wable-server"

pipeline {
    agent any

    stages {
        stage('Start Notification') {
            steps {
                script {
                    def jsonData = '{"username":"Jenkins harmonika", "content":"[DEV] - API 디폴로이중..."}'
                    sh """
                    curl -d '${jsonData}' \
                        -H 'Content-Type: application/json' \
                        -X POST ${env.DISCORD_WEBHOOK_URL}
                    """
                }
            }
        }
        stage('Github Pull') {
            steps{
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh """
                export ENCRYPTOR_PASSWORD=${env.ENCRYPTOR_PASSWORD}
                ./gradlew clean build
                """
            }
        }
        stage('Jib Docker Repository Push') {
            steps {
                sh """
                    ./gradlew jib -Djib.from.platforms=linux/amd64,linux/arm64
                """
            }
        }
        stage('Deploy') {
            steps {
                // TOOD: shell script 추가 해야함
                sh """
                    docker pull ${repository}:main;
                    docker stop ddd-api || true;
                    docker rm ddd-api || true;
                    docker container run -d -t -e ENCRYPTOR_PASSWORD=${env.ENCRYPTOR_PASSWORD} --network=ddd-10-wable-server_default --restart always --name ddd-api ghcr.io/ddd-community/ddd-10-wable-server:main;
                """
            }
        }
        stage('Deploy Notification') {
            steps {
                script {
                    def jsonData = '{"username":"Jenkins harmonika", "content":"[DEV] - API 디폴로이 되었습니다.\\n Swagger : https://waggle.reactjs.kr/api/swagger-ui/index.html  \\nRepository : https://github.com/DDD-Community/DDD-10-WABLE-SERVER"}'
                    sh """
                    curl -d '${jsonData}' \
                        -H 'Content-Type: application/json' \
                        -X POST ${env.DISCORD_WEBHOOK_URL}
                    """
                }
            }
        }
    }
}
