//package com.compass.desafio02.web.dto.coordinator;
//
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//
//public class CoordinatorTeachDto {
//
//    @NotBlank(message = "The subject name cannot be empty.")
//    private String subjectName;
//
//    @NotBlank(message = "The email of coordinator cannot be empty.")
//    @Email(message = "Invalid email format.")
//    private String coordinatorEmail;
//
//
//    public CoordinatorTeachDto() {
//    }
//
//    public CoordinatorTeachDto(String subjectName, String coordinatorEmail) {
//        this.subjectName = subjectName;
//        this.coordinatorEmail = coordinatorEmail;
//    }
//
//    public String getSubjectName() {
//        return subjectName;
//    }
//
//    public void setSubjectName(String subjectName) {
//        this.subjectName = subjectName;
//    }
//
//    public String getCoordinatorEmail() {
//        return coordinatorEmail;
//    }
//
//    public void setCoordinatorEmail(String coordinatorEmail) {
//        this.coordinatorEmail = coordinatorEmail;
//    }
//}
