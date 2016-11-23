package com.wangyi;

import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by tengyu on 2016/8/2.
 */
public class liaoli {
    public static void main(String[] args) {
        HashSet<String> list = new HashSet<>();
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String[] strings = in.nextLine().split(" ");
            for (int i = 0; i < strings.length; i++) {
                list.add(strings[i]);
            }
        }
        System.out.println(list.size());

    }
}
