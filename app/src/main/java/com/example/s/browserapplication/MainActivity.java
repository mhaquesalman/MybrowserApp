package com.example.s.browserapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    WebView brow;
    EditText urledit;
    Button go,forward,back,clear,reload,home;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        brow= findViewById(R.id.wv_brow);
        urledit = findViewById(R.id.et_url);
        go = findViewById(R.id.btn_go);
        forward = findViewById(R.id.btn_fwd);
        back = findViewById(R.id.btn_bck);
        clear = findViewById(R.id.btn_clear);
        reload = findViewById(R.id.btn_reload);
        home = findViewById(R.id.btn_home);
        progressBar = findViewById(R.id.progressbar);

        /* When we click on something in our browser this enables our application
         to open the link in the same browser instead of opening another browser */
        brow.setWebViewClient(new ourViewClient());

        //Showing progressbar until webview complete loading contents from internet
        brow.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if(newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        WebSettings webSettings = brow.getSettings();
        webSettings.setJavaScriptEnabled(true);
        brow.loadUrl("http://www.google.com");

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editextvalue = urledit.getText().toString();

                if(!editextvalue.startsWith("http://"))
                    editextvalue = "http://" + editextvalue;

                String url = editextvalue;
                brow.loadUrl(url);

                //Hide keyboard after using EditText
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(urledit.getWindowToken(),0);
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(brow.canGoForward())
                    brow.goForward();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(brow.canGoBack())
                    brow.goBack();
            }
        });

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brow.reload();
            }
        });

        clear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        brow.clearHistory();

                    }
                });

        home.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        brow.loadUrl("http://www.google.com");
                        brow.setWebChromeClient(new WebChromeClient(){
                            @Override
                            public void onProgressChanged(WebView view, int newProgress) {
                                progressBar.setProgress(newProgress);
                                if(newProgress == 100){
                                    progressBar.setVisibility(View.GONE);
                                    urledit.setText("");
                                }else{
                                    progressBar.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                });
    }

}


