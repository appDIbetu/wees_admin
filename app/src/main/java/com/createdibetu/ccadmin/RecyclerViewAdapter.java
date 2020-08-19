package com.createdibetu.ccadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Filterable {
    private Context context;
    //private List<Contact> contactList;
    public List<Item> newsFiltered;
    private RecyclerViewAdapterListener listener;
    private List<Item> newsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView newsSite, title, url, status, timestamp;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            newsSite = view.findViewById(R.id.newsSite);
            //chef = view.findViewById(R.id.chef);
            title = view.findViewById(R.id.title);
            status = view.findViewById(R.id.status);
            thumbnail = view.findViewById(R.id.thumbnail);
            url = view.findViewById(R.id.urlA);


           /* view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onItemSelected(noticesFiltered.get(getAdapterPosition()));
                }
            });*/
        }
    }


    public RecyclerViewAdapter(Context context, List<Item> newsList) {
        this.listener = listener;
        this.context = context;
        this.newsList = newsList;
        this.newsFiltered = newsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        final Item item = newsFiltered.get(newsFiltered.size() -position-1);
        /*if(item.getLink().endsWith("pdf")){
            holder.type.setText("PDF");
        }else {
            holder.type.setText("Web");
        }*/

        holder.newsSite.setText(item.newsSite);

        //holder.chef.setText("By " + recipe.getChef());
        holder.title.setText(item.getTitle());
        holder.status.setText(item.getStatus());
        //holder.timestamp.setText(recipe.getDate());
holder.url.setText(item.getUrl());
        Glide.with(context)
                .load(item.getThumbnail())
                .asBitmap()
                .into(holder.thumbnail);
    }
    // recipe
    @Override
    public int getItemCount() {
        return newsFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    //Log.v("filterY", "performFiltering: ");
                    newsFiltered = newsList;
                } else {
                    List<Item> filteredList = new ArrayList<>();
                    for (Item row : newsList) {
//                       // Log.v("filterYu",row.getLink());
//                        Log.v("filterY", "performFiltering: jj");
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNewsSite().toLowerCase().contains(charString.toLowerCase()) || row.getTitle().contains(charString.toLowerCase())
                                || row.getStatus().toLowerCase().contains(charString.toLowerCase())
                                || row.getUrl().toLowerCase().contains(charString.toLowerCase())) {

                            // Log.v("filterYup",row.getLink());
                            filteredList.add(row);
                        }
                    }

                    newsFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = newsFiltered;
                //Log.v("filterYuk",filterResults.toString());
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                newsFiltered = (ArrayList<Item>) filterResults.values;
                notifyDataSetChanged();
                //Log.v("filterYuko",filterResults.toString());
            }
        };
    }

    public interface RecyclerViewAdapterListener {
        void onItemSelected(Item item);
    }
}

