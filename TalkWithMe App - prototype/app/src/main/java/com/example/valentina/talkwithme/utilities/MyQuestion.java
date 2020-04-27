package com.example.valentina.talkwithme.utilities;

public class MyQuestion {
    private final String text;
    private final String id;
    private final String answer;

    public MyQuestion(String text,String id, String answer){
        this.text = text;
        this.id = id;
        this.answer = answer;
    }

    public String getText(){
        return text;
    }

    public String getId(){
        return id;
    }

    public String getAnswer(){
        return answer;
    }

}
