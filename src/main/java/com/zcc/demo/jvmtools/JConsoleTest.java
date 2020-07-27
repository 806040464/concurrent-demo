package com.zcc.demo.jvmtools;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoccf
 * @Description:
 * @date: 2020/7/9 15:58
 * @since 1.8
 */
public class JConsoleTest {
    static class OOMObject {
        public byte[] placeholder = new byte[64 * 1024];
    }

    public static void fillHeap(int num) throws InterruptedException {
        List<OOMObject> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Thread.sleep(50);
            list.add(new OOMObject());
        }
//        System.gc();
//        Thread.sleep(10000);
    }

    public static void main(String[] args) throws InterruptedException {
        fillHeap(1000);
        System.gc();
        Thread.sleep(10000);
    }
}
