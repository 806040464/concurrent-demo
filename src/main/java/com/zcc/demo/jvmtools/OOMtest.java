package com.zcc.demo.jvmtools;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoccf
 * @Description:
 * @date: 2020/7/21 20:21
 * @since 1.8
 */
public class OOMtest {
    static class OOMObject {

    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }
}
