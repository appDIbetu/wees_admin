package com.createdibetu.ccadmin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerList extends Fragment {
    private ProgressBar progressBarTranslate;
    //private OnFragmentInteractionListener mListener;



    public RecyclerList() {
        // Required empty public constructor
    }

    public static RecyclerList newInstance(String param1, String param2) {
        RecyclerList fragment = new RecyclerList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recycler_list, container, false);

        WebView web_view = rootView.findViewById(R.id.webViewTranslate);
        progressBarTranslate = rootView.findViewById(R.id.progressBarTranslate);
        WebSettings webSettings = web_view.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web_view.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && progressBarTranslate.getVisibility() == ProgressBar.GONE) {
                    progressBarTranslate.setVisibility(ProgressBar.VISIBLE);
                }

                progressBarTranslate.setProgress(progress);
                if (progress == 100) {
                    progressBarTranslate.setVisibility(ProgressBar.GONE);
                }
            }
        });
        //webView.loadUrl("https://drivezy.com");
        //title.setText();

        web_view.loadUrl("https://translate.google.co.in/#view=home&op=translate&sl=ne&tl=en");

        return rootView;
    }

 /*   // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/


}
