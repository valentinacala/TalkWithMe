package com.example.valentina.talkwithme.utilities;


public class MyContact {
    private final String description;
    private final String FirstName;
    private final String LastName;
    private final String Number;
    private final String Phone;
    private final String email;

    public MyContact(String description,String FirstName, String LastName, String Number, String Phone, String email){
        this.description = description;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Number = Number;
        this.Phone = Phone;
        this.email = email;
    }

    public String getDescription(){
        return description;
    }

    public String getFirstName(){
        return FirstName;
    }

    public String getLastName(){
        return LastName;
    }

    public String getNumber(){
        return Number;
    }

    public String getPhone(){
        return Phone;
    }

    public String getemail(){
        return email;
    }


}
