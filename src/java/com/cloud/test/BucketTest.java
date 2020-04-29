package com.cloud.test;

import com.cloud.utils.ObsBucketOperation;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.cloud.utils.ObsBucketOperation;
import java.util.Scanner;

public class BucketTest {
    public static void main(String[] args) throws IOException {
        ObsBucketOperation bucketOperation = new ObsBucketOperation();
//         Scanner sc = new Scanner(System.in);
//         System.out.println("请输入您要创建的桶名称");
//         String bucketName = sc.next();
//        // 创建桶
////         String bucketName = JOptionPane.showInputDialog("请输入您要创建的桶名称");
//         bucketOperation.craetBucket(bucketName);
//         System.out.println("完成");
//         sc.close();
//         System.exit(0);

//         查询
//        bucketOperation.getAllBucket();

        // 删除
        String bucketName = JOptionPane.showInputDialog("请输入您要删除的桶的名称");
        bucketOperation.removeBucket(bucketName);
        System.out.println("完成");

        bucketOperation.getAllBucket();
        System.exit(0);
    }
}
