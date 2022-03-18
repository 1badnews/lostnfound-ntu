package com.example.lostandfound;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogClass  extends AppCompatDialogFragment {
    private EditText dialogusername;
    private EditText dialogpassword;
    public DialogInterfaceListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("Please reauthenticate to continue!")
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String username = dialogusername.getText().toString();
                        String password = dialogpassword.getText().toString();
                        listener.reauth(username, password);
                    }
                });

        dialogusername = view.findViewById(R.id.dialogusername);
        dialogpassword = view.findViewById(R.id.dialogpassword);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DialogInterfaceListener) context;
        } catch (ClassCastException e) {

        }
    }

    public interface DialogInterfaceListener {
        void reauth(String username, String password);
    }

}
