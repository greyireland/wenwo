package com.wangyi;

public class HasStatic {
    private static int x = 100;

    public static void main(String args[]) {
        HasStatic hs1 = new HasStatic();
        x=200;
        x++;
        HasStatic hs2 = new HasStatic();
        x++;
        hs1 = new HasStatic();
        x++;
        HasStatic.x--;
        System.out.println("x=" + x);
        for (int i = 0; i < 10; i++) {
            System.out.println("helloworld");

        }
    }
}