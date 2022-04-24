package br.com.rodpk.productapi.modules.jwt.dto;

import lombok.Data;

@Data
public class AuthUser {

    private Integer id;
    private String name;
    private String email;
    
}
