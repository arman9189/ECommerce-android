package com.example.jimmy.e_commerce.Models;

import java.util.Date;

public class Customer {

    private String Cus_name;
    private String Cus_Username;
    private String Cus_Pass;
    private String Cus_Job;
    private String Cus_Email;
    private String Cus_Birthdate;
    private String Cus_Gender;


    public Customer() {
    }

    public String getCus_Email() {
        return Cus_Email;
    }

    public void setCus_Email(String cus_Email) {
        Cus_Email = cus_Email;
    }

    public String getCus_name() {
        return Cus_name;
    }

    public void setCus_name(String cus_name) {
        Cus_name = cus_name;
    }

    public String getCus_Username() {
        return Cus_Username;
    }

    public void setCus_Username(String cus_Username) {
        Cus_Username = cus_Username;
    }

    public String getCus_Pass() {
        return Cus_Pass;
    }

    public void setCus_Pass(String cus_Pass) {
        Cus_Pass = cus_Pass;
    }

    public String getCus_Job() {
        return Cus_Job;
    }

    public void setCus_Job(String cus_Job) {
        Cus_Job = cus_Job;
    }

    public String getCus_Birthdate() {
        return Cus_Birthdate;
    }

    public void setCus_Birthdate(String cus_Birthdate) {
        Cus_Birthdate = cus_Birthdate;
    }

    public String getCus_Gender() {
        return Cus_Gender;
    }

    public void setCus_Gender(String cus_Gender) {
        Cus_Gender = cus_Gender;
    }


}
