package com.atguigu.spzx.manager.service.Impl;

import cn.hutool.core.date.DateUtil;
import com.atguigu.spzx.manager.properties.MinioProperties;
import com.atguigu.spzx.manager.service.FileUploadService;
import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

/**

 */
@Service
public class FileUploadServiceImpl implements FileUploadService {


    @Autowired
    private MinioProperties minioProperties ;

    @Override
    public String fileUpload(MultipartFile multipartFile) {


        /**
         1。// 创建一个Minio的客户端对象 MinioClient minioClient
         2。// 判断桶是否存在 if (!found)
                    // 如果不存在，那么此时就创建一个新的桶
                    // 如果存在打印信息
         3。// 设置存储对象名称             //20230801/443e1e772bef482c95be28704bec58a901.jpg

         * */

        try {
            //1
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioProperties.getEndpointUrl())
                    .credentials(minioProperties.getAccessKey(), minioProperties.getSecreKey())
                    .build();
            System.out.println("客户端链接成功");

            //2
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            } else {
                System.out.println("Bucket 'spzx-bucket' already exists.");
            }

            //3
            String dateDir = DateUtil.format(new Date(), "yyyyMMdd");
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String fileName = dateDir+"/"+uuid+multipartFile.getOriginalFilename();
            System.out.println(fileName);

            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                    .object(fileName)
                    .build();
            minioClient.putObject(putObjectArgs) ;

            return minioProperties.getEndpointUrl() + "/" + minioProperties.getBucketName() + "/" + fileName ;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        /**  官网代码

         try {

         // 创建一个Minio的客户端对象
         MinioClient minioClient = MinioClient.builder()
         .endpoint("http://127.0.0.1:9000")
         .credentials("minioadmin", "minioadmin")
         .build();

         boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("spzx-bucket").build());

         // 如果不存在，那么此时就创建一个新的桶
         if (!found) {
         minioClient.makeBucket(MakeBucketArgs.builder().bucket("spzx-bucket").build());
         } else {  // 如果存在打印信息
         System.out.println("Bucket 'spzx-bucket' already exists.");
         }

         FileInputStream fis = new FileInputStream("D://01.jpg") ;
         PutObjectArgs putObjectArgs = PutObjectArgs.builder()
         .bucket("spzx-bucket")
         .stream(fis, fis.available(), -1)
         .object("01.jpg")
         .build();
         minioClient.putObject(putObjectArgs) ;

         // 构建fileUrl
         String fileUrl = "http://127.0.0.1:9000/spzx-bucket/01.jpg" ;
         System.out.println(fileUrl);
         return fileUrl;
         } catch (Exception e) {
         throw new RuntimeException(e);
         }
         * */
    }
}
