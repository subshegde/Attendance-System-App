package com.example.facultdash;

public class MainModelStudentList {


    String Name; // (should be same as database col name)

    //default constructor for firebase
    MainModelStudentList(){

    }
    // creting constructor
//    public MainModel(String name, String uniqueID, String email, String phone) {
    public MainModelStudentList(String name, String uniqueID) {
        Name = name;
    }

    //    creating getter and setter for all variable
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    //creating xml(i.e main_item) for perticular studnet details ( items )
//    after creating xml, creating adapter class(MainAdapter)
}

