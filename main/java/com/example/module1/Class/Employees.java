package com.example.module1.Class;

public class Employees {
    long ID;
    String FirstName, LastName, Phone;
    boolean isAdmin;
    String Username, Password;

    public Employees() {
    }

    public Employees(long ID, String firstName, String lastName, String phone, boolean isAdmin, String username, String password) {
        this.ID = ID;
        FirstName = firstName;
        LastName = lastName;
        Phone = phone;
        this.isAdmin = isAdmin;
        Username = username;
        Password = password;
    }

    public long getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName(){ return FirstName + " " + LastName;}
}
