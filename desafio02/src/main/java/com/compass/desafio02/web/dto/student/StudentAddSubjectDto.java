package com.compass.desafio02.web.dto.student;

public class StudentAddSubjectDto {

    private String studentEmail;

    private String subjectName;

    public StudentAddSubjectDto() {
    }

    public StudentAddSubjectDto(String studentEmail, String subjectName) {
        this.studentEmail = studentEmail;
        this.subjectName = subjectName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
