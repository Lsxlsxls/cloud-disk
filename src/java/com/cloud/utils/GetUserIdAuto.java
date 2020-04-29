package com.cloud.utils;

public class GetUserIdAuto {

    private static int userid = 1;
    public int getUserid_auto() {

        userid++;
        return userid;
    }
}
