package com.brzas.basic_crud_sqlite.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.brzas.basic_crud_sqlite.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView tittleTextView;
    TextView themeTextView;
    ImageView deleteImageView;

    public MyViewHolder(View view) {
        super(view);

        tittleTextView = view.findViewById(R.id.tittle_txt);
        themeTextView = view.findViewById(R.id.theme_txt);
        deleteImageView = view.findViewById(R.id.deleteImageView);
    }
}
