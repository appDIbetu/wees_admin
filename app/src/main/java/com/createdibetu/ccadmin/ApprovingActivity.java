package com.createdibetu.ccadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.LayoutTransition;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ApprovingActivity extends AppCompatActivity implements ApprovingListAdapter.RecyclerViewAdapterListener{

    private ApprovingListAdapter recyclerViewAdapter;
    //ActionBar actionBar = getActivity().getActionBar();
    private RecyclerView recyclerView;
    private List<ApprovingItem> newsList;
    //private ViewPager viewPager;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approving);


        recyclerView = findViewById(R.id.recyclerViewA);
        newsList = new ArrayList<>();
        recyclerViewAdapter = new ApprovingListAdapter(this, newsList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(ApprovingActivity.this, 1));
        recyclerView.setAdapter(recyclerViewAdapter);
        fetchNotices();
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(ApprovingActivity.this, recyclerView, new RecyclerViewTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {



                //viewPager.setCurrentItem(1);

                //((MainActivity) getActivity()).setItem(item);

                //Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();

                /*CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                builder.setStartAnimations(getContext(), R.anim.fade_out, R.anim.fade_in);
                builder.setExitAnimations(getContext(), R.anim.fade_out,
                        R.anim.fade_in);
                final Bitmap backButton = BitmapFactory.decodeResource(getResources(), R.drawable.ic_back);

                builder.setCloseButtonIcon(backButton);
                CustomTabsIntent customTabsIntent = builder.build();

                /*ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(getContext(), R.anim.fade_in, R.anim.fade_out);
                customTabsIntent.launchUrl(getContext(), Uri.parse(item.getLink()));*/

            }

            @Override
            public void onLongClick(View view, int position) {
                ApprovingItem item = newsList.get(newsList.size() - position - 1);

               /* switch (item.status) {
                    case "not Aprroved": {
                        //posting(item.url);
                        Intent i = new Intent(ApprovingActivity.this, MainActivity.class);
                        i.putExtra("url", item.url);
                        i.putExtra("source", item.getNewsSite());
                        i.putExtra("titleNepali", item.titleNepali);
                        i.putExtra("descriptionNepali", item.descriptionNepali);
                        i.putExtra("headLineNepali", item.headLineNepali);
                        i.putExtra("titleEnglish", item.titleEnglish);
                        i.putExtra("descriptionEnglish", item.descriptionEnglish);
                        i.putExtra("headLineEnglish", item.headLineEnglish);
                        i.putExtra("descriptionNepali", item.topicA);
                        i.putExtra("topicA", item.topicA);
                        i.putExtra("topicB", item.topicB);
                        i.putExtra("descriptionNepali", item.topicC);
                        i.putExtra("approving","yes");
                        startActivity(i);
                        break;
                    }

                    default:
                        Toast.makeText(ApprovingActivity.this, "The post is already approved:)", Toast.LENGTH_SHORT).show();
                }*/
            }
        }));
    }


    /*private void posting(String string) {
        try {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "https://us-central1-dlfirst-244317.cloudfunctions.net/feedUploadStatusPosting";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("title_english", string);

            final String requestBody = jsonBody.toString();

            final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    //progressBar.setVisibility(View.GONE);
                    Toast.makeText(LockListActivity.this, "You entered posting mode:)", Toast.LENGTH_LONG).show();
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LockListActivity.this, "my bad, did you make any mistake?:(", Toast.LENGTH_LONG).show();
                    //progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
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

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
*/





    private void fetchNotices() {

        //getActivity().getActionBar().setTitle("Academic");
        //getActivity().getActionBar().setIcon(R.drawable.ic_academic);
        DatabaseReference mDatabase;

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("toApprove").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               /* for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    String name = (String) messageSnapshot.child("name").getValue();
                    String message = (String) messageSnapshot.child("message").getValue();
                }*/

                if (dataSnapshot.exists()) {
                    //textView.setVisibility(View.GONE);
                    Object object = dataSnapshot.getValue(Object.class);
                    //String json = new Gson().toJson(object);

                    List<ApprovingItem> feedList = new ArrayList<>();
                    feedList.clear();
                    for (DataSnapshot feedSnapshot : dataSnapshot.getChildren()) {
                        ApprovingItem feedItem = feedSnapshot.getValue(ApprovingItem.class);
                        feedList.add(feedItem);
                    }
                    //Toast.makeText(getActivity(), "Error: " + dataSnapshot.getValue(), Toast.LENGTH_SHORT).show();

                    //Log.v("fg","op"+json);

                    //List<Item> recipes = new Gson().fromJson(json, new TypeToken<List<Item>>() {
                    //}.getType());

                    // adding recipes to cart list
                    newsList.clear();
                    newsList.addAll(feedList);

                    // refreshing recycler view
                    recyclerViewAdapter.notifyDataSetChanged();
                 /*final Handler handler = new Handler();
                 handler.postDelayed(new Runnable() {
                     @Override
                     public void run() {
                         // Do something after 5s = 5000ms
                         //mShimmerViewContainer.stopShimmer();
                         //mShimmerViewContainer.setVisibility(View.GONE);
                         //((MainActivity) getActivity()).getSupportActionBar().show();
                         recyclerView.setVisibility(View.VISIBLE);
                     }
                 }, 500);*/
                    // stop animating Shimmer and hide the layout
                } else {

                    //todo
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(ApprovingActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Add your menu entries here
        //super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //inflater.inflate(R.menu.menu_main, menu);

//myMenu = menu;
        //((MainActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_notifications_button_toolbar);
        //((MainActivity) getActivity()).getSupportActionBar().setIcon(null);

        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        searchView = (androidx.appcompat.widget.SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        //searchView.startAnimation(fab_close);
        //int searchBarId = searchView.getId();//getContext().getResources().getIdentifier("android:id/search_bar",null,null);
        //LinearLayout searchBar = (SearchView) searchView.findViewById(searchBarId);
        searchView.setLayoutTransition(new LayoutTransition());
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(this.getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        // listening to search query text change
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                recyclerViewAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                recyclerViewAdapter.getFilter().filter(query);
                return false;
            }
        });


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            //((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            //getActivity().getActionBar().setIcon(null);
            return true;
        }

        if (id == R.id.action_refresh) {

            //((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            //getActivity().getActionBar().setIcon(null);
            recreate();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(ApprovingItem item) {

    }


    boolean doubleBackToExitPressedOnce = false;
/*
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 6000);
    }
*/
}
