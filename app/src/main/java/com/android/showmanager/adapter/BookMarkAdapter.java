package com.android.showmanager.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.showmanager.R;
import com.android.showmanager.model.ShowSearchDetails;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.BookMarkViewHolder>
{
    public static List<ShowSearchDetails> showList;
    private Context context;
    IShowClickListner listner;

    public BookMarkAdapter(Context context, IShowClickListner listner)
    {
        this.showList = new ArrayList<>();
        this.context = context;
        this.listner = listner;
    }

    public List<ShowSearchDetails> getShowList()
    {
        return showList;
    }

    public void setShowList(List<ShowSearchDetails> showList)
    {
        this.showList = showList;
    }

    @NonNull
    @Override
    public BookMarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.bookmark_layout, parent, false);
        BookMarkViewHolder viewHolder = new BookMarkViewHolder(viewItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookMarkViewHolder holder, int position)
    {
        final ShowSearchDetails showDetails = showList.get(position);
        holder.showName.setText(showDetails.getTitle());
        Picasso.with(context)
            .load(showDetails.getPoster())
            .placeholder(R.drawable.placeholder_background)
            .error(R.drawable.placeholder_background)
            .fit()
            .noFade()
            .into(holder.imageView, new Callback()
            {
                @Override
                public void onSuccess()
                {
                }

                @Override
                public void onError()
                {
                }
            });

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listner.onShowClick(showDetails);
            }
        });

        holder.itemView.setLongClickable(true);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                showDialog(showDetails);
                return true;
            }
        });
    }

    private void showDialog(final ShowSearchDetails showDetails)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.dialog_details);
        builder.setCancelable(true);

        builder.setPositiveButton(
            R.string.YES,
            new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    listner.onDeleteBookMark(showDetails);
                }
            });

        builder.setNegativeButton(
            R.string.NO,
            new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    dialog.cancel();
                }
            });

        AlertDialog alert11 = builder.create();
        alert11.show();
    }

    @Override
    public int getItemCount()
    {
        return showList != null ? showList.size() : 0;
    }

    class BookMarkViewHolder extends RecyclerView.ViewHolder
    {

        ImageView imageView;
        TextView showName;

        public BookMarkViewHolder(@NonNull View itemView)
        {
            super(itemView);
            showName = itemView.findViewById(R.id.showName);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
