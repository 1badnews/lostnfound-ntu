package com.example.lostandfound;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PostItemFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retrn = inflater.inflate(R.layout.fragment_postitem,container,false);
        final EditText ptitle = retrn.findViewById(R.id.post_title);
        final EditText pdescription = retrn.findViewById(R.id.post_description);
        final EditText pcontacts = retrn.findViewById(R.id.post_contacts);
        FirebaseUser loggedinuser = FirebaseAuth.getInstance().getCurrentUser();
        final String pemail = loggedinuser.getEmail();
        Button pbutton = retrn.findViewById(R.id.post_button);
        ItemsEdit itm = new ItemsEdit();
        pbutton.setOnClickListener(v->
        {
            Items item = new Items(ptitle.getText().toString().trim(),pdescription.getText().toString().trim(),pcontacts.getText().toString().trim(), pemail);
            itm.add(item).addOnSuccessListener(suc->
            {
                Toast.makeText(getActivity(),"Item is submitted!",Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(error->
            {
                Toast.makeText(getActivity(),"Item failed to submit!",Toast.LENGTH_SHORT).show();
            });
        });

        return retrn;
    }


}
