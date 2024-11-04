package com.compass.desafio02.web.dto.subject;

public class SubjectEditProfessorsDto {

    private String mainProfessorEmail;
    private String substituteProfessorEmail;

    public SubjectEditProfessorsDto() {
    }

    public SubjectEditProfessorsDto(String mainProfessorEmail, String substituteProfessorEmail) {
        this.mainProfessorEmail = mainProfessorEmail;
        this.substituteProfessorEmail = substituteProfessorEmail;
    }

    public String getMainProfessorEmail() {
        return mainProfessorEmail;
    }

    public void setMainProfessorEmail(String mainProfessorEmail) {
        this.mainProfessorEmail = mainProfessorEmail;
    }

    public String getSubstituteProfessorEmail() {
        return substituteProfessorEmail;
    }

    public void setSubstituteProfessorEmail(String substituteProfessorEmail) {
        this.substituteProfessorEmail = substituteProfessorEmail;
    }
}
