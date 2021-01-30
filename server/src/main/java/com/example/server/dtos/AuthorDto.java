package com.example.server.dtos;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class AuthorDto {

    private Long id;

    @Size(min = 2, max = 20, message = "first name must be between 2 and 20 characters")
    private String firstName;

    @Size(min = 2, max = 30, message = "first name must be between 2 and 30 characters")
    private String lastName;

    @NotNull(message = "birth date can not be empty.")
    private Date birthDate;

    @Size(min = 1, max = 250, message = "Memoir must be between 1 and 250 characters")
    private String memoir;

    @URL(message = "image must be url.")
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
