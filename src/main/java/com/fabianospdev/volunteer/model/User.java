package com.fabianospdev.volunteer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String name;

    @Indexed(unique = true)
    private String email;

    private String phone;

    @JsonIgnore
    private String password;

    private boolean enabled = true;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "User{id='" + id + "', email='" + email + "'}";
    }

    public static final class Builder {
        private final User user = new User();

        public Builder id(String id) {
            user.setId(id);
            return this;
        }

        public Builder name(String name) {
            user.setName(name);
            return this;
        }

        public Builder email(String email) {
            user.setEmail(email);
            return this;
        }

        public Builder phone(String phone) {
            user.setPhone(phone);
            return this;
        }

        public Builder password(String password) {
            user.setPassword(password);
            return this;
        }

        public Builder enabled(boolean enabled) {
            user.setEnabled(enabled);
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            user.setCreatedAt(createdAt);
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            user.setUpdatedAt(updatedAt);
            return this;
        }

        public User build() {
            return user;
        }
    }
}
