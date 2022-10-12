package com.lab7;

import java.util.List;

public class Student {

    public Student(List<Subject> subjects, Double average, String lastName) {
        this.subjects = subjects;
        this.average = average;
        this.lastName = lastName;
    }

    private List<Subject> subjects;

    private Double average;

    private String lastName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public static class Subject {

        public Subject(int mark, String title) {
            this.mark = mark;
            this.title = title;
        }

        private int mark;

        private String title;

        public int getMark() {
            return mark;
        }

        public void setMark(int mark) {
            this.mark = mark;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
