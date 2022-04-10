package br.com.rodpk.productapi.config.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class SuccessResponse {

    private Integer status;
    private String message;
    
    public static SuccessResponse create(String message) {
        return SuccessResponse.builder()
                .status(200)
                .message(message)
                .build();
    }
}
