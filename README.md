# spring-boot-kotlin-s3

[![Java CI with Gradle](https://github.com/hendisantika/spring-boot-kotlin-s3/actions/workflows/gradle.yml/badge.svg)](https://github.com/hendisantika/spring-boot-kotlin-s3/actions/workflows/gradle.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Kotlin](https://img.shields.io/badge/Kotlin-2.4-7F52FF?logo=kotlin)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F?logo=springboot)

Simple REST API built with **Spring Boot + Kotlin** to manage **AWS S3** buckets and objects using
[Spring Cloud AWS](https://awspring.io/).

## Tech Stack

| Component        | Version |
|------------------|---------|
| Java (toolchain) | 25      |
| Kotlin           | 2.4.x   |
| Spring Boot      | 4.1.x   |
| Spring Cloud AWS | 4.2.x   |
| Gradle (wrapper) | 9.x     |

## Prerequisites

- JDK 25 (e.g. Temurin 25). Gradle toolchains will use / provision JDK 25 automatically.
- An AWS account with an IAM user/role that has S3 permissions.

## Getting Started

1. Clone this repository:
   ```shell
   git clone https://github.com/hendisantika/spring-boot-kotlin-s3.git
   cd spring-boot-kotlin-s3
   ```
2. Configure your AWS credentials & region in `src/main/resources/application.yml`
   (`spring.cloud.aws.credentials.*` and `spring.cloud.aws.s3.region`).
   > ⚠️ Never commit real AWS keys. Prefer environment variables, e.g.
   > `access-key: ${AWS_ACCESS_KEY_ID}` / `secret-key: ${AWS_SECRET_ACCESS_KEY}`.
3. Run the application:
   ```shell
   ./gradlew bootRun
   ```
4. Build & run tests:
   ```shell
   ./gradlew clean build
   ```

The application runs on `http://localhost:8080`.

## API Endpoints

| Method   | Endpoint                                   | Description                          |
|----------|--------------------------------------------|--------------------------------------|
| `GET`    | `/buckets`                                 | List all buckets                     |
| `POST`   | `/buckets`                                 | Create a new bucket                  |
| `POST`   | `/buckets/{bucketName}/objects`            | Create an object in a bucket         |
| `GET`    | `/buckets/{bucketName}/objects`            | List objects in a bucket             |
| `GET`    | `/buckets/{bucketName}/objects/{objectName}` | Fetch object content               |
| `DELETE` | `/buckets/{bucketName}`                    | Delete all objects & the bucket      |

You can also use the [`S3-Upload.http`](S3-Upload.http) file from IntelliJ IDEA.

### List All Buckets

```shell
curl --location --request GET 'http://localhost:8080/buckets'
```

### Create New S3 Bucket

```shell
curl --location --request POST 'http://localhost:8080/buckets' \
--header 'Content-Type: application/json' \
--data-raw '{
  "bucketName": "testing"
}'
```

### Create Object In The Bucket

```shell
curl --location --request POST 'http://localhost:8080/buckets/your-awesome-name/objects' \
--header 'Content-Type: application/json' \
--data-raw '{
  "objectName": "file-example.txt",
  "content": "My file content"
}'
```

### List Objects From The Bucket

```shell
curl --location --request GET 'http://localhost:8080/buckets/your-awesome-name/objects'
```

### Fetch The Object From S3 Bucket

```shell
curl --location --request GET 'http://localhost:8080/buckets/your-awesome-name/objects/file-example.txt'
```

Response:

```
My file content
```

### Delete S3 Bucket

Deletes every object in the bucket, then the bucket itself.

```shell
curl --location --request DELETE 'http://localhost:8080/buckets/your-awesome-name'
```

## CI/CD

- **GitHub Actions** ([`.github/workflows/gradle.yml`](.github/workflows/gradle.yml)) builds and tests the project
  with Temurin JDK 25 on every push / pull request to `main`, and submits the dependency graph for Dependabot alerts.
- **Dependabot** ([`.github/dependabot.yml`](.github/dependabot.yml)) checks Gradle dependencies and GitHub Actions
  daily (05:00 Asia/Jakarta), grouping related updates (Spring Boot, Kotlin, Spring Cloud AWS, test libs,
  minor/patch, GitHub Actions) into single PRs with labels and conventional commit prefixes.
