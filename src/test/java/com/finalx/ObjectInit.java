package com.finalx;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tengyu on 2016/7/14.
 */
public class ObjectInit {
    public static void print(Object o) {
        System.out.println(o.toString());
    }
    public static void main(String[] args) {
        String[] str=new String[]{"hello","world"};
        String[] str2={"a","b"};
        //容器zaiinsert ABC初始化
        List<String > list=new ArrayList<>();
        list=Arrays.asList("a","b");
        Arrays.asList(1, 2, 3, 4);

        Iterator<String> iterator=list.iterator();//step1
        while (iterator.hasNext()) {//step2
            print(iterator.next());//step3

        }
    }
}
