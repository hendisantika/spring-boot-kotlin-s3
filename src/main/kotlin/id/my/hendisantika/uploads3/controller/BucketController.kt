package id.my.hendisantika.uploads3.controller

import org.springframework.web.bind.annotation.*
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.*
import kotlin.text.Charsets.UTF_8

/**
 * Created by IntelliJ IDEA.
 * Project : upload-s3
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 09/11/24
 * Time: 07.40
 * To change this template use File | Settings | File Templates.
 */

typealias PutObjectRequestBody = software.amazon.awssdk.core.sync.RequestBody

data class BucketRequest(val bucketName: String)

data class ObjectRequest(val objectName: String, val content: String)

@RestController
@RequestMapping("/buckets")
class BucketController(private val s3Client: S3Client) {
    @GetMapping
    fun listBuckets(): List<String> {
        val response = s3Client.listBuckets()

        return response.buckets()
            .mapIndexed { index, bucket ->
                "Bucket #${index + 1}: ${bucket.name()}"
            }
    }

    @PostMapping
    fun createBucket(@RequestBody request: BucketRequest) {
        val createBucketRequest = CreateBucketRequest.builder()
            .bucket(request.bucketName)
            .build()

        s3Client.createBucket(createBucketRequest)
    }

    @PostMapping("/{bucketName}/objects")
    fun createObject(@PathVariable bucketName: String, @RequestBody request: ObjectRequest) {
        val createObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(request.objectName)
            .build()

        val fileContent = PutObjectRequestBody.fromString(request.content)

        s3Client.putObject(createObjectRequest, fileContent)
    }

    @GetMapping("/{bucketName}/objects")
    fun listObjects(@PathVariable bucketName: String): List<String> {
        val listObjectsRequest = ListObjectsRequest.builder()
            .bucket(bucketName)
            .build()

        val response = s3Client.listObjects(listObjectsRequest)

        return response.contents()
            .map { s3Object -> s3Object.key() }
    }

    @GetMapping("/{bucketName}/objects/{objectName}")
    fun getObject(@PathVariable bucketName: String, @PathVariable objectName: String): String {
        val getObjectRequest = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(objectName)
            .build()

        val response = s3Client.getObjectAsBytes(getObjectRequest)

        return response.asString(UTF_8)
    }

    @DeleteMapping("/{bucketName}")
    fun deleteBucket(@PathVariable bucketName: String) {
        val listObjectsRequest = ListObjectsRequest.builder()
            .bucket(bucketName)
            .build()

        val listObjectsResponse = s3Client.listObjects(listObjectsRequest)

        val allObjectsIdentifiers = listObjectsResponse.contents()
            .map { s3Object ->
                ObjectIdentifier.builder()
                    .key(s3Object.key())
                    .build()
            }

        val del = Delete.builder()
            .objects(allObjectsIdentifiers)
            .build()

        val deleteObjectsRequest = DeleteObjectsRequest.builder()
            .bucket(bucketName)
            .delete(del)
            .build()

        s3Client.deleteObjects(deleteObjectsRequest)


        val deleteBucketRequest = DeleteBucketRequest.builder()
            .bucket(bucketName)
            .build()

        s3Client.deleteBucket(deleteBucketRequest)
    }
}
