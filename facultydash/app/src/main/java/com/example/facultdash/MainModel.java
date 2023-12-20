package com.example.facultdash;

public class MainModel {


    String Name,UniqueID; // (should be same as database col name)

    //default constructor for firebase
    MainModel(){

    }
    // creting constructor
//    public MainModel(String name, String uniqueID, String email, String phone) {
    public MainModel(String name, String uniqueID) {
        Name = name;
        UniqueID = uniqueID;
//        Email = email;
//        Phone = phone;
    }

    //    creating getter and setter for all variable
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUniqueID() {
        return UniqueID;
    }

    public void setUniqueID(String uniqueID) {
        UniqueID = uniqueID;
    }

//    public String getEmail() {
//        return Email;
//    }
//
//    public void setEmail(String email) {
//        Email = email;
//    }
//
//    public String getPhone() {
//        return Phone;
//    }
//
//    public void setPhone(String phone) {
//        Phone = phone;
//    }

    //creating xml(i.e main_item) for perticular studnet details ( items )
//    after creating xml, creating adapter class(MainAdapter)
}
