package com.example.myapplication.utils;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class AlertDialogHelper {

    public static AlertDialog createDialog(@NonNull Context context, String title, String message) {
        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                })
                .show();
    }
}
