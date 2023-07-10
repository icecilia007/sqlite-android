package com.brzas.basic_crud_sqlite.fragment;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.brzas.basic_crud_sqlite.R;
import com.brzas.basic_crud_sqlite.helper.ExamCreateListener;
import com.brzas.basic_crud_sqlite.helper.SQLiteAttributes;
import com.brzas.basic_crud_sqlite.models.Exam;
import com.brzas.basic_crud_sqlite.sqlite.DatabaseQueryClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExamCreateDialogFragment extends DialogFragment {

    private static ExamCreateListener examCreateListener;

    private EditText titleEditText;
    private EditText themeEditText;
    private EditText teacherEditText;
    private EditText timeDurationEditText;
    private EditText examDateEditText;

    private Button createButton;
    private Button cancelButton;

    private String examTitle = "";
    private String examTheme = "";
    private String examTeacher = "";
    private long timeDuration = -1;
    private Date examDate = null;
    SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    SimpleDateFormat inputFormatDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());


    public ExamCreateDialogFragment() {
    }

    public static ExamCreateDialogFragment newInstance(String title, ExamCreateListener listener) {
        examCreateListener = listener;
        ExamCreateDialogFragment examCreateDialogFragment = new ExamCreateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        examCreateDialogFragment.setArguments(args);

        examCreateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return examCreateDialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_exam_create_dialog, container, false);

        titleEditText = view.findViewById(R.id.title);
        themeEditText = view.findViewById(R.id.theme);
        teacherEditText = view.findViewById(R.id.teacher);
        timeDurationEditText = view.findViewById(R.id.duration);
        examDateEditText = view.findViewById(R.id.date);
        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(SQLiteAttributes.TITLE);
        getDialog().setTitle(title);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                examTitle = titleEditText.getText().toString();
                examTheme = themeEditText.getText().toString();
                examTeacher = teacherEditText.getText().toString();
                try {
                    Date time = inputFormat.parse(timeDurationEditText.getText().toString());
                    timeDuration = time.getTime();
                    Date date = inputFormatDate.parse(examDateEditText.getText().toString());
                    Log.d(TAG,"String "+examDateEditText.getText().toString()+" date "+date);
                    examDate = date;
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                Exam exam = new Exam(-1, examTitle, examTheme, examTeacher, timeDuration, examDate);

                DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(getContext());

                long id = databaseQueryClass.insertExam(exam);

                if (id > 0) {
                    exam.setId(id);
                    examCreateListener.onExamCreated(exam);
                    getDialog().dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
