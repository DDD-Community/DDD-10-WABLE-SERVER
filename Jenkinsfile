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
    }
}