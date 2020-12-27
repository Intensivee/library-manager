package com.example.server.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class AuthorDto {


    private Long id;
    @NotBlank(message = "first name can not be empty.")
    private String firstName;
    @NotBlank(message = "last name can not be empty.")
    private String lastName;
    @NotNull(message = "birth date can not be empty.")
    private Date birthDate;
    @NotBlank(message = "memoir can not be empty.")
    private String memoir;
    @NotBlank(message = "image can not be empty.")
    private String imageUrl;


    public AuthorDto() {
    }

    public AuthorDto(Long id, String firstName, String lastName, Date birthDate, String memoir, String imageUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.memoir = memoir;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getMemoir() {
        return memoir;
    }

    public void setMemoir(String memoir) {
        this.memoir = memoir;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
