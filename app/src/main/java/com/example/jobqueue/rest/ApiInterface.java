package com.example.jobqueue.rest;

import com.example.jobqueue.model.Employee;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    String Base_url = "http://dummy.restapiexample.com/api/v1/";

    @GET("employees")
    Call<ArrayList<Employee>> getEmployees();
}