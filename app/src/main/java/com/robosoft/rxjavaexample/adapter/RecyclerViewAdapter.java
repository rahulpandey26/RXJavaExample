package com.robosoft.rxjavaexample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.robosoft.rxjavaexample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Kumar Pandey on 17-05-2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private final Context mContext;
    private final List<String> mStrings = new ArrayList<>();

    public RecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public void setStrings(List<String> newStrings) {
        mStrings.clear();
        mStrings.addAll(newStrings);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.string_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mColorTextView.setText(mStrings.get(position));
        holder.itemView.setOnClickListener(v -> Toast.makeText(mContext, mStrings.get(position), Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mColorTextView;

        public ViewHolder(View view) {
            super(view);
            mColorTextView = (TextView) view.findViewById(R.id.color_display);
        }
    }
}
