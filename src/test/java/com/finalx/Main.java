package com.finalx;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int t=cin.nextInt();

        ArrayList<StringBuilder> list = new ArrayList<>();
        for (int i = 0; i < t; i++) {
            int n=cin.nextInt();
            int k=cin.nextInt();
            int[] arr=new int[2*n];
            for (int j = 0; j < 2*n; j++) {
                arr[j]=cin.nextInt();
            }
            merge(arr,k);
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 2*n; j++) {
                if(j==2*n-1){
                    sb.append(arr[j]);
                    //System.out.print(arr[j]);
                }else{
                    sb.append(arr[j]+" ");
                    //System.out.print(arr[j]+"");
                }
            }
            list.add(sb);
        }
        for (StringBuilder str : list) {
            System.out.println(str);
        }
    }

    public static void merge(int[] arr, int k) {
        int[] l=new int[arr.length];
        int[] r=new int[arr.length];
        int p=0,q=0;
        for (int i = 0; i < arr.length/2; i++) {
            l[p++]=arr[i];
        }

        for (int i = arr.length/2; i < arr.length; i++) {
            r[q++]=arr[i];
        }
        int flag=0;
        p=0;
        q=0;
        for (int i = 0; i < arr.length; i++) {
            if(flag==0){
                arr[i]=l[p++];
                flag=1;
            }else{
                arr[i]=r[q++];
                flag=0;
            }
        }
        if(k!=1){
            merge(arr,k-1);
        }
    }
}

/*public class Main {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int n=cin.nextInt();
        if(n<4||n>25){
            System.out.println("error");
        }
        ArrayList<Line> lines = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Line l=new Line();
            l.x1=cin.nextInt();
            l.y1=cin.nextInt();
            l.x2=cin.nextInt();
            l.y2=cin.nextInt();
            l.len=l.x2-l.x1==0?l.y2-l.y1:0;
            lines.add(l);
        }
        if()
    }

}

class Line{
    public int x1,y1,x2,y2,len;
}*/
/*
class Main2 {
    public static void main2(String[] args) {
        Scanner s = new Scanner(System.in);
        int l = s.nextInt();
        int r = s.nextInt();
        int m = s.nextInt();
        int time = 0;
        for (int i = l; i <= r; i++) {
            int count = 0;
            int t = i;
            while (t != 0) {
                if ((t & 1) != 0) {
                    count++;
                }
                t = t >> 1;
            }
            if (count == m) {
                time++;
            }
        }
        if (time == 0) {
            System.out.println(-1);
        } else {
            System.out.println(time);
        }


    }

}*/
