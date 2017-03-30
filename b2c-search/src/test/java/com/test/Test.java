package com.test;

import java.util.Arrays;

public class Test {
    private String[] names = new String[10];
    
    private String[] copyNames = new String[10];
    
    public static void main(String[] args) {
        Test test = new Test();
        test.start();
    }
    
    public void start(){
        structureNames1();
        
        structureCopyName1(names);
        for(String copy: copyNames){
            System.out.println(copy);
        }
        
        System.out.println("========================");
        
        structureNames2();
        for (String name : names) {
            System.out.println(name);
        }
        
        System.out.println("========================");
        for(String copy: copyNames){
            System.out.println(copy);
        }
    }

    private void structureNames1(){
        for(int i = 0; i<10; i++){
            names[i] = "james" + i;
        }
    }

    private void structureNames2(){
        for(int i = 0; i< 3; i++){
            names[i] = "hhe" + i;
        }
    }

    private void structureCopyName1(String[] names){
        if(names.length == 0){
            this.copyNames = new String[0];
        }else{
            this.copyNames = Arrays.copyOf(names, names.length);
        }
    }
}