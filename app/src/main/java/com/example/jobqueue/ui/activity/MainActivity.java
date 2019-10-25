package com.example.jobqueue.ui.activity;

import  android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobqueue.R;
import com.example.jobqueue.model.Employee;
import com.example.jobqueue.rest.ApiInterface;
import com.example.jobqueue.ui.adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    FrameLayout closeFrameLayout,searchFrameLayout;
    LinearLayout searchBarLayout;
    EditText searchText;
    TextView catchupCount,findText,descText;
    RecyclerView rv;
    ProgressBar progressBar;
    ArrayList<Employee> Employees,searchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        rv = (RecyclerView)findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        closeFrameLayout = (FrameLayout)findViewById(R.id.frameLayout);
        searchFrameLayout = (FrameLayout)findViewById(R.id.searchFrameLayout);
        searchBarLayout = (LinearLayout)findViewById(R.id.searchBarLayout) ;
        searchText = (EditText)findViewById(R.id.searchText) ;
        catchupCount = (TextView)findViewById(R.id.catchup_count);
        findText = (TextView)findViewById(R.id.text_findCatchup);
        descText = (TextView)findViewById(R.id.text_searchDesc) ;
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        closeFrameLayout.setVisibility(View.INVISIBLE);
        catchupCount.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.Base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        final Call<ArrayList<Employee>> call = api.getEmployees();

        loadList(call);

        /*rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    loadMoreList(call.clone());

                }
            }
        });*/




    }
    public static List<Employee> filter(String string, Iterable<Employee> iterable, boolean byName) {
        if (iterable == null)
            return new LinkedList<Employee>();
        else {
            List<Employee> collected = new LinkedList<Employee>();
            Iterator<Employee> iterator = iterable.iterator();
            if (iterator == null)
                return collected;
            while (iterator.hasNext()) {
                Employee item = iterator.next();

                if (item.getName().toLowerCase().startsWith(string)){
                    collected.add(item);
                }
                else if(item.getName().toUpperCase().startsWith(string)){
                    collected.add(item);
                }
            }
            return collected;
        }
    }
    public void loadList(Call<ArrayList<Employee>> call){
        call.enqueue(new Callback<ArrayList<Employee>>() {
            @Override
            public void onResponse(Call<ArrayList<Employee>> call, Response<ArrayList<Employee>> response) {
                Employees  = response.body();
                searchText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        closeFrameLayout.setVisibility(View.VISIBLE);
                        searchList = new ArrayList<Employee>();
                        List<Employee> list = filter(s.toString(),Employees, true);
                        searchList.clear();
                        searchList.addAll(list);

                        catchupCount.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                        descText.setVisibility(View.GONE);
                        findText.setVisibility(View.GONE);
                        catchupCount.setText("Catchup Results("+list.size()+")");
                        RecyclerAdapter adapter = new RecyclerAdapter(MainActivity.this,searchList);
                        rv.setAdapter(adapter);
                        if(list.size()==0){
                            catchupCount.setVisibility(View.INVISIBLE);
                            descText.setVisibility(View.VISIBLE);
                            findText.setVisibility(View.VISIBLE);
                            descText.setText("We didn't find any Catchup related to your query.");
                            findText.setText("Oops, Nothing here");
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                closeFrameLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RecyclerAdapter adapter = new RecyclerAdapter(MainActivity.this,Employees);
                        rv.setAdapter(adapter);
                        searchText.setText("");
                        LinearLayout mainLayout;
                        mainLayout = (LinearLayout)findViewById(R.id.mainLinearLayout);

                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
                    }
                });


                for(Employee e: Employees){
                    Log.i("id",e.getId());
                    Log.i("name",e.getName());
                    Log.i("age",e.getAge());
                    Log.i("salary",e.getSalary());
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Employee>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void loadMoreList(Call<ArrayList<Employee>> call) {
        call.enqueue(new Callback<ArrayList<Employee>>() {
            @Override
            public void onResponse(Call<ArrayList<Employee>> call, Response<ArrayList<Employee>> response) {
                ArrayList<Employee> NewEmployees  = response.body();
                searchList.addAll(NewEmployees);
                RecyclerAdapter adapter = new RecyclerAdapter(MainActivity.this,searchList);
                rv.setAdapter(adapter);
                for(Employee e: Employees){
                    Log.i("id",e.getId());
                    Log.i("name",e.getName());
                    Log.i("age",e.getAge());
                    Log.i("salary",e.getSalary());
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Employee>> call, Throwable t) {

            }
        });
    }

}
