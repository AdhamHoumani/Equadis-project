package com.core.Core.customer.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Type(type = "pg-uuid")
    private UUID id;

    @NotBlank(message = "{should.not.be.empty.key}")
    @Column(name = "first_name", nullable = false)
    private String firstName;


    @NotBlank(message = "{should.not.be.empty.key}")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "{should.not.be.empty.key}")
    @Column(unique = true, name = "email", nullable = false)
    private String email;


    @NotBlank(message = "{should.not.be.empty.key}")
    @Column(unique = true,name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotNull(message = "{should.not.be.empty.key}")
    @Column(name = "address", nullable = false)
    private String address;
}
