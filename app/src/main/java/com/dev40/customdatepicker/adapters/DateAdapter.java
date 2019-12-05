package com.dev40.customdatepicker.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dev40.customdatepicker.R;
import com.dev40.customdatepicker.model.DateItem;

import java.util.ArrayList;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {


    ArrayList<DateItem> mylist;
    Context context;
    IDate callback;


    public DateAdapter(Context context, ArrayList<DateItem> mylist, IDate callback) {
        this.mylist = mylist;
        this.context = context;
        this.callback = callback;
    }


    public void onBindViewHolder(@NonNull DateAdapter.DateViewHolder holder, final int position) {
        final int positionInList = position % mylist.size();
        final DateItem item = mylist.get(position % mylist.size());
        holder.dateitem.setText(item.title);

        if (item.isSelected) {
            Typeface type = ResourcesCompat.getFont(context, R.font.english_bold);
            holder.dateitem.setTypeface(type);
            holder.dateitem.setTextColor(context.getResources().getColor(R.color.black));
            callback.onFocus(item);
        } else {
            Typeface type = ResourcesCompat.getFont(context, R.font.english_regular);
            holder.dateitem.setTypeface(type);
            holder.dateitem.setTextColor(context.getResources().getColor(R.color.gray));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //selectItem(positionInList);

                callback.onClick(v);
            }
        });


    }



    public DateItem getItem(int position) {
        return mylist.get(position % mylist.size());
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }


    @NonNull
    @Override
    public DateAdapter.DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_item, parent, false);
        return new DateAdapter.DateViewHolder(v);
    }

    public void selectItem(int position) {
        for (DateItem item : mylist) {
            item.isSelected = false;
        }
        mylist.get(position % mylist.size()).isSelected = true;
        notifyDataSetChanged();
    }


    class DateViewHolder extends RecyclerView.ViewHolder {

        TextView dateitem;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);

            dateitem = itemView.findViewById(R.id.date_item);
        }
    }

    public interface IDate {
        void onClick(View v);

        void onFocus(DateItem item);
    }
}
