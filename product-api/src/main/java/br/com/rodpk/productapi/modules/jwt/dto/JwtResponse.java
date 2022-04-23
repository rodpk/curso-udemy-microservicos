package br.com.rodpk.productapi.modules.jwt.dto;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    
    private Integer id;
    private String name;
    private String email;

    public static JwtResponse getUser(Claims jwtClaims) {
        try {
            return JwtResponse.builder()
                    .id(Integer.parseInt(jwtClaims.get("id").toString()))
                    .name(jwtClaims.get("name").toString())
                    .email(jwtClaims.get("email").toString())
                    .build();
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
