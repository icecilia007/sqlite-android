package com.brzas.basic_crud_sqlite.helper;

import com.brzas.basic_crud_sqlite.models.Exam;

public interface ExamUpdateListener {
    void onExamUpdate(Exam exam, int pos);
}
