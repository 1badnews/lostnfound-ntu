package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsFragment extends Fragment {
    private TextView currentaddress, currentnumber;
    private EditText changeemail,changepassword, changepassword2;
    private Button changeemailbtn,changepasswordbtn,deleteaccbtn;
    private String email,pass;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View retrn = inflater.inflate(R.layout.fragment_settings,container,false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        changeemailbtn = retrn.findViewById(R.id.changeemailbtn);
        changepasswordbtn=retrn.findViewById(R.id.changepasswordbtn);
        deleteaccbtn=retrn.findViewById(R.id.deleteaccbtn);
        database.getReference().child("Accounts").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account acc = snapshot.getValue(Account.class);

                currentaddress = retrn.findViewById(R.id.currentaddress);
                currentnumber= retrn.findViewById(R.id.currentnumber);
                currentnumber.setText(acc.getStudentID());
                currentaddress.setText(acc.getEmail());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }




    });

        deleteaccbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String currentemail = user.getEmail();
                DatabaseReference itmdatabase = FirebaseDatabase.getInstance().getReference("Items");
                itmdatabase.orderByChild("user").equalTo(currentemail).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                            dataSnapshot.getRef().removeValue();
                            // String key = dataSnapshot.getKey();

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                DatabaseReference accdatabase = FirebaseDatabase.getInstance().getReference("Accounts");
                accdatabase.orderByChild("email").equalTo(currentemail).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                            dataSnapshot.getRef().removeValue();
                            // String key = dataSnapshot.getKey();

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    FirebaseAuth.getInstance().signOut();
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    Toast.makeText(getActivity(),"Account has been deleted!",Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });
    changepasswordbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            changepassword=retrn.findViewById(R.id.changepassword);
            changepassword2=retrn.findViewById(R.id.changepassword2);
            String password = changepassword.getText().toString().trim();
            String password2 = changepassword2.getText().toString().trim();

            if (password.isEmpty()){
                changepassword.setError("Password is empty!");
                changepassword.requestFocus();
                return;
            }

            if (password2.isEmpty()){
                changepassword2.setError("Confirm Password is empty!");
                changepassword2.requestFocus();
                return;

            }
            if (password.length() < 5){
                changepassword.setError("Please enter a password with more than 5 characters.");
                return;
            }
            if (!changepassword.getText().toString().equals(changepassword2.getText().toString()))
            {
                changepassword2.setError("Passwords do not match!");
                changepassword.setError("Passwords do not match!");
                changepassword.requestFocus();
                return;

            }

            user.updatePassword(password)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(),"Password has been changed.",Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
        }
    });
    changeemailbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            changeemail=retrn.findViewById(R.id.changeemail);
            String email = changeemail.getText().toString().trim();

            if (email.isEmpty()){

                changeemail.setError("Email is empty!");
                changeemail.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                changeemail.setError("Please type in a valid Email!");
                return;
            }

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String currentemail = user.getEmail();





            DatabaseReference itmdatabase = FirebaseDatabase.getInstance().getReference("Items");
            itmdatabase.orderByChild("user").equalTo(currentemail).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                        dataSnapshot.getRef().removeValue();
                       // String key = dataSnapshot.getKey();

                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });





            DatabaseReference accdatabase = FirebaseDatabase.getInstance().getReference("Accounts");

            user.updateEmail(email);
            DatabaseReference deletereference = accdatabase.child(FirebaseAuth.getInstance().getUid()).child("email");
            deletereference.setValue(email);
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getContext(), MainActivity.class);
            Toast.makeText(getActivity(),"Email has been changed!",Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    });





        return retrn;}



}