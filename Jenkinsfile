pipeline {
    agent any

    environment {
        AWS_ACCOUNT_ID = credentials('AWS_ACCOUNT_ID')
        AWS_REGION = 'us-east-1'
        ECR_REPO = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
        IMAGE_NAME = 'user-service'
        IMAGE_TAG = "${BUILD_NUMBER}"
        EKS_CLUSTER_NAME = 'your-eks-cluster'
        NAMESPACE = 'user-service'
        SONAR_PROJECT_KEY = 'user-service'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('SonarQube Analysis') {
            environment {
                SONAR_TOKEN = credentials('SONAR_TOKEN')
            }
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                        -Dsonar.host.url=http://your-sonarqube-url:9000 \
                        -Dsonar.login=${SONAR_TOKEN}
                    """
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_REPO}"
                    docker.build("${ECR_REPO}/${IMAGE_NAME}:${IMAGE_TAG}")
                }
            }
        }

        stage('Push to ECR') {
            steps {
                script {
                    sh """
                        docker push ${ECR_REPO}/${IMAGE_NAME}:${IMAGE_TAG}
                        docker tag ${ECR_REPO}/${IMAGE_NAME}:${IMAGE_TAG} ${ECR_REPO}/${IMAGE_NAME}:latest
                        docker push ${ECR_REPO}/${IMAGE_NAME}:latest
                    """
                }
            }
        }

        stage('Deploy to EKS') {
            steps {
                script {
                    sh "aws eks update-kubeconfig --region ${AWS_REGION} --name ${EKS_CLUSTER_NAME}"
                    sh "kubectl create namespace ${NAMESPACE} --dry-run=client -o yaml | kubectl apply -f -"

                    sh """
                        kubectl apply -f k8s/ -n ${NAMESPACE}
                        kubectl rollout status deployment/user-service -n ${NAMESPACE}
                    """
                }
            }
        }
    }

    post {
        always {
            cleanWs()
            sh """
                docker rmi ${ECR_REPO}/${IMAGE_NAME}:${IMAGE_TAG} || true
                docker rmi ${ECR_REPO}/${IMAGE_NAME}:latest || true
            """
        }
        failure {
            sh "kubectl rollout undo deployment/user-service -n ${NAMESPACE} || true"
        }
    }
}