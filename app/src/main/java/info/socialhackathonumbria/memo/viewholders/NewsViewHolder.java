package info.socialhackathonumbria.memo.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import info.socialhackathonumbria.memo.R;
import info.socialhackathonumbria.memo.models.News;

/**
 * Created by LuckySeven srl on 04/10/2017.
 */

public class NewsViewHolder extends RecyclerView.ViewHolder {
    final static DateFormat FORMATTER;
    static {
        FORMATTER = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
    }

    public TextView textTitle;
    public ImageView imageView;
    public TextView textDescription;
    public TextView textAuthor;
    public TextView textDate;

    public NewsViewHolder(View itemView) {
        super(itemView);
        this.textTitle = itemView.findViewById(R.id.textTitle);
        this.imageView = itemView.findViewById(R.id.imageView);
        this.textDescription = itemView.findViewById(R.id.textDescription);
        this.textAuthor = itemView.findViewById(R.id.textAuthor);
        this.textDate = itemView.findViewById(R.id.textDate);
    }

    public void bind(News news)
    {
        textTitle.setText(news.title);
        textDescription.setText(news.description);
        textAuthor.setText(news.author);
        Picasso.with(imageView.getContext()).load(news.urlToImage).into(imageView);
        textDate.setText(news.publishedAt != null ? FORMATTER.format(news.publishedAt) : null);
    }


}
