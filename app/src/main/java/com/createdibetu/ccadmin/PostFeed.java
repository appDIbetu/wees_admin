package com.createdibetu.ccadmin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class PostFeed extends Fragment {

    private EditText token, urlEdit, thumbnailEdit, titleNepali, descriptionNepali, headLineNepali, titleEnglish, descriptionEnglish, headLineEnglish, priority, topic_a, topic_b, topic_c, sourceMention;
    private String source;
    private RelativeLayout progressBar;
    private SharedPreferences sharedPref;
    private TextView countN, countE;
    private View rootview;
    private String url, titleNepaliText, descriptionNepaliText;
    //private OnFragmentInteractionListener mListener;

    public PostFeed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_post_feed, container, false);

        countE = rootview.findViewById(R.id.countE);
        countN = rootview.findViewById(R.id.countN);

        sharedPref = getContext().getSharedPreferences("tok", Context.MODE_PRIVATE);

        progressBar = rootview.findViewById(R.id.progress);

        url = ((MainActivity) getActivity()).getUrl();
        source = ((MainActivity) getActivity()).getSource();

        titleNepaliText = ((MainActivity) getActivity()).getNepaliTitle();
        descriptionNepaliText = ((MainActivity) getActivity()).getNepaliDescription();


        String thumbnail = ((MainActivity) getActivity()).getThumbnail();
        Button send = rootview.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                progressBar.setVisibility(View.VISIBLE);
                InputMethodManager im = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(rootview.getWindowToken(), 0);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("remTok", "" + token.getText());
                editor.apply();
                sendRequest();
            }
        });

        sourceMention = rootview.findViewById(R.id.source);
        token = rootview.findViewById(R.id.tok);
        token.setText(sharedPref.getString("remTok", ""));
        urlEdit = rootview.findViewById(R.id.url);
        thumbnailEdit = rootview.findViewById(R.id.thumbnail);
        titleNepali = rootview.findViewById(R.id.title_nepali);
        descriptionNepali = rootview.findViewById(R.id.description_nepali);
        headLineNepali = rootview.findViewById(R.id.headLine_nepali);
        titleEnglish = rootview.findViewById(R.id.title_english);
        descriptionEnglish = rootview.findViewById(R.id.description_english);
        headLineEnglish = rootview.findViewById(R.id.headLine_english);
        priority = rootview.findViewById(R.id.priority);
        topic_a = rootview.findViewById(R.id.topic_a);
        topic_b = rootview.findViewById(R.id.topic_b);
        topic_c = rootview.findViewById(R.id.topic_c);

        urlEdit.setText(url);
        thumbnailEdit.setText(thumbnail);
        sourceMention.setText(source);
        //titleNepali.setText(titleNepaliText);
        //descriptionNepali.setText(descriptionNepaliText);
        //headLineNepali.setText(((MainActivity) getActivity()).getNepaliHeadLine());
        //titleEnglish.setText(((MainActivity) getActivity()).getEnglishTitle());
        //descriptionEnglish.setText(((MainActivity) getActivity()).getEnglishDescription());
        //headLineEnglish.setText(((MainActivity) getActivity()).getEnglishHeadLine());
        //topic_a.setText(((MainActivity) getActivity()).getTopicA());
        //topic_b.setText(((MainActivity) getActivity()).getTopicB());
        //topic_c.setText(((MainActivity) getActivity()).getTopicC());

        descriptionNepali.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = "count " + descriptionNepali.length();
                countN.setText(s);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        descriptionEnglish.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = "count " + descriptionEnglish.length();
                countE.setText(s);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        Button back = rootview.findViewById(R.id.clearAll);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   // if (((MainActivity) getActivity()).getIsApproving().equals("yes")) {
                    if (!url.equals("http://dipak.epizy.com")) {
                        not_posting();
                    }

                    Toast.makeText(getContext(), "my bad, you went back:(", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), LockListActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(i);
               // }
            /*else

            {
                Toast.makeText(getContext(), "my bad, not Approving:(", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), ApprovingItem.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
            }*/
        }
        });


        //Item item = ((MainActivity) getActivity()).getItem();
        return rootview;
    }
    private StringRequest stringRequest;

    private void sendRequest() {
        try {


            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            String URL = "https://us-central1-dlfirst-244317.cloudfunctions.net/feedUploadUATApproval";
            if (url.equals("http://dipak.epizy.com")) {
                URL = "https://us-central1-dlfirst-244317.cloudfunctions.net/feedUploadUATOpen";
            }

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("tok", token.getText());
            jsonBody.put("title_nepali", titleNepali.getText());
            jsonBody.put("description_nepali", descriptionNepali.getText());
            jsonBody.put("headLine_nepali", headLineNepali.getText());
            jsonBody.put("url", urlEdit.getText());
            jsonBody.put("thumbnail", thumbnailEdit.getText());
            jsonBody.put("priority", priority.getText());
            jsonBody.put("topic_a", topic_a.getText());
            jsonBody.put("topic_b", topic_b.getText());
            jsonBody.put("topic_c", topic_c.getText());
            jsonBody.put("title_english", titleEnglish.getText());
            jsonBody.put("description_english", descriptionEnglish.getText());
            jsonBody.put("headLine_english", headLineEnglish.getText());
            jsonBody.put("source", sourceMention.getText());

            final String requestBody = jsonBody.toString();

          stringRequest = new StringRequest(Request.Method.POST, URL, new com.android.volley.Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "wohoo smart, you added an wee:)", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getActivity(), LockListActivity.class);
                    startActivity(i);
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "my bad, did you make any mistake?:(", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
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
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
            }


    private void not_posting() {
        try {

            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            String URL = "https://us-central1-dlfirst-244317.cloudfunctions.net/feedUploadStatusNotPosted";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("title_english", url);

            final String requestBody = jsonBody.toString();

            final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    progressBar.setVisibility(View.GONE);

                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "my bad, did you make any mistake?:(", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
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
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
