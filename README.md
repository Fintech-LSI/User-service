<div align="center">

# 👤 User Service

[![Build Status](https://img.shields.io/jenkins/build?jobUrl=your-jenkins-url)](https://jenkins-url)
[![Quality Gate Status](https://img.shields.io/sonar/quality_gate/user-service?server=http://54.86.47.1:9000)](http://54.86.47.1:9000)
[![Docker Pulls](https://img.shields.io/docker/pulls/your-repo/user-service)](https://hub.docker.com/r/your-repo/user-service)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
</div>

## 📋 Table of Contents
- [Overview](#-overview)
- [Features](#-features)
- [Architecture](#-architecture)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Pipeline](#-pipeline)
- [Getting Started](#-getting-started)
- [API Documentation](#-api-documentation)
- [Configuration](#-configuration)
- [Monitoring](#-monitoring)
- [Deployment](#-deployment)
- [Contributing](#-contributing)
- [Team](#-team)
- [License](#-license)

## 🎯 Overview

A comprehensive user management microservice built with Spring Boot that handles user accounts, profiles, and preferences within the FinTechPro ecosystem. This service provides centralized user data management with seamless integration with other platform services.

## ✨ Features

- 👤 **User Account Management**
    - Registration
    - Profile updates
    - Account status
- 📧 **Email Services**
    - Verification
    - Notifications
    - Password reset
- 🎯 **Favorite Currencies**
    - Add/Remove favorites
    - Preference tracking
    - Real-time updates
- 🖼️ **Image Handling**
    - Profile pictures
    - Document uploads
    - Secure storage
- 🔄 **Service Integration**
    - Currency service connectivity
    - Real-time data sync
- 📊 **User Analytics**
    - Activity tracking
    - Usage patterns
    - Preference analysis

## 🏗 Architecture

The service follows a microservices architecture pattern:

```
                                  ┌─────────────┐
                             ┌──▶│   Currency   │
┌──────────────┐    REST    │    │   Service   │
│    User      │◀──────────▶│    └─────────────┘
│   Service    │            │    ┌─────────────┐
└──────────────┘    AWS     └──▶│    S3 for    │
                   Services      │   Images     │
                                └─────────────┘
```

## 🛠 Tech Stack

<div align="center">
  <img src="https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL" />
  <img src="https://img.shields.io/badge/Amazon_AWS-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white" alt="AWS" />
  <img src="https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white" alt="Docker" />
  <img src="https://img.shields.io/badge/kubernetes-326ce5.svg?&style=for-the-badge&logo=kubernetes&logoColor=white" alt="Kubernetes" />
  <img src="https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white" alt="Jenkins" />
</div>

## 📂 Project Structure

```
src/main/java/com.fintech.user/
├── 📊 config/               # Configuration files
├── 🎮 controller/           # REST controllers
├── 📦 dto/                 # Data Transfer Objects
├── 🏢 entity/              # Domain entities
├── 📚 repository/          # Data repositories
│   ├── FavoriteCurrenciesRepository
│   ├── ImageRepository
│   └── UserRepository
├── 🔧 service/             # Business logic
│   ├── feign_clients/
│   │   └── CurrencyFeignClientService
│   ├── mapper/
│   │   └── UserMapper
│   ├── EmailService
│   ├── FavoriteCurrencyService
│   ├── ImageService
│   └── UserService
└── 🚀 UserApplication.java
```

## 🔄 Pipeline

Our robust CI/CD pipeline ensures reliable deployments:

![CI/CD Pipeline](/images/pipeline-diagram.png)


1. 📥 **Code Checkout**
    - Automated GitHub webhook triggers
    - Clean workspace preparation

2. 🔍 **SonarQube Analysis**
    - Code quality verification
    - Security vulnerability scanning
    - Test coverage assessment
    - Quality gate enforcement

3. 🏗️ **Maven Build**
    - Source compilation
    - Unit test execution
    - JAR packaging
    - Dependency validation

4. 🐳 **Docker Build & Push**
    - No-cache image building
    - AWS ECR authentication
    - Image tagging and pushing

5. ☸️ **EKS Deployment**
    - Kubernetes manifest application
    - Deployment verification
    - Health check monitoring

## 🚀 Getting Started

```bash
# Clone the repository
git clone https://github.com/your-org/user-service.git

# Navigate to project directory
cd user-service

# Install dependencies and build
mvn clean install

# Run locally
mvn spring-boot:run -Dspring.profiles.active=local

# Run tests
mvn test

# Build Docker image
docker build -t user-service .
```



## 📊 Monitoring

Integrated monitoring includes:
- 📈 Application metrics via Prometheus
- 🔍 Performance tracking through Grafana
- ⚡ Resource utilization monitoring
- 🚨 Error tracking and alerting
- 📝 Automated logging

## 👥 Team

| Avatar                                                                                                  | Name | Role | GitHub |
|---------------------------------------------------------------------------------------------------------|------|------|--------|
| <img src="https://github.com/zachary013.png" width="50" height="50" style="border-radius: 50%"/>        | Zakariae Azarkan | DevOps Engineer | [@zachary013](https://github.com/zachary013) |
| <img src="https://github.com/goalaphx.png" width="50" height="50" style="border-radius: 50%"/>          | El Mahdi Id Lahcen | Frontend Developer | [@goalaphx](https://github.com/goalaphx) |
| <img src="https://github.com/hodaifa-ech.png" width="50" height="50" style="border-radius: 50%"/>       | Hodaifa | Cloud Architect | [@hodaifa-ech](https://github.com/hodaifa-ech) |
| <img src="https://github.com/khalilh2002.png" width="50" height="50" style="border-radius: 50%"/>       | Khalil El Houssine | Backend Developer | [@khalilh2002](https://github.com/khalilh2002) |
| <img src="https://github.com/Medamine-Bahassou.png" width="50" height="50" style="border-radius: 50%"/> | Mohamed Amine BAHASSOU | ML Engineer | [@Medamine-Bahassou](https://github.com/Medamine-Bahassou) |

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
<div align="center">
  <p>Built with ❤️ by the FinTech Team</p>
</div>