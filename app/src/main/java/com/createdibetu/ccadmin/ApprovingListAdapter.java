package com.createdibetu.ccadmin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ApprovingListAdapter extends RecyclerView.Adapter<ApprovingListAdapter.MyViewHolder> implements Filterable {
    private Context context;
    //private List<Contact> contactList;
    private List<ApprovingItem> newsFiltered;
    private RecyclerViewAdapterListener listener;
    private List<ApprovingItem> newsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;

        public TextView status;
        public EditText tokenApprover, newsSite, url, statusEdit, titleEnglish, titleNepali,
                headLineEnglish, headLineNepali, descriptionNepali, descriptionEnglish, topicA,
                topicB, topicC, author, thumbnailText, priority;
        public RelativeLayout progressBar;

        public MyViewHolder(View view) {
            super(view);
            newsSite = view.findViewById(R.id.newsSiteA);
            //chef = view.findViewById(R.id.chef);
            status = view.findViewById(R.id.statusA);
            thumbnail = view.findViewById(R.id.thumbnailA);
            url = view.findViewById(R.id.urlA);
            titleNepali = view.findViewById(R.id.titleNepali);
            descriptionNepali = view.findViewById(R.id.descriptionNepali);
            headLineNepali = view.findViewById(R.id.headLineNepali);
            titleEnglish = view.findViewById(R.id.titleEnglish);
            descriptionEnglish = view.findViewById(R.id.descriptionEnglish);
            headLineEnglish = view.findViewById(R.id.headLineEnglish);
            topicA = view.findViewById(R.id.topicA);
            topicB = view.findViewById(R.id.topicB);
            topicC = view.findViewById(R.id.topicC);
            author = view.findViewById(R.id.authorA);
            thumbnailText = view.findViewById(R.id.thumbnail_Text);
            progressBar = view.findViewById(R.id.progressApprove);
            statusEdit = view.findViewById(R.id.statusEdit);
            tokenApprover = view.findViewById(R.id.tokenApprove);
            priority = view.findViewById(R.id.priority);

            Button button = view.findViewById(R.id.ApproveButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!(tokenApprover.getText() + "").equals("")) {
                        if (status.getText().equals("Not Approved")) {
                            progressBar.setVisibility(View.VISIBLE);


                            InputMethodManager im = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
                            im.hideSoftInputFromWindow(view.getWindowToken(), 0);

                            try {


                                RequestQueue requestQueue = Volley.newRequestQueue(context);
                                String URL = "https://us-central1-dlfirst-244317.cloudfunctions.net/feedUploadUAT";

                                JSONObject jsonBody = new JSONObject();
                                //jsonBody.put("tok", token.getText());
                                jsonBody.put("title_nepali", titleNepali.getText());
                                jsonBody.put("description_nepali", descriptionNepali.getText());
                                jsonBody.put("headLine_nepali", headLineNepali.getText());
                                jsonBody.put("url", url.getText());
                                jsonBody.put("thumbnail", thumbnailText.getText());
                                //jsonBody.put("priority", priority.getText());
                                jsonBody.put("topic_a", topicA.getText());
                                jsonBody.put("topic_b", topicB.getText());
                                jsonBody.put("topic_c", topicC.getText());
                                jsonBody.put("title_english", titleEnglish.getText());
                                jsonBody.put("description_english", descriptionEnglish.getText());
                                jsonBody.put("headLine_english", headLineEnglish.getText());
                                jsonBody.put("source", newsSite.getText());
                                jsonBody.put("author", author.getText());
                                jsonBody.put("priority", priority.getText());
                                jsonBody.put("status_edit", statusEdit.getText());
                                jsonBody.put("tokenApprove", tokenApprover.getText());

                                final String requestBody = jsonBody.toString();

                                final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new com.android.volley.Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        //context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(context, "wohoo smart, you approved an wee:)", Toast.LENGTH_LONG).show();
                                        //((Activity)context).recreate();
                                        //Intent i = new Intent(context, LockListActivity.class);
                                        //startActivity(i);
                                    }
                                }, new com.android.volley.Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(context, "my bad, did you make any mistake?:(", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        //getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    }
                                }) {
                                    @Override
                                    public String getBodyContentType() {
                                        return "application/json; charset=utf-8";
                                    }

                                    @Override
                                    public byte[] getBody() throws AuthFailureError {
                                        try {
                                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                                        } catch (UnsupportedEncodingException uee) {
                                            //VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                            return null;
                                        }
                                    }

                                    @Override
                                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                                        String responseString = "";
                                        if (response != null) {
                                            responseString = String.valueOf(response.statusCode);
                                            // can get more details such as response.headers
                                        }
                                        return com.android.volley.Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                                    }
                                };
                                //custom retry policy
                                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                        0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                                requestQueue.add(stringRequest);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(context, "Already Approved:)", Toast.LENGTH_LONG).show();

                        }

                    } else {
                        Toast.makeText(context, "Did you miss token? :)", Toast.LENGTH_LONG).show();
                    }
                }
            });
           /* view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onItemSelected(noticesFiltered.get(getAdapterPosition()));
                }
            });*/
        }
    }


    public ApprovingListAdapter(Context context, List<ApprovingItem> newsList) {
        this.listener = listener;
        this.context = context;
        this.newsList = newsList;
        this.newsFiltered = newsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.approving_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ApprovingItem item = newsFiltered.get(newsFiltered.size() - position - 1);
        /*if(item.getLink().endsWith("pdf")){
            holder.type.setText("PDF");
        }else {
            holder.type.setText("Web");
        }*/

        holder.newsSite.setText(item.newsSite);

        //holder.chef.setText("By " + recipe.getChef());
        holder.titleNepali.setText(item.getTitleNepali());
        holder.descriptionNepali.setText(item.getDescriptionNepali());
        holder.titleEnglish.setText(item.getTitleEnglish());
        holder.headLineNepali.setText(item.getHeadLineNepali());
        holder.titleEnglish.setText(item.getTitleEnglish());
        holder.descriptionEnglish.setText(item.getDescriptionEnglish());
        holder.headLineEnglish.setText(item.getHeadLineEnglish());
        holder.topicA.setText(item.getTopicA());
        holder.topicB.setText(item.getTopicB());
        holder.topicC.setText(item.getTopicC());
        holder.status.setText(item.getStatus());
        holder.author.setText(item.getAuthor());
        holder.thumbnailText.setText(item.thumbnail);
        holder.statusEdit.setText(item.getStatus());
        holder.priority.setText("1");

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
                    List<ApprovingItem> filteredList = new ArrayList<>();
                    for (ApprovingItem row : newsList) {
//                       // Log.v("filterYu",row.getLink());
//                        Log.v("filterY", "performFiltering: jj");
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getHeadLineEnglish().toLowerCase().contains(charString.toLowerCase()) || row.getDescriptionEnglish().contains(charSequence)
                                || row.getStatus().toLowerCase().contains(charString.toLowerCase())
                                || row.getTitleEnglish().toLowerCase().contains(charString.toLowerCase())
                                || row.getAuthor().toLowerCase().contains(charString.toLowerCase())) {

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
                newsFiltered = (ArrayList<ApprovingItem>) filterResults.values;
                notifyDataSetChanged();
                //Log.v("filterYuko",filterResults.toString());
            }
        };
    }

    public interface RecyclerViewAdapterListener {
        void onItemSelected(ApprovingItem item);
    }


}
