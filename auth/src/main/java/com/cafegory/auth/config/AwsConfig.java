package com.cafegory.auth.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.cafegory.auth.infrastructure.aws.AwsS3HandlerImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

	@Value("${cloud.aws.credentials.access-key-id}")
	private String accessKey;

	@Value("${cloud.aws.credentials.secret-access-key}")
	private String secretAccessKey;

	@Value("${cloud.aws.region.static}")
	private String region;

	@Bean
	public AmazonS3 amazonS3() {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretAccessKey);

		return AmazonS3ClientBuilder
			.standard()
			.withCredentials(new AWSStaticCredentialsProvider(credentials))
			.withRegion(Regions.AP_NORTHEAST_2)
			.build();
	}

	@Bean
	public AwsS3HandlerImpl awsS3Handler() {
		return new AwsS3HandlerImpl(amazonS3());
	}

}
