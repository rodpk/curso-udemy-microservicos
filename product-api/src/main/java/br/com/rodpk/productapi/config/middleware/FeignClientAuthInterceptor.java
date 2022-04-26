package br.com.rodpk.productapi.config.middleware;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.com.rodpk.productapi.config.exception.ValidationException;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignClientAuthInterceptor implements RequestInterceptor{
    
    private static final String AUTHORIZATION = "Authorization";
    @Override
    public void apply(RequestTemplate template) {
        var currentRequest = getCurrentRequest();
        template.header(AUTHORIZATION, currentRequest.getHeader(AUTHORIZATION)); // repassa nosso token para a aplicacao
    }

    private HttpServletRequest getCurrentRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        } catch(Exception ex) {
            ex.printStackTrace();
            throw new ValidationException("current request could not be processed.");            
        }
    }
}
