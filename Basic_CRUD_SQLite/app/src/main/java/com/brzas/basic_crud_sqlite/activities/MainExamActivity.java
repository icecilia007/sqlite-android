package com.brzas.basic_crud_sqlite.activities;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brzas.basic_crud_sqlite.R;
import com.brzas.basic_crud_sqlite.adapter.ExamRecyclerViewAdapter;
import com.brzas.basic_crud_sqlite.fragment.ExamCreateDialogFragment;
import com.brzas.basic_crud_sqlite.helper.ExamCreateListener;
import com.brzas.basic_crud_sqlite.helper.SQLiteAttributes;
import com.brzas.basic_crud_sqlite.models.Exam;
import com.brzas.basic_crud_sqlite.sqlite.DatabaseQueryClass;

import java.util.ArrayList;
import java.util.List;

public class MainExamActivity extends AppCompatActivity implements ExamCreateListener {

    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);
    private List<Exam> examList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ExamRecyclerViewAdapter examRecyclerViewAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_exam);
        getSupportActionBar().setTitle(R.string.title_exam);

        recyclerView = findViewById(R.id.recyclerExam);
        examList.addAll(databaseQueryClass.getAllExam());
        Log.d(TAG, "ExamList " + examList);

        examRecyclerViewAdapter = new ExamRecyclerViewAdapter(this, examList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(examRecyclerViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_exam, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_insert) {
            openExamCreateDialog();
            return true;
        } else if (id == R.id.action_delete) {
            if (examList.isEmpty()) return false;
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure you want to delete?");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            boolean isAllDeleted = databaseQueryClass.deleteAllExams();
                            if (isAllDeleted) {
                                examList.clear();
                                examRecyclerViewAdapter.notifyDataSetChanged();
                            }
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openExamCreateDialog() {
        ExamCreateDialogFragment examCreateDialogFragment = ExamCreateDialogFragment.newInstance("Create Exam", this);
        examCreateDialogFragment.show(getSupportFragmentManager(), SQLiteAttributes.CREATE_EXAM);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStudentCreated(Exam exam) {
        examList.add(exam);
//        examRecyclerViewAdapter.addExam(exam);
        Log.d(TAG, exam.getTitle());
    }
}
