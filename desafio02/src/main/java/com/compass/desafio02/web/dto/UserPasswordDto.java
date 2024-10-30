package com.compass.desafio02.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserPasswordDto {

    @NotBlank
    @Size(min = 6, max = 8)
    private String currentPassword;
    @NotBlank
    @Size(min = 6, max = 8)
    private String newPassword;
    @NotBlank
    @Size(min = 6, max = 8)
    private String confirmPassword;

    public UserPasswordDto() {
    }

    public UserPasswordDto(String currentPassword, String newPassword, String confirmPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public @NotBlank @Size(min = 6, max = 8) String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(@NotBlank @Size(min = 6, max = 8) String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public @NotBlank @Size(min = 6, max = 8) String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(@NotBlank @Size(min = 6, max = 8) String newPassword) {
        this.newPassword = newPassword;
    }

    public @NotBlank @Size(min = 6, max = 8) String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotBlank @Size(min = 6, max = 8) String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
