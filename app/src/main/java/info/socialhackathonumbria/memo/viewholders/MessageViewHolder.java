package info.socialhackathonumbria.memo.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import info.socialhackathonumbria.memo.R;

/**
 * Created by LuckySeven srl on 27/09/2017.
 */

public class MessageViewHolder extends RecyclerView.ViewHolder {
    public TextView mTextView;
    public ImageView mImageView;

    public MessageViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView)itemView.findViewById(R.id.textView);
        mImageView = (ImageView)itemView.findViewById(R.id.imageView);
    }

}
