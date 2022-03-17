package com.example.lostandfound;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class PostItemFragment extends Fragment {
    ImageView pimage;
    FirebaseStorage storage;
    Uri imageuri;
    String imgdownloadurl;
    EditText ptitle, pdescription, pcontacts;
    String pemail;
    ItemsEdit itm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retrn = inflater.inflate(R.layout.fragment_postitem,container,false);
        final EditText ptitle = retrn.findViewById(R.id.post_title);
        final EditText pdescription = retrn.findViewById(R.id.post_description);
        final EditText pcontacts = retrn.findViewById(R.id.post_contacts);
        pimage = retrn.findViewById(R.id.post_image);
        FirebaseUser loggedinuser = FirebaseAuth.getInstance().getCurrentUser();
        final String pemail = loggedinuser.getEmail();
        storage = FirebaseStorage.getInstance();
        Button pbutton = retrn.findViewById(R.id.post_button);
        ItemsEdit itm = new ItemsEdit();

        pimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });


        pbutton.setOnClickListener(v->
        {
            uploadtostorage();
            Toast.makeText(getActivity(),"Posting an item...",Toast.LENGTH_LONG).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    String title = ptitle.getText().toString().trim().toUpperCase();
                    String contacts = pcontacts.getText().toString().trim();

                    if (title.isEmpty()){
                        ptitle.setError("Please enter a title!");
                        ptitle.requestFocus();
                        return;
                    }
                    if (contacts.isEmpty()){
                        pcontacts.setError("Please enter your contact details!");
                        pcontacts.requestFocus();
                        return;
                    }

                    if (TextUtils.isEmpty(imgdownloadurl))
                    {
                        imgdownloadurl = "NoImage";
                        Items item = new Items(title,pdescription.getText().toString().trim(),contacts, pemail, imgdownloadurl);
                        itm.add(item).addOnSuccessListener(suc->
                        {
                            Toast.makeText(getActivity(),"You have posted a new item!",Toast.LENGTH_SHORT).show();
                        }).addOnFailureListener(error->
                        {
                            Toast.makeText(getActivity(),"Item failed to submit!",Toast.LENGTH_SHORT).show();
                        });
                        return;
                    }

                    Items item = new Items(title,pdescription.getText().toString().trim(),contacts, pemail, imgdownloadurl);
                    itm.add(item).addOnSuccessListener(suc->
                    {
                        Toast.makeText(getActivity(),"You have posted a new item!",Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(error->
                    {
                        Toast.makeText(getActivity(),"Item failed to submit!",Toast.LENGTH_SHORT).show();
                    });
                }
            }, 5000);




        });

        return retrn;
    }

    private void uploadtostorage() {



    if (imageuri!= null)
    {
        StorageReference reference = storage.getReference().child("postimages/" + UUID.randomUUID().toString());
        reference.putFile(imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                if (task.isSuccessful())
                {

                }
            }
        });

        reference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {




                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri downloadUrl = urlTask.getResult();

                    imgdownloadurl = String.valueOf(downloadUrl);



            }});}


    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result!=null)
                    {
                        pimage.setImageURI(result);
                        imageuri = result;
                    }

                }
            });





}
