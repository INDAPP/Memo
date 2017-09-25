package info.socialhackathonumbria.memo.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import info.socialhackathonumbria.memo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    OnHomeFragmentInteractionListener listener;

    TextView mTextView;

    public HomeFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.fab).setOnClickListener(this);
        mTextView = (TextView)view.findViewById(R.id.textView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeFragmentInteractionListener) {
            listener = (OnHomeFragmentInteractionListener) context;
        }
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onFabClick(view);
        }
    }

    public void setMessage(String message) {
        if (mTextView != null) {
            mTextView.setText(message);
        }
    }

    public interface OnHomeFragmentInteractionListener {
        void onFabClick(View view);
    }
}
