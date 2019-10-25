package com.example.jobqueue.model;

import com.google.gson.annotations.SerializedName;

public class Employee {

    @SerializedName("id")
    private String id;
    @SerializedName("employee_name")
    private String name;
    @SerializedName("employee_salary")
    private String salary;
    @SerializedName("employee_age")
    private String age;
    @SerializedName("profile_image")
    private String image;

    public Employee(String id, String name, String salary, String age, String image) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

