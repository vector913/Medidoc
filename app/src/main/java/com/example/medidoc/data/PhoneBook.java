package com.example.medidoc.data;

public class PhoneBook {

    String name, num;

    public PhoneBook(String name, String num){
        this.name = name;
        this.num = num;
    }
    public String getName(){
        return name;
    }
    public String getNum(){
        return num;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setNum(String num){
        this.num = num;
    }
}
