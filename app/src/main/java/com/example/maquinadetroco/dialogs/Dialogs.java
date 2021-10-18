package com.example.maquinadetroco.dialogs;

import android.content.Context;
import android.content.DialogInterface;

import com.example.maquinadetroco.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Dialogs {

    private Context context;

    private Dialogs(Context context){
        this.context = context;
    }

    public void showMessage(String message){
        new MaterialAlertDialogBuilder(context)
                .setTitle("Aviso")
                .setNeutralButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setMessage(message)
                .show();
    }

    public static Dialogs getInstance(Context context){
        return new Dialogs(context);
    }

}