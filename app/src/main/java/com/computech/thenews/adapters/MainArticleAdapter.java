package com.computech.thenews.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.computech.thenews.R;
import com.computech.thenews.activities.NewsDetailActivity;
import com.computech.thenews.common.Common;
import com.computech.thenews.common.ISO8601Parser;
import com.computech.thenews.model.Article;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainArticleAdapter extends RecyclerView.Adapter<MainArticleAdapter.MainArticleViewHolder> {
    private List<Article> articleList;
    private Context context;

    public MainArticleAdapter(List<Article> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }

    @NonNull
    @Override
    public MainArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.item_news,parent,false);

       return new MainArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainArticleViewHolder holder, int position) {
        //set the imageView
        if (articleList.get(position).getUrlToImage()!=null)
            Glide.with(context)
                    .asBitmap()
                    .load(articleList.get(position).getUrlToImage())
                    .into(holder.imageView);
        else
            Glide.with(context)
                .asBitmap()
                .load(context.getResources().getDrawable(R.drawable.the_news_logo))
                .into(holder.imageView);
        //set the title
        if (articleList.get(position).getTitle().length()>65)
            holder.title.setText(articleList.get(position).getTitle().substring(0,65)+"...");
        else
            holder.title.setText(articleList.get(position).getTitle());

        //set the date
        Date date = null;
        try {
            date = ISO8601Parser.parse(articleList.get(position).getPublishedAt());
        }catch (ParseException e){
            e.printStackTrace();
        }
        if (date!=null)
            holder.timeTextView.setReferenceTime(date.getTime());

        //set the source
        holder.source.setText(articleList.get(position).getSource().getName());
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class MainArticleViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title,source;
        RelativeTimeTextView timeTextView;
        public MainArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.news_image);
            title = itemView.findViewById(R.id.news_title);
            timeTextView = itemView.findViewById(R.id.news_time_ago);
            source = itemView.findViewById(R.id.news_source);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //save the current article
                    Common.currentArticle = articleList.get(getAdapterPosition());
                    //go to the detail activity
                    Intent intent = new Intent(context, NewsDetailActivity.class);
                    context.startActivity(intent);
                }
            });

        }
    }
}
