package com.cloud.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.AccessControlList;
import com.obs.services.model.HeaderResponse;
import com.obs.services.model.ListBucketsRequest;
import com.obs.services.model.ObsBucket;
import com.obs.services.model.StorageClassEnum;

import java.io.IOException;

public class ObsBucketOperation {
    // private static final String NAME = "nicai";
    // 耦合性太高

    private static final String endPoint = "obs.cn-north-4.myhuaweicloud.com";

    private static final String ak = "WAJJBEXJXZS9HWG24PDW";

    private static final String sk = "3xMojft9FJsrRoUb8InUaKKWbGenXiVsnms1WPlV";

//    private static final String bucketName = "obs-skd-04";

    private static final String bucketLocation = "cn-north-4";

    public ObsClient getInstance() {
        return new ObsClient(ak, sk, endPoint);
    }

    public void craetBucket(String bucketName) throws IOException {
        ObsClient obsClient = getInstance();
        ObsBucket obsBucket = new ObsBucket();
        obsBucket.setBucketName(bucketName);
        // 设置桶访问权限为公共读，默认是私有读写
        obsBucket.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ);
        // 设置桶的存储类型为归档存储
        obsBucket.setBucketStorageClass(StorageClassEnum.STANDARD);
        // 设置桶区域位置
        obsBucket.setLocation(bucketLocation);
        // 创建桶
        try {
            // 创建桶成功
            HeaderResponse response = obsClient.createBucket(obsBucket);
            System.out.println(response.getRequestId());
        } catch (ObsException e) {
            // 创建桶失败
            System.out.println("HTTP Code: " + e.getResponseCode());
            System.out.println("Error Code:" + e.getErrorCode());
            System.out.println("Error Message: " + e.getErrorMessage());
            System.out.println("Request ID:" + e.getErrorRequestId());
            System.out.println("Host ID:" + e.getErrorHostId());
        }
        obsClient.close();

    }

    // 方法名:首字母小写，以驼峰式命名
    public void getAllBucket() throws IOException {
        // 创建ObsClient实例
        ObsClient obsClient = getInstance();
        // 列举桶
        ListBucketsRequest request = new ListBucketsRequest();
        request.setQueryLocation(true);
        List<ObsBucket> buckets = obsClient.listBuckets(request);
        for (ObsBucket bucket : buckets) {
            System.out.println("BucketName:" + bucket.getBucketName());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // System.out.println("CreationDate:" + bucket.getCreationDate());
            System.out.println("创建日期:" + sdf.format(bucket.getCreationDate()));
            System.out.println("Location:" + bucket.getLocation());
            System.out.println();
        }
        obsClient.close();

    }

    public void removeBucket(String bucketName) throws IOException {
        // 创建ObsClient实例
        ObsClient obsClient = getInstance();
        boolean exists = obsClient.headBucket(bucketName);
        if (exists) {
            // 删除桶
            obsClient.deleteBucket(bucketName);
            obsClient.close();
        } else {
            System.out.println("没有您要删除的桶");
        }
    }

}
