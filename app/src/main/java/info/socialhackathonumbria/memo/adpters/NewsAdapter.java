package info.socialhackathonumbria.memo.adpters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.socialhackathonumbria.memo.R;
import info.socialhackathonumbria.memo.models.News;
import info.socialhackathonumbria.memo.viewholders.NewsViewHolder;

/**
 * Created by LuckySeven srl on 04/10/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    private News[] news;

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_holder_news,parent,false);
        NewsViewHolder newsViewHolder = new NewsViewHolder(view);
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.bind(news[position]);
    }

    @Override
    public int getItemCount() {
        return news != null ? news.length : 0;
    }

    public void update(News[] news) {
        this.news = news;
        notifyDataSetChanged();

    }
}
