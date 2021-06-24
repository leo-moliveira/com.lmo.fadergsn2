package com.lmo.fadergsn2;

public class Instance {
    public static Instance instance = null;

    public User user = new User();

    protected Instance(){}

    public static synchronized Instance getInstance(){
        if(null == instance){
            instance = new Instance();
        }
        return instance;
    }

}
