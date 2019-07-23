package com.t_systems.webstore.model.dto;

import com.t_systems.webstore.entity.Address;
import com.t_systems.webstore.entity.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Data
public class RegistrationForm
{
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String username;
    private String email;
    private String password;
    private String confirm;

    private String country;
    private String city;
    private String postCode;
    private String street;
    private String house;
    private String flat;

    public User toUser(PasswordEncoder encoder)
    {
        Address address = new Address();
        address.setCountry(country);
        address.setCity(city);
        address.setPostCode(postCode);
        address.setStreet(street);
        address.setHouse(house);
        address.setFlat(flat);

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setAddress(address);
        user.setDateOfBirth(dateOfBirth);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        return user;
    }
}
