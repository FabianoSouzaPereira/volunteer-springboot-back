package com.fabianospdev.volunteer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Document(collection = "members")
public class Member {

    @Id
    private String id;
    private String name;
    private Integer age;
    private String group;
    private Set<MemberRole> roles = EnumSet.noneOf(MemberRole.class);
    private List<String> functions = new ArrayList<>();
    private MemberStatus status = MemberStatus.ACTIVE;
    private String phone;

    @Indexed(unique = true, sparse = true)
    private String email;

    private String address;
    private String job;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Member() {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Set<MemberRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<MemberRole> roles) {
        this.roles = roles == null ? EnumSet.noneOf(MemberRole.class) : roles;
    }

    public List<String> getFunctions() {
        return functions;
    }

    public void setFunctions(List<String> functions) {
        this.functions = functions == null ? new ArrayList<>() : functions;
    }

    public MemberStatus getStatus() {
        return status;
    }

    public void setStatus(MemberStatus status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
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

    public static final class Builder {
        private final Member member = new Member();

        public Builder id(String id) {
            member.setId(id);
            return this;
        }

        public Builder name(String name) {
            member.setName(name);
            return this;
        }

        public Builder age(Integer age) {
            member.setAge(age);
            return this;
        }

        public Builder group(String group) {
            member.setGroup(group);
            return this;
        }

        public Builder roles(Set<MemberRole> roles) {
            member.setRoles(roles);
            return this;
        }

        public Builder functions(List<String> functions) {
            member.setFunctions(functions);
            return this;
        }

        public Builder status(MemberStatus status) {
            member.setStatus(status);
            return this;
        }

        public Builder phone(String phone) {
            member.setPhone(phone);
            return this;
        }

        public Builder email(String email) {
            member.setEmail(email);
            return this;
        }

        public Builder address(String address) {
            member.setAddress(address);
            return this;
        }

        public Builder job(String job) {
            member.setJob(job);
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            member.setCreatedAt(createdAt);
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            member.setUpdatedAt(updatedAt);
            return this;
        }

        public Member build() {
            return member;
        }
    }
}
