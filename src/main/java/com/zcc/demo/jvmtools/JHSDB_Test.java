package com.zcc.demo.jvmtools;

/**
 * @author zhaoccf
 * @Description:
 * @date: 2020/7/9 13:38
 * @since 1.8
 */
public class JHSDB_Test {

    static class Test {
        static ObjectHolder staticObject = new ObjectHolder();
        ObjectHolder instanceObject = new ObjectHolder();

        void foo() {
            ObjectHolder localObject = new ObjectHolder();
            System.out.println("done");
        }
    }

    private static class ObjectHolder {}

    public static void main(String[] args) {
        Test test = new JHSDB_Test.Test();
        test.foo();
    }
}
