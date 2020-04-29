package com.cloud.samples_java;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.exception.ObsException;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectRequest;

/**
 * This sample demonstrates how to create an empty folder under specified bucket
 * to OBS using the OBS SDK for Java.
 */
// 创建文件夹的用法
public class CreateFolderSample {
	private static final String endPoint = "https://yourdomainname";

	private static final String ak = "*** Provide your Access Key ***";

	private static final String sk = "*** Provide your Secret Key ***";

	private static ObsClient obsClient;

	private static String bucketName = "my-obs-bucket-demo";

	public static void main(String[] args) throws IOException {

		ObsConfiguration config = new ObsConfiguration();
		config.setSocketTimeout(30000);
		config.setConnectionTimeout(10000);
		config.setEndPoint(endPoint);
		try {

			/*
			 * Constructs a obs client instance with your account for accessing
			 * OBS
			 */
			obsClient = new ObsClient(ak, sk, config);

			/*
			 * Create bucket
			 */
			System.out.println("Create a new bucket for demo\n");
			obsClient.createBucket(bucketName);

			/*
			 * Way 1: Create an empty folder without request body, note that the
			 * key must be suffixed with a slash
			 */
			final String keySuffixWithSlash1 = "MyObjectKey1/";
			obsClient.putObject(bucketName, keySuffixWithSlash1, new ByteArrayInputStream(new byte[0]));
			System.out.println("Creating an empty folder " + keySuffixWithSlash1 + "\n");

			/*
			 * Verify whether the size of the empty folder is zero
			 */
			ObsObject object = obsClient.getObject(bucketName, keySuffixWithSlash1, null);
			System.out.println("Size of the empty folder '" + object.getObjectKey() + "' is "
					+ object.getMetadata().getContentLength());
			object.getObjectContent().close();

			/*
			 * Way 2: Create an empty folder without request body, note that the
			 * key must be suffixed with a slash
			 */
			final String keySuffixWithSlash2 = "MyObjectKey2/";
			PutObjectRequest request = new PutObjectRequest();
			request.setBucketName(bucketName);
			request.setObjectKey(keySuffixWithSlash2);
			request.setInput(new ByteArrayInputStream(new byte[0]));
			obsClient.putObject(request);
			System.out.println("Creating an empty folder " + keySuffixWithSlash2 + "\n");

			/*
			 * Verify whether the size of the empty folder is zero
			 */
			object = obsClient.getObject(bucketName, keySuffixWithSlash2, null);
			System.out.println("Size of the empty folder '" + object.getObjectKey() + "' is "
					+ object.getMetadata().getContentLength());
			object.getObjectContent().close();

		} catch (ObsException e) {
			System.out.println("Response Code: " + e.getResponseCode());
			System.out.println("Error Message: " + e.getErrorMessage());
			System.out.println("Error Code:       " + e.getErrorCode());
			System.out.println("Request ID:      " + e.getErrorRequestId());
			System.out.println("Host ID:           " + e.getErrorHostId());
		} finally {
			if (obsClient != null) {
				try {
					/*
					 * Close obs client
					 */
					obsClient.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
