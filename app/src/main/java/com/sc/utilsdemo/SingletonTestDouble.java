package com.sc.utilsdemo;

public class SingletonTestDouble {

    private static volatile SingletonTestDouble singleton;

    private SingletonTestDouble() {
    }

    public static SingletonTestDouble getInstance() {
        if (singleton == null) {
            synchronized (SingletonTestDouble.class) {
                if (singleton == null) {
                    singleton = new SingletonTestDouble();
                }
            }
        }
        return singleton;
    }
}