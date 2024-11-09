package id.my.hendisantika.uploads3.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import software.amazon.awssdk.services.s3.S3Client

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
class BucketController(private val s3Client: S3Client)
