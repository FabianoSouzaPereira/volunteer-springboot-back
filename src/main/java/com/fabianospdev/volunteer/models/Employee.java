package com.fabianospdev.volunteer.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection="employee")
public class Employee implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String name;
    private int age;
    private String group;
    private String role;
    private List<String> functions;
    private String status;

    private String phone;
    private String email;
    private String address;
    private String job;

    public Employee() {}

    public Employee(String id, String name, String email, String phone) {
        this.id = id;
        this.name = name != null ? name : "";
        this.email = email != null ? email : "";
        this.phone = phone != null ? phone : "";
    }

    public Employee(String id, String name, int age, String group, String role, List<String> functions, String status, String phone, String email, String address, String job) {
        this.id = id;
        this.name = name != null ? name : "";
        this.age = age > 0 ? age : 0;
        this.group = group != null ? group : "";
        this.role = role != null ? role : "";
        this.functions = functions != null ? functions : new ArrayList<>();
        this.status = status != null ? status : "";
        this.phone = phone != null ? phone : "";
        this.email = email != null ? email : "";
        this.address = address != null ? address : "";
        this.job = job != null ? job : "";
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getFunctions() {
        return functions;
    }

    public void setFunctions(List<String> functions) {
        this.functions = functions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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


    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}