package info.socialhackathonumbria.memo.adpters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.socialhackathonumbria.memo.R;
import info.socialhackathonumbria.memo.viewholders.MessageViewHolder;

/**
 * Created by LuckySeven srl on 27/09/2017.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessageViewHolder> {
    private String[] mMessages;

    public MessagesAdapter(String[] messages) {
        mMessages = messages;
    }

    public void update(String[] messages) {
        mMessages = messages;
        notifyDataSetChanged();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_holder_message, parent, false);
        MessageViewHolder holder = new MessageViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.mTextView.setText(mMessages[position%mMessages.length]);
    }

    @Override
    public int getItemCount() {
        return mMessages != null ? mMessages.length * 100 : 0;
    }
}
