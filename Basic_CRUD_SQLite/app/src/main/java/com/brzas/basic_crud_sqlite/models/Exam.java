package com.brzas.basic_crud_sqlite.models;

import java.util.Date;

public class Exam {
    private long id;
    private String title;
    private String theme;
    private String teacher;
    private long timeDuration;
    private Date examDate;

    public Exam() {
    }

    public Exam(long id, String title, String theme, String teacher, long timeDuration, Date examDate) {
        this.id = id;
        this.title = title;
        this.theme = theme;
        this.teacher = teacher;
        this.timeDuration = timeDuration;
        this.examDate = examDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public long getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(long timeDuration) {
        this.timeDuration = timeDuration;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "title='" + title + '\'' +
                ", theme='" + theme + '\'' +
                ", teacher='" + teacher + '\'' +
                ", timeDuration=" + timeDuration +
                ", examDate=" + examDate +
                '}';
    }
}
