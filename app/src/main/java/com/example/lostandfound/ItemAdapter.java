package com.example.lostandfound;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemAdapter  extends FirebaseRecyclerAdapter<Items,ItemAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public ItemAdapter(@NonNull FirebaseRecyclerOptions<Items> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Items model) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String useremail = user.getEmail();
        String itememail = model.getUser();
        holder.title.setText(model.getTitle());
        final String key = this.getRef(position).getKey();
        if (useremail.equals(itememail))
        {
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), EditInfoActivity.class);
                    intent.putExtra("key", key);
                    view.getContext().startActivity(intent);
                }
            });
        }
        if (!useremail.equals(itememail)) {
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), InfoActivity.class);
                    intent.putExtra("key", key);
                    view.getContext().startActivity(intent);
                }
            });

        }

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView title;
        View view;
        TextView description;
        TextView contacts;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            description = (TextView) itemView.findViewById(R.id.post_description);
            contacts = (TextView) itemView.findViewById(R.id.post_contacts);
            image = (CircleImageView) itemView.findViewById(R.id.itempic);
            title = (TextView) itemView.findViewById(R.id.titletext);
            view = itemView.findViewById(R.id.clickitem);



        }
    }

}
