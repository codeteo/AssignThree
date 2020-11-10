package com.example.myapplication.ui.main.screens;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class BooksFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    private static final int RC_WRITE_EXTERNAL_STORAGE = 123;
    public static final String DOWNLOAD_FOLDER = "/download/";

    private RecyclerView recyclerView;

    private BooksAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        adapter = new BooksAdapter(getActivity(), this::startDownload);
        recyclerView.setAdapter(adapter);

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
                // TODO: 10/11/2020 show error toast
            }
        });


        // set response data to adapter

        return view;
    }

    @AfterPermissionGranted(RC_WRITE_EXTERNAL_STORAGE)
    public void downloadPDF(BooksResponse book) {
        DownloadManager downloadmanager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(book.getPdfUrl());
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(book.getTitle());
        request.setDescription("Downloading");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(DOWNLOAD_FOLDER, book.getTitle());
        downloadmanager.enqueue(request);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        // required by easyPermissions lib
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        // required by easyPermissions lib
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public void startDownload(BooksResponse book) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            Timber.i("mesa sta perms");
            downloadPDF(book);
        } else {
            EasyPermissions.requestPermissions(this, "Write Permissions required!",
                    RC_WRITE_EXTERNAL_STORAGE, perms);
        }
    }
}
