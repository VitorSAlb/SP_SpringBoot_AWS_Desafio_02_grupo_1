package com.compass.desafio02.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserPasswordDto {

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$", message = "The password must have at least one uppercase letter, one lowercase letter, one number, one special character and at least 8 characters.")
    private String currentPassword;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$", message = "The password must have at least one uppercase letter, one lowercase letter, one number, one special character and at least 8 characters.")
    private String newPassword;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$", message = "The password must have at least one uppercase letter, one lowercase letter, one number, one special character and at least 8 characters.")
    private String confirmPassword;

    public UserPasswordDto() {
    }

    public UserPasswordDto(String currentPassword, String newPassword, String confirmPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public @NotBlank @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$", message = "The password must have at least one uppercase letter, one lowercase letter, one number, one special character and at least 8 characters.") String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(@NotBlank @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$", message = "The password must have at least one uppercase letter, one lowercase letter, one number, one special character and at least 8 characters.") String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public @NotBlank @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$", message = "The password must have at least one uppercase letter, one lowercase letter, one number, one special character and at least 8 characters.") String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(@NotBlank @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$", message = "The password must have at least one uppercase letter, one lowercase letter, one number, one special character and at least 8 characters.") String newPassword) {
        this.newPassword = newPassword;
    }

    public @NotBlank @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$", message = "The password must have at least one uppercase letter, one lowercase letter, one number, one special character and at least 8 characters.") String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotBlank @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$", message = "The password must have at least one uppercase letter, one lowercase letter, one number, one special character and at least 8 characters.") String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
