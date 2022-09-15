package com.example.igalanews.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igalanews.Adapters.ImageAdapter;
import com.example.igalanews.Models.ImageModel;
import com.example.igalanews.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class FaceOfTheWeekFragment extends Fragment {

    RecyclerView recyclerView_pic;
    ImageAdapter imageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_face_of_the_week, container, false);

        recyclerView_pic = view.findViewById(R.id.recycler_pic);

        recyclerView_pic.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<ImageModel> firebaseRecyclerOptions =
                new FirebaseRecyclerOptions.Builder<ImageModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("images"), ImageModel.class)
                .build();

        imageAdapter = new ImageAdapter(firebaseRecyclerOptions);
        recyclerView_pic.setAdapter(imageAdapter);

        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        imageAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        imageAdapter.stopListening();
    }
}