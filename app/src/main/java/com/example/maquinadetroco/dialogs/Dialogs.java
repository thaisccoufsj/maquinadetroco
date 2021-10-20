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
        showMessage(message, (dialog, which) -> dialog.dismiss());
    }

    public void showMessage(String message,DialogInterface.OnClickListener listener){
        new MaterialAlertDialogBuilder(context)
                .setTitle("Aviso")
                .setNeutralButton("OK", listener)
                .setMessage(message)
                .setCancelable(false)
                .show();
    }

    public static Dialogs getInstance(Context context){
        return new Dialogs(context);
    }

}