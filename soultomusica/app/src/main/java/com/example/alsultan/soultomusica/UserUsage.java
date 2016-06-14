package com.example.alsultan.soultomusica;

/**
 * Created by Al Sultan on 5/21/2016.
 */
public class UserUsage {
    /**
     *
     */
    private String fullName;
    private String songtitle;

    public UserUsage(String fullName,String songtitle){
        this.fullName=fullName;
        this.songtitle=songtitle;
    }

    public String getSongtitle() {
        return songtitle;
    }

    public void setSongtitle(String songtitle) {
        this.songtitle = songtitle;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
