package com.computech.thenews.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.computech.thenews.R;
import com.computech.thenews.common.Common;
import com.computech.thenews.model.Article;

public class NewsDetailActivity extends AppCompatActivity {
    private NestedScrollView nestedScrollView;
    private ImageView imageView;
    private Button read_more_btn,share_btn;
    private TextView title,description,author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        setUpToolbar();
        initUI();

        getDetails();

        //onClick methods for the buttons
        read_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show webview dialog
                showWebViewDialog(Common.currentArticle.getUrl());
            }
        });

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a share intent
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TITLE,Common.currentArticle.getTitle());
                intent.putExtra(Intent.EXTRA_TEXT,Common.currentArticle.getUrl());
                intent.putExtra(Intent.EXTRA_REFERRER_NAME,"Source: TheNews app");
                startActivity(Intent.createChooser(intent,"Share news with:"));
            }
        });
    }

    private void getDetails() {
        if (Common.currentArticle != null){
            nestedScrollView.setVisibility(View.VISIBLE);
            Article article = Common.currentArticle;

            //set the imageBackDrop
            Glide.with(this)
                    .asBitmap()
                    .load(article.getUrlToImage())
                    .into(imageView);

            //set the news title
            title.setText(article.getTitle());
            description.setText(article.getDescription());
            author.setText(article.getAuthor());
        }
        else {
            Toast.makeText(this,"Internet connection error",Toast.LENGTH_SHORT)
                    .show();
            finish();
        }
    }

    private void initUI() {
        imageView = findViewById(R.id.news_image_view);
        title = findViewById(R.id.news_title);
        description = findViewById(R.id.news_description);
        nestedScrollView = findViewById(R.id.nested_detail);

        read_more_btn = findViewById(R.id.read_more);
        share_btn = findViewById(R.id.share);
        author = findViewById(R.id.author);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showWebViewDialog(final String url){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        WebView wv = new WebView(this);
        wv.loadUrl(url);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }
        });

        alert.setView(wv);
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.setPositiveButton("Open in browser", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    Intent intent= new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(Intent.createChooser(intent,"Open with: "));
            }
        });
        alert.show();
    }
}
