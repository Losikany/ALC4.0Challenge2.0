package com.example.travel;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.DealViewHolder> {
    ArrayList<TravelDeals> deals;
    private FirebaseDatabase mFirebaseDataBase;
    private ChildEventListener mChildListener;
    private DatabaseReference mDatabaseReference;
    private ImageView imageDeal;

    public DealAdapter(){

       // FirebaseUtil.openFbReference("traveldeals", this);
        mFirebaseDataBase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        //listArray to populate recycleview
        deals=FirebaseUtil.mDeals;
        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                TravelDeals td = dataSnapshot.getValue(TravelDeals.class);
                Log.d("Deal :", td.getTitle());
                td.setId(dataSnapshot.getKey());
                deals.add(td);
                notifyItemInserted(deals.size() -1);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildListener);
    }

    @NonNull
    @Override
    public DealViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context=viewGroup.getContext();
        View itemView= LayoutInflater.from(context).inflate(R.layout.rv_row, viewGroup, false);

        return new DealViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DealViewHolder dealViewHolder, int position) {
        TravelDeals deal=deals.get(position);
        dealViewHolder.bind(deal);


    }

    @Override
    public int getItemCount() {
        //items in the array list
        return deals.size();
    }

    public class DealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView tvTitle;
    TextView tvDescription;
    TextView tvPrice;

    public DealViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvDescription = itemView.findViewById(R.id.tvDescription);
        tvPrice = itemView.findViewById(R.id.tvPrice);
        imageDeal=itemView.findViewById(R.id.imageDeal);
        itemView.setOnClickListener(this);

    }

    public void bind(TravelDeals deal) {

        tvTitle.setText(deal.getTitle());
        tvDescription.setText(deal.getDescription());
        tvPrice.setText(deal.getPrice());
       // Picasso.get().load().into(imageDeal);
        //showImage(deal.getImageUrl());
    }

        @Override
        public void onClick(View v) {
            //get the position of the item clicked
            int position=getAdapterPosition();
            Log.d("Click :", String.valueOf(position));
            TravelDeals selectedDeal=deals.get(position);
            Intent intent=new Intent(v.getContext(), DealActivity.class);
            intent.putExtra("Deal" ,selectedDeal);
            v.getContext().startActivity(intent);
        }
        private void showImage(String url){
if(url!=null && url.isEmpty()==false){
    Log.d(DealAdapter.class.getSimpleName(), url);
    Picasso.with(imageDeal.getContext()).load(url).
            resize(200, 200 ).centerCrop()
            .into(imageDeal);

}

        }
    }
}
