package info.socialhackathonumbria.memo.adpters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import info.socialhackathonumbria.memo.R;
import info.socialhackathonumbria.memo.models.Note;
import info.socialhackathonumbria.memo.viewholders.MessageViewHolder;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by LuckySeven srl on 27/09/2017.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessageViewHolder> {
    private RealmResults<Note> mNotes;
    private ItemTouchHelper mTouchHelper = new ItemTouchHelper(
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();
                    remove(position);
                }
            }
    );

    public MessagesAdapter() {
        this.setHasStableIds(true);
        Realm realm = Realm.getDefaultInstance();
        mNotes = realm.where(Note.class).findAll();
    }

    public void add(final Note note) {
        if (note != null) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(note);
                }
            });
            notifyItemInserted(mNotes.size()-1);
        }
    }

    public void add(String text) {
        if (text != null && !text.isEmpty()) {
            Note note = new Note(text);
            add(note);
        }
    }

    public void remove(int position) {
        Note note = mNotes.get(position);
        final long id = note.time;
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Note note = realm.where(Note.class).equalTo("time", id).findFirst();
                note.deleteFromRealm();
            }
        });
        notifyItemRemoved(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mTouchHelper.attachToRecyclerView(recyclerView);
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
        holder.mTextView.setText(mNotes.get(position).text);
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    @Override
    public long getItemId(int position) {
        return mNotes.get(position).time;
    }
}
