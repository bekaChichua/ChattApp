package com.beka.chatapp.security.securityFilters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.beka.chatapp.entity.AppUser;
import com.beka.chatapp.service.MyUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;



public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/authenticate");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AppUser user = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
//            System.out.println("sucsess1");
//            String username = request.getParameter("username");
//            String password = request.getParameter("password");
//            System.out.println(username + " " + password);
            return authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( user.getUsername(), user.getPassword()) );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("bla1");
        String jwtToken = JWT.create()
                .withSubject(((MyUserDetails) authResult.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000*60*60*7))
                .sign(Algorithm.HMAC512("Yle".getBytes()));
        String body = ((MyUserDetails) authResult.getPrincipal()).getUsername() + " " + jwtToken;
        System.out.println("bla");
        response.getWriter().write(body);
        response.getWriter().flush();
    }
}
