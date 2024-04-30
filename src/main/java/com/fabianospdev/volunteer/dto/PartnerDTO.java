package com.fabianospdev.volunteer.dto;

import com.fabianospdev.volunteer.models.Partner;


import java.io.Serializable;

public class PartnerDTO implements Serializable {

    private String id;
    private String name;
    private String phone;
    private String email;


    public String getPhone() {
        return phone;
    }

    public void setPhone( String phone ) {
        this.phone = phone;
    }

    public PartnerDTO(){}

    public PartnerDTO( Partner obj){
        id = obj.getId();
        name = obj.getName();
        email = obj.getEmail();
        phone = obj.getPhone();
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

}