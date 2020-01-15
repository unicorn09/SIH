package com.saibaba.hackathon.Adapters;

public class ModelPersonalDetails {
    private String Name,Age,Gender,Address,Address2,DOB,Email,Mobile,State,District,Police,State2,District2,Police2;

    public ModelPersonalDetails(String name, String age, String gender, String address, String DOB, String email, String mobile, String state, String district, String police, String state2, String district2, String police2,String address2) {
        Name = name;
        Age = age;
        Gender = gender;
        Address = address;
        this.DOB = DOB;
        Email = email;
        Mobile = mobile;
        State = state;
        District = district;
        Police = police;
        State2 = state2;
        District2 = district2;
        Police2 = police2;
        Address2=address2;
    }

    public ModelPersonalDetails(){}

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getState2() {
        return State2;
    }

    public void setState2(String state2) {
        State2 = state2;
    }

    public String getDistrict2() {
        return District2;
    }

    public void setDistrict2(String district2) {
        District2 = district2;
    }

    public String getPolice2() {
        return Police2;
    }

    public void setPolice2(String police2) {
        Police2 = police2;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getPolice() {
        return Police;
    }

    public void setPolice(String police) {
        Police = police;
    }

    public String getAddress2() {
        return Address2;
    }
}
