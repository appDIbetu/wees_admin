package com.createdibetu.ccadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
private ViewPager viewPager;
private ViewPagerAdapter viewPagerAdapter;

    public String getUrl() {
        return url;
    }

    public String getSource() {
        return source;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    String url, source, thumbnail, nepaliTitle, nepaliDescription, nepaliHeadLine, englishTitle, englishDescription, englishHeadLine, topicA, isApproving="no",
    topicB, topicC;

    public String getNepaliTitle() {
        return nepaliTitle;
    }

    public String getNepaliDescription() {
        return nepaliDescription;
    }

    public String getNepaliHeadLine() {
        return nepaliHeadLine;
    }

    public String getEnglishTitle() {
        return englishTitle;
    }

    public String getEnglishDescription() {
        return englishDescription;
    }

    public String getEnglishHeadLine() {
        return englishHeadLine;
    }

    public String getTopicA() {
        return topicA;
    }

    public String getTopicB() {
        return topicB;
    }

    public String getTopicC() {
        return topicC;
    }

    public String getIsApproving() {
        return isApproving;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        thumbnail = intent.getStringExtra("thumbnail");
        url = intent.getStringExtra("url");
        source = intent.getStringExtra("source");
        nepaliTitle = intent.getStringExtra("nepaliTitle");
        nepaliDescription = intent.getStringExtra("nepaliDescription");
        nepaliHeadLine = intent.getStringExtra("nepaliHeadLine");
        englishTitle = intent.getStringExtra("englishTitle");
        englishHeadLine = intent.getStringExtra("englishHeadLine");
        englishDescription = intent.getStringExtra("englishDescription");
        topicA = intent.getStringExtra("topicA");
        topicB = intent.getStringExtra("topicB");
        topicC = intent.getStringExtra("topicC");
        isApproving = intent.getStringExtra("approving");


        viewPager = findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Click Back button in Posting Fragment to go back", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 100);
    }


}
