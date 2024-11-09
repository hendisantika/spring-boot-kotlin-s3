package id.my.hendisantika.uploads3.controller

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

class BucketController
