package com.example.igalanews.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.igalanews.API.ApiUtilities;
import com.example.igalanews.Adapters.Adapter;
import com.example.igalanews.Models.MainNews;
import com.example.igalanews.Models.ModelClass;
import com.example.igalanews.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GeneralNewsFragment extends Fragment  {

    String api_key = "96d8094b9d584e4baa94603f7125144b";
    ArrayList<ModelClass> modelClassArrayList;
    Adapter adapter;
    String country = "ng";
    private RecyclerView recyclerView_main;
    SearchView searchView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_news, container, false);

        recyclerView_main = view.findViewById(R.id.recycler_main);
        modelClassArrayList = new ArrayList<>();
        recyclerView_main.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter(getContext(), modelClassArrayList);
        recyclerView_main.setAdapter(adapter);
        searchView = view.findViewById(R.id.search_view);

//        handleUserSearch();
        findNews();

        return  view;
    }


    private void findNews() {
        ApiUtilities.getApiInterface().getNews(country, 100, api_key).enqueue(new Callback<MainNews>() {
            @Override
            public void onResponse(Call<MainNews> call, Response<MainNews> response) {
                if (response.isSuccessful()){
                    modelClassArrayList.addAll(response.body().getArticles());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MainNews> call, Throwable t) {
                Toast.makeText(getContext(), "Error!!", Toast.LENGTH_SHORT).show();

            }
        });
    }


}