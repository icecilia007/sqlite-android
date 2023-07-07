package com.brzas.basic_crud_sqlite.helper;

import com.brzas.basic_crud_sqlite.models.Exam;

public interface ExamCreateListener {
    void onStudentCreated(Exam exam);

}
