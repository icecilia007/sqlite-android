package com.brzas.basic_crud_sqlite.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.brzas.basic_crud_sqlite.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView tittleTextView;
    TextView themeTextView;
    TextView durationTextView;
    TextView dateTextView;
    ImageView deleteImageView;

    public MyViewHolder(View view) {
        super(view);

        tittleTextView = view.findViewById(R.id.tittle_txt);
        themeTextView = view.findViewById(R.id.theme_txt);
        durationTextView=view.findViewById(R.id.duration_txt);
        dateTextView=view.findViewById(R.id.date_txt);
        deleteImageView = view.findViewById(R.id.deleteImageView);
    }
}
