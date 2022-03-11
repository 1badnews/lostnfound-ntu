package com.example.lostandfound;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ItemsEdit
{
    private DatabaseReference databaseReference;
    public ItemsEdit()
    {
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Items.class.getSimpleName());
    }

    public Task<Void> add(Items itm)
    {

        return databaseReference.push().setValue(itm);
    }

}
