package com.example.travel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class DealAdapter extends RecyclerView.Adapter<DealAdapter.DealViewHolder> {


    private static FirebaseDatabase mFirebaseDatabase;
    private static DatabaseReference mDatabaseReference;
    private static ArrayList<TravelDeals> deals;
    private static ChildEventListener mChildListener;

    private ImageView imageDeal;

    public DealAdapter() {


        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;

        deals = FirebaseUtil.mDeals;


        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                TravelDeals td = dataSnapshot.getValue(TravelDeals.class);

                if (td != null) {

                    Log.d("Deal", "" + td.getTitle());
                    td.setId(dataSnapshot.getKey());
                    deals.add(td);
                    notifyItemInserted(deals.size() - 1);
                }


            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildListener);
    }


    public class DealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvDescription;
        TextView tvPrice;


        public DealViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);

            imageDeal = itemView.findViewById(R.id.imageDeal);
            itemView.setOnClickListener(this);

        }

        public void bind(TravelDeals deal) {
            tvTitle.setText(deal.getTitle());
            tvDescription.setText(deal.getDescription());
            tvPrice.setText(deal.getPrice());

            showImage(deal.getImageUrl());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.d("Deal", String.valueOf(position));

            TravelDeals selectedDeal = deals.get(position);
            Intent intent = new Intent(itemView.getContext(), DealActivity.class);
            intent.putExtra("Deal", selectedDeal);
            v.getContext().startActivity(intent);
        }

        public void showImage(String url) {
            if (url != null && !url.isEmpty()) {
                Picasso.with(imageDeal.getContext())
                        .load(url)
                        .centerCrop()
                        .resize(160, 160)
                        .into(imageDeal);
            }
        }
    }


    @NonNull
    @Override
    public DealViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();

        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_row, viewGroup, false);

        return new DealViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull DealViewHolder dealViewHolder, int i) {

        TravelDeals deal = deals.get(i);
        dealViewHolder.bind(deal);
    }

    @Override
    public int getItemCount() {
        return deals.size();
    }
}
