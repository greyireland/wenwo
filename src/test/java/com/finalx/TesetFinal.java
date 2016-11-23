package com.finalx;

/**
 * Created by tengyu on 2016/7/31.
 */
public class TesetFinal {
    private final String s="sss";
    private  String string;
    public void setString(String str){
        this.string=str;
    }
    @Override
    public boolean equals(Object o) {
        System.out.println(((TesetFinal)o).string);
        return false;
    }
    public static void   main(String []args){
        TesetFinal f2= new TesetFinal();
        TesetFinal f3= new TesetFinal();

    }

    public void test() {

        System.out.println("You are a hero.");
    }

}
