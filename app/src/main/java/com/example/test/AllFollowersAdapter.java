package com.example.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AllFollowersAdapter extends RecyclerView.Adapter<AllFollowersAdapter.ViewHolder>{
    private Context mContext;
    private List<DataSnapshot> mUsers;
    String theLastMessage;
    public AllFollowersAdapter(Context mContext,List<DataSnapshot> mUsers)
    {
        this.mUsers = mUsers;
        this.mContext =mContext;


    }

    @NonNull
    @Override
    public AllFollowersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item,parent,false);


        return new AllFollowersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AllFollowersAdapter.ViewHolder holder, int position) {

        final DataSnapshot followers = mUsers.get(position);
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        String userid = followers.child("user_id").getValue().toString();
        reference.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String user_name = dataSnapshot.child("username").getValue().toString();
                String img = dataSnapshot.child("ImageUrl").getValue().toString();
                holder.username.setText(user_name);
                Glide.with(mContext).load(img).into(holder.profile_img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,InfoActivity.class);
                intent.putExtra("userid",user.getUid());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class  ViewHolder extends  RecyclerView.ViewHolder{

        public TextView username;

        public ImageView profile_img;



        public ViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            profile_img = itemView.findViewById(R.id.user_profile);




        }
    }
}
