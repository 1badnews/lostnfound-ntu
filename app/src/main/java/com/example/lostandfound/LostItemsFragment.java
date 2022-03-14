package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class LostItemsFragment extends Fragment {
    RecyclerView recyclerview;
    ItemAdapter itemadapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View retrn = inflater.inflate(R.layout.fragment_lostitems,container,false);








        setHasOptionsMenu(true);
        recyclerview= retrn.findViewById(R.id.itemlist);
        recyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));

        FirebaseRecyclerOptions<Items> options = new FirebaseRecyclerOptions.Builder<Items>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Items"), Items.class )
                .build();
        recyclerview.setItemAnimator(null);
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


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.searching, menu);
        MenuItem item = menu.findItem(R.id.searching);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                search(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                search(s);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void search(String txt)
    {
        FirebaseRecyclerOptions<Items> options = new FirebaseRecyclerOptions.Builder<Items>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Items").orderByChild("title").startAt(txt).endAt(txt+"~"), Items.class )
                .build();
        itemadapter = new ItemAdapter(options);
        itemadapter.startListening();
        recyclerview.setAdapter(itemadapter);

    }
}
