# User Service

A Spring Boot microservice for managing user accounts, authentication, and user-related operations in a fintech ecosystem.

## Project Overview

The User Service is a microservice built with Spring Boot that handles:
- User account management
- Profile management
- Email services
- Favorite currencies management
- Image handling
- Integration with Currency Service

## Project Structure

```
src/main/java/com.fintech.user/
├── config/          # Configuration files
├── controller/      # REST controllers
├── dto/            # Data Transfer Objects
├── entity/         # Domain entities
├── repository/     # Data repositories
│   ├── FavoriteCurrenciesRepository
│   ├── ImageRepository
│   └── UserRepository
├── service/        # Business logic
│   ├── feign_clients/
│   │   └── CurrencyFeignClientService
│   ├── mapper/
│   │   └── UserMapper
│   ├── EmailService
│   ├── FavoriteCurrencyService
│   ├── ImageService
│   └── UserService
└── UserApplication.java
```

## Tech Stack

- **Framework**: Spring Boot
- **Build Tool**: Maven
- **Database**: PostgreSQL
- **Container**: Docker
- **Container Registry**: AWS ECR (Public)
- **Orchestration**: Kubernetes (EKS)
- **CI/CD**: Jenkins
- **Code Quality**: SonarQube

## CI/CD Pipeline

Our automated CI/CD pipeline ensures reliable and consistent deployments through multiple stages:

![CI/CD Pipeline](/images/pipeline-diagram.png)

### Pipeline Stages

#### 1. Code Checkout
- Triggered automatically by GitHub webhook
- Fetches the latest code from the repository
- Ensures clean workspace for build process

#### 2. Static Code Analysis (Currently Disabled)
- Performs comprehensive code quality analysis using SonarQube
- Checks for:
    - Code smells
    - Bugs
    - Security vulnerabilities
    - Test coverage
- Enforces quality gates with a 5-minute timeout
- Fails the pipeline if quality criteria aren't met

#### 3. Maven Build
- Compiles the Java source code
- Runs unit tests (currently skipped for faster builds)
- Packages the application into a JAR file
- Validates project structure and dependencies

#### 4. Docker Build & Push
- Creates a Docker image from the built application
- Uses no-cache to ensure fresh builds
- Authenticates with AWS ECR Public registry
- Tags the image as 'latest'
- Pushes the image to the registry for deployment

#### 5. EKS Deployment
- Configures kubectl with cluster credentials
- Creates or updates the fintech namespace
- Applies Kubernetes manifests:
    - ConfigMaps for configuration
    - Secrets for sensitive data
    - Deployment for pod specifications
    - Service for networking
- Verifies deployment status

### Pipeline Cleanup
- Automatically runs after pipeline completion
- Removes local Docker images to free up space
- Cleans the workspace for next run
- Ensures clean state for future builds

## Deployment

The service is deployed to AWS EKS using Kubernetes manifests in the `k8s/` directory:
- `configmap.yaml`: Environment variables and configurations
- `secrets.yaml`: Sensitive data (credentials, tokens)
- `deployment.yaml`: Pod specifications and container settings
- `service.yaml`: Service exposure and networking

## Getting Started

1. **Prerequisites**
    - Java 17+
    - Maven
    - Docker
    - AWS CLI
    - kubectl

2. **Local Development**
   ```bash
   # Build the project
   mvn clean package

   # Run locally
   mvn spring-boot:run
   ```

3. **Docker Build**
   ```bash
   docker build -t user-service .
   ```

4. **Deploy to Kubernetes**
   ```bash
   # Apply manifests
   kubectl apply -f k8s/ -n fintech
   ```

## Monitoring

The service includes monitoring integration for:
- Application metrics
- Performance monitoring
- Error tracking
- Resource utilization

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Contributors

| Name | Role | GitHub |
|------|------|--------|
| Zakariae Azarkan | DevOps Engineer | [@zachary013](https://github.com/zachary013) |
| El Mahdi Id Lahcen | Frontend Developer | [@goalaphx](https://github.com/goalaphx) |
| Hodaifa | Cloud Architect | [@hodaifa-ech](https://github.com/hodaifa-ech) |
| Khalil El Houssine | Backend Developer | [@khalilh2002](https://github.com/khalilh2002) |
| Mohamed Amine BAHASSOU | ML Engineer | [@Medamine-Bahassou](https://github.com/Medamine-Bahassou) |

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.