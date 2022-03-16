package com.example.lostandfound;

public class Account {

    private String Email,studentID;


    public Account() {}


    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public Account(String Email, String studentID){
    this.Email = Email;
    this.studentID=studentID;
}
}
