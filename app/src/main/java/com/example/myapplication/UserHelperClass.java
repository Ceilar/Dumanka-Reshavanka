package com.example.myapplication;



public class UserHelperClass {
    String email, name, uid;
    int words, equations;

    public UserHelperClass() {

    }

    public UserHelperClass(String email, String name, String uid, int words, int equations) {
        this.email = email;
        this.name = name;
        this.uid = uid;
        this.words = words;
        this.equations = equations;
    }

    public int getWords() {
        return words;
    }

    public void setWords(int words) {
        this.words = words;
    }

    public int getEquations() {
        return equations;
    }

    public void setEquations(int equations) {
        this.equations = equations;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
