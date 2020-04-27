package com.example.valentina.talkwithme.utilities;

public class MySubject {
    private final String name;
    private final String id;
    private final int imageResource;

    public MySubject(String name, String id, int imageResource){
        this.name = name;
        this.id = id;
        this.imageResource = imageResource;
    }

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }

    public int getImageResource(){
        return imageResource;
    }

}
