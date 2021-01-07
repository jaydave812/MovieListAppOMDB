package com.android.showmanager.adapter;


import com.android.showmanager.R;
import com.android.showmanager.model.ShowSearchDetails;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ShowListAdapter extends PagedListAdapter<ShowSearchDetails, ShowListAdapter.ShowListViewHolder>
{

    private static final String TAG = ShowListAdapter.class.getSimpleName();
    // View Types
    IShowClickListner mListner;
    private Context mContext;

    public ShowListAdapter(Context mContext, IShowClickListner listner)
    {
        super(DIFF_CALLBACK);
        mListner = listner;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ShowListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewItem = inflater.inflate(R.layout.show_list_layout, parent, false);
        ShowListViewHolder viewHolder = new ShowListViewHolder(viewItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowListViewHolder holder, int position)
    {
        holder.bind(getItem(position));
    }

    private static DiffUtil.ItemCallback<ShowSearchDetails> DIFF_CALLBACK =
        new DiffUtil.ItemCallback<ShowSearchDetails>()
        {
            @Override
            public boolean areItemsTheSame(ShowSearchDetails oldItem, ShowSearchDetails newItem)
            {
                return oldItem.getImdbID() == newItem.getImdbID();
            }

            @SuppressLint("DiffUtilEquals")
            @Override
            public boolean areContentsTheSame(ShowSearchDetails oldItem, ShowSearchDetails newItem)
            {
                return oldItem.equals(newItem);
            }
        };


    class ShowListViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView showNameView;
        TextView showYearView;
        ImageButton button;

        public ShowListViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            showNameView = itemView.findViewById(R.id.showName);
            showYearView = itemView.findViewById(R.id.showYear);
            button = itemView.findViewById(R.id.bookmarkButton);
        }

        void bind(final ShowSearchDetails showDetails)
        {
            showYearView.setText(showDetails.getYear());
            showNameView.setText(showDetails.getTitle());
            Picasso.with(mContext)
                .load(showDetails.getPoster())
                .placeholder(R.drawable.placeholder_background)
                .error(R.drawable.placeholder_background)
                .fit()
                .noFade()
                .into(imageView, new Callback()
                {
                    @Override
                    public void onSuccess()
                    {
                    }

                    @Override
                    public void onError()
                    {
                        Log.i(TAG, "Error while loading image");
                    }
                });

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mListner.onShowClick(showDetails);
                }
            });

            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mListner.onSaveBookMark(showDetails);
                }
            });

        }
    }

}
