package com.example.lostandfound;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class LostItemsFragment extends Fragment {
    RecyclerView recyclerview;
    ItemAdapter itemadapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View retrn = inflater.inflate(R.layout.fragment_lostitems,container,false);

        recyclerview= retrn.findViewById(R.id.itemlist);
        recyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));

        FirebaseRecyclerOptions<Items> options = new FirebaseRecyclerOptions.Builder<Items>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Items"), Items.class )
                .build();

        itemadapter = new ItemAdapter(options);
        recyclerview.setAdapter(itemadapter);
        return retrn;

    }

    @Override
    public void onStart() {
        super.onStart();
        itemadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        itemadapter.stopListening();
    }
}
