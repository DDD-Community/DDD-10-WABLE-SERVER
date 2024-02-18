package com.wable.harmonika.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;

@Configuration
public class AWSS3Config {

//    @Value("${cloud.aws.s3.credentials.accesskey}")
//    private String accessKey;
//
//    @Value("${cloud.aws.s3.credentials.secretkey}")
//    private String secretKey;

//    @Bean
//    public AwsCredentials basicAWSCredentials() {
//        return AwsBasicCredentials.create(accessKey, secretKey);
//    }

//    @Bean
//    public S3Presigner s3Presigner(AwsCredentials awsCredentials) {
//        return S3Presigner.builder()
//                .region(Region.AP_NORTHEAST_2)
//                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
//                .build();
//    }
//
//    @Bean
//    public S3Client s3Client (AwsCredentials awsCredentials) {
//        return S3Client.builder()
//                .region(Region.AP_NORTHEAST_2)
//                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
//                .build();
//    }
}
