package com.example.facultdash;

public class Faculty {
    private String Name;
    private String UniqueID;
    private String Email;
    private String program;

    public void setName(String name) {
        Name = name;
    }

    public void setUniqueID(String uniqueID) {
        UniqueID = uniqueID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;


    public Faculty() {
    }

    public String getName() {
        return Name;
    }

    public String getUniqueID() {
        return UniqueID;
    }

}
