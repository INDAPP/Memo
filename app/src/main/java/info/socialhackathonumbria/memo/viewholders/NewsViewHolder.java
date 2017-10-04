package info.socialhackathonumbria.memo.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import info.socialhackathonumbria.memo.R;
import info.socialhackathonumbria.memo.models.News;

/**
 * Created by LuckySeven srl on 04/10/2017.
 */

public class NewsViewHolder extends RecyclerView.ViewHolder {

    public TextView textTitle;
    public ImageView imageView;
    public TextView textDescription;
    public TextView textAuthor;

    public NewsViewHolder(View itemView) {
        super(itemView);
        this.textTitle = itemView.findViewById(R.id.textTitle);
        this.imageView = itemView.findViewById(R.id.imageView);
        this.textDescription = itemView.findViewById(R.id.textDescription);
        this.textAuthor = itemView.findViewById(R.id.textAuthor);
    }

    public void bind(News news)
    {
        textTitle.setText(news.title);
        textDescription.setText(news.description);
        textAuthor.setText(news.author);
        //TODO: mettere l'immagine
    }


}
