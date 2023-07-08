package com.brzas.basic_crud_sqlite.adapter;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brzas.basic_crud_sqlite.R;
import com.brzas.basic_crud_sqlite.models.Exam;
import com.brzas.basic_crud_sqlite.sqlite.DatabaseQueryClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExamRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private DatabaseQueryClass databaseQueryClass;
    private List<Exam> examList;

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");



    public ExamRecyclerViewAdapter(Context context, List<Exam> examList) {
        this.context = context;
        this.examList = examList;
        databaseQueryClass = new DatabaseQueryClass(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.exam_content, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final int itemPosition = position;
        final Exam exam = examList.get(position);

        holder.themeTextView.setText(exam.getTheme());
        holder.tittleTextView.setText(exam.getTitle());
        holder.durationTextView.setText(sdf.format(new Date(exam.getTimeDuration())));
        holder.dateTextView.setText(format.format(exam.getExamDate()));
        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure you want to delete?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteExam(itemPosition);
                            }
                        });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    private void deleteExam(int position) {
        Exam exam = examList.get(position);
        long count = databaseQueryClass.deleteExamById(exam.getId());
        if (count > 0) {
            examList.remove(position);
            notifyDataSetChanged();
        }
    }

    public Exam addExam(Exam exam) {
        int pos = getItemCount();
        Log.d(TAG, "addExam " + exam);
        examList.add(exam);
        notifyItemChanged(pos - 1);
        return exam;
    }

    @Override
    public int getItemCount() {
        return examList.size();
    }
}
