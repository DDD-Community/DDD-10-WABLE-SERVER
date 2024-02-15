def repository="ghcr.io/ddd-community/ddd-10-wable-server"

pipeline {
    agent any

    stages {
        stage('Github Pull') {
            steps{
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh """
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
                    docker stop ddd-api;
                    docker rm ddd-api;
                    docker container run -d -t --network=ddd-10-wable-server_default --rm --name ddd-api ${repository}:main;
                """
            }
        }
        stage('Notification') {
            steps {
                sh """
                    curl \
                      -H "Content-Type: application/json" \
                      -d "{\"username\": \"Jenkins harmonika\", \"content\": \"[DEV] - API 디폴로이 되었습니다. \nSwagger : https://harmonika.wo.tc/swagger-ui/index.html\nRepository : https://github.com/DDD-Community/DDD-10-WABLE-SERVER\"}" \
                      ${env.DISCORD_WEBHOOK_URL}
                """
            }
        }
    }
}