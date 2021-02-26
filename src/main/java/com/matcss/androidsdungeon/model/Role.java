package com.matcss.androidsdungeon.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int roleId;

    private String role_name;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customerId")
    @JsonBackReference
    private Customer customer;

    public Role(int roleId, String role_name) {
        this.roleId = roleId;
        this.role_name = role_name;
    }
}
