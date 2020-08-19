package com.createdibetu.ccadmin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;


public class WebViewFragment extends Fragment {
private ProgressBar progressBar;
private WebView web_view;
private EditText webTitle;
private ImageView web_refresh;

    public WebViewFragment() {
        // Required empty public constructor
    }

    public static WebViewFragment newInstance(String param1, String param2) {
        WebViewFragment fragment = new WebViewFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_web_view, container, false);

        String url = ((MainActivity) getActivity()).getUrl();
        webTitle = rootView.findViewById(R.id.url_text);
        webTitle.setText(url);
        web_refresh = rootView.findViewById(R.id.web_refresh);
        web_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                web_refresh.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
                web_view.loadUrl(webTitle.getText()+"");
            }
        });

if(url!= null) {
    web_view = rootView.findViewById(R.id.webViewFragment);
    progressBar = rootView.findViewById(R.id.progressBar);
    WebSettings webSettings = web_view.getSettings();
    webSettings.setJavaScriptEnabled(true);
    web_view.setWebChromeClient(new WebChromeClient() {
        public void onProgressChanged(WebView view, int progress) {
            if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                progressBar.setVisibility(ProgressBar.VISIBLE);
            }

            progressBar.setProgress(progress);
            if (progress == 100) {
                progressBar.setVisibility(ProgressBar.GONE);
                web_refresh.clearAnimation();
            }
        }
    });
    //webView.loadUrl("https://drivezy.com");
    //title.setText();

    web_view.loadUrl(url);
}

        return rootView;
    }


}
