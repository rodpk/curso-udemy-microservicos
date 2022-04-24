package br.com.rodpk.productapi.modules.jwt.dto;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    public static JwtResponse getUserMyVersion(Claims jwtClaims) {
        try {

            boolean containsKey = jwtClaims.containsKey("authUser");

            if (containsKey) {
                var authUser = jwtClaims.get("authUser", LinkedHashMap.class);
                Integer id = (Integer) authUser.get("id");
                String name = (String) authUser.get("name");
                String email = (String) authUser.get("email");
                return JwtResponse.builder()
                        .id(id)
                        .name(name)
                        .email(email)
                        .build();
            } else {
                return null;
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // metodo usado no curso
    public static JwtResponse getUser(Claims jwtClaims) {
        try {
            return new ObjectMapper().convertValue(jwtClaims.get("authUser"), JwtResponse.class);
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

}
