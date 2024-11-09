# spring-boot-kotlin-s3

### Things todo list

1. Clone this repository: `git clone https://github.com/hendisantika/spring-boot-kotlin-s3.git`
2. Navigate to the folder: `cd spring-boot-kotlin-s3`
3. Change AWS S3 Credentials with yours
4. Run the application: `gradle clean bootRun`

### List All Buckets

```shell
curl --location --request GET 'http://localhost:8080/test'
``` 

### Create New S3 Bucket

```shell
curl --location --request POST 'http://localhost:8080/buckets' \
--header 'Content-Type: application/json' \
--data-raw '{
"bucketName": "testing"
}'
```

#### Create Object In The Bucket

````shell
curl --location --request POST 'http://localhost:8080/buckets/your-awesome-name/objects' \
--header 'Content-Type: application/json' \
--data-raw '{
"objectName": "file-example.txt",
"content": "My file content"
}'
````

### List Objects From The Bucket

```shell
curl --location --request GET 'http://localhost:8080/buckets/your-awesome-name/objects'
```

### Fetch The Object From S3 Bucket

```shell
curl --location --request GET 'http://localhost:8080/buckets/your-awesome-name/objects/file-example.txt'
```

# Response:

"My file content"

Delete S3 Bucket


