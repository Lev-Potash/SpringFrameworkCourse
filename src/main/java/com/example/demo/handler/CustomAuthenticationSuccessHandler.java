package com.example.demo.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Класс, который отвечает за успешный вход в систему.
 * При успешном логине, в ответ будет приходить статус 200.
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }



    protected void handle(HttpServletRequest request,
                          HttpServletResponse response, Authentication authentication)
            throws IOException {

        System.out.println("authentication.getAuthorities() " + authentication.getAuthorities());
        System.out.println("authentication.getCredentials() " + authentication.getCredentials());
        System.out.println("authentication.getDetails() " + authentication.getDetails());
        System.out.println("authentication.isAuthenticated() " + authentication.isAuthenticated());
        authentication.setAuthenticated(false);
        System.out.println("authentication.getAuthorities() " + authentication.isAuthenticated());
        /*authentication.getAuthorities();
        authentication.getCredentials();
        authentication.getDetails();
        authentication.isAuthenticated();
        authentication.setAuthenticated(false);*/
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().flush();
        response.getWriter().close();
    }



    protected void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

}
