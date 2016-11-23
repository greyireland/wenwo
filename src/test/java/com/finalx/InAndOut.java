package com.finalx;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by tengyu on 2016/8/11.
 */
public class InAndOut {
    public static void main(String[] args) throws IOException {
        testIn();
    }

    public void test() {
        System.out.println("helloworld");
    }
    public static void testIn() throws IOException {
        Object object,object2;
        int n=0;
        while (true) {
            object=(char)System.in.read();

            if (object==' ') {
                System.out.println("空格");
            } else if (object != '\n') {
                System.out.println(String.valueOf(object));
            } else if(object=='\n') {
                n++;
                if(n==2){
                    break;
                }
            }

        }
    }

    public static void testIn2() throws IOException {
        //Scanner s = new Scanner("123 asdf sd 45 789 sdf asdfl,sdf.sdfl,asdf    ......asdfkl    las");
        Scanner s = new Scanner(System.in);
        Scanner s2;
        //s.useDelimiter(" |,|\\.");
        while (s.hasNext()) {

            System.out.println(s.next().toString());
            if(new Scanner(System.in).hasNext()){
                break;
            }
        }
    }

    public static void testIn3() throws IOException {
        char s[]=new char[20];

        System.out.println("input something:");

        int i=-1;
        int n=0;

        do{
            n++;
            i++;

            s[i]=(char) System.in.read();

            System.out.println(s[i]);

        }while(s[i]!='\n');

        System.out.println(i);
        System.out.println(n);

    }
}
