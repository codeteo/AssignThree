package com.example.myapplication.ui.main.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.data.network.RetrofitHelper;
import com.example.myapplication.data.network.RetrofitService;
import com.example.myapplication.data.network.responses.BooksResponse;
import com.example.myapplication.data.preferences.SharedPreferencesManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksFragment extends Fragment {

    private RecyclerView recyclerView;

    private BooksAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        adapter = new BooksAdapter();
//        recyclerView.setAdapter(adapter);

        // make request
        RetrofitService apiInterface = RetrofitHelper.createService(RetrofitService.class);
        Call<List<BooksResponse>> call = apiInterface.books("Bearer " + SharedPreferencesManager.getAccessToken(getActivity()));
        call.enqueue(new Callback<List<BooksResponse>>() {
            @Override
            public void onResponse(Call<List<BooksResponse>> call, Response<List<BooksResponse>> response) {
                if (response.isSuccessful()) {
                    List<BooksResponse> body = response.body();
                    if (body != null) {
                        adapter.setData(body);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BooksResponse>> call, Throwable t) {

            }
        });


        // set response data to adapter

        return view;
    }
}
