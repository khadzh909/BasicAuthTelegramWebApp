package com.example.telegramauth.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    private Long id;
    private String first_name;
    private String last_name;
    private String username;
    private String language_code;
    private boolean allows_write_to_pm;

    public User() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return allows_write_to_pm == user.allows_write_to_pm && Objects.equals(id, user.id) && Objects.equals(first_name, user.first_name) && Objects.equals(last_name, user.last_name) && Objects.equals(username, user.username) && Objects.equals(language_code, user.language_code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name, username, language_code, allows_write_to_pm);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    public boolean getAllows_write_to_pm() {
        return allows_write_to_pm;
    }

    public void setAllows_write_to_pm(boolean allows_write_to_pm) {
        this.allows_write_to_pm = allows_write_to_pm;
    }
}
