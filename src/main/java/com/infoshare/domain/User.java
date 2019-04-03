package com.infoshare.domain;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    private String login;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private UserStatus admin;
    private String status;

}

