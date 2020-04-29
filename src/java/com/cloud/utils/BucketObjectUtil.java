package com.cloud.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.obs.services.ObsClient;
import com.obs.services.model.DeleteObjectResult;
import com.obs.services.model.HttpMethodEnum;
import com.obs.services.model.ObjectListing;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectResult;
import com.obs.services.model.TemporarySignatureRequest;
import com.obs.services.model.TemporarySignatureResponse;

/**
 * 作者: 杜丹东 D.D.D 日期: 2020年4月7日下午3:44:27
 */
public class BucketObjectUtil {
    // 官方帮助文档
    // https://obssdk.obs.cn-north-1.myhuaweicloud.com/apidoc/cn/java/index.html
    // 示例代码
    // https://support.huaweicloud.com/sdk-java-devg-obs/obs_21_0002.html
    private static final String endPoint = "obs.cn-north-4.myhuaweicloud.com";

    private static final String ak = "WAJJBEXJXZS9HWG24PDW";

    private static final String sk = "3xMojft9FJsrRoUb8InUaKKWbGenXiVsnms1WPlV";

    private static final String bucketName = "obs-skd-04";

    public ObsClient getInstance() {
        return new ObsClient(ak, sk, endPoint);
    }
    public Integer uploadFile(InputStream is, String objectKey) throws IOException {
        // is 对象的流
        ObsClient obsClient = getInstance();
        boolean flag = obsClient.doesObjectExist(bucketName, objectKey);
        PutObjectResult result = null;
        // 根据业务需求，决定是否覆盖
        if (flag) {
            return 0;
        } else {
            // 已存在的文件会被覆盖
            result = obsClient.putObject(bucketName, objectKey, is);
        }
        obsClient.close();
        return result.getStatusCode();
    }

//    public List<ObsObject> getAllFileInfo() throws IOException {
//        ObsClient obsClient = getInstance();
//        ObjectListing objectList = obsClient.listObjects(bucketName);
//        List<ObsObject> list = objectList.getObjects();
//        obsClient.close();
//        return list;
//    }
//
//    public boolean removeFile(String objectKey) throws IOException {
//        ObsClient obsClient = getInstance();
//        boolean flag = obsClient.doesObjectExist(bucketName, objectKey);
//        DeleteObjectResult result = null;
//        if (flag) {
//            result = obsClient.deleteObject(bucketName, objectKey);
//        }
//        obsClient.close();
//        return result.isDeleteMarker();// 是否被标记能被删除
//    }
//
//    // 获取文件对象---下载
//    // 通常在网上你们见到的下载，是从服务器直接下载，现在文件存在obs上，所以下载的业务流程会发生变化
//    public ObsObject getFile(String objectKey) {
//        ObsClient obsClient = getInstance();
//        boolean flag = obsClient.doesObjectExist(bucketName, objectKey);
//        if (flag) {
//            ObsObject object = obsClient.getObject(bucketName, objectKey);
//            return object;
//        }
//        return null;
//    }
//
//    // 预览---授权访问---你们在网上看到的跟这次不一样，因为网上，文件是存在服务器上的，谁都能看，现在是在obs
//    // 预览只支持流式文件
//    public String preview(String objectKey) throws IOException {
//        ObsClient obsClient = getInstance();
//        // 300 有效时间
//        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, 300);
//        request.setBucketName(bucketName);
//        request.setObjectKey(objectKey);
//        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
//        obs、Client.close();、
//        return response.getSignedUrl();
//    }
}
