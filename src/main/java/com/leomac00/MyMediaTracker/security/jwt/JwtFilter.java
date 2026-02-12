package com.leomac00.MyMediaTracker.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;


public class JwtFilter extends GenericFilterBean {
    @Autowired
    JwtProvider provider;

    public JwtFilter(JwtProvider provider) {
        this.provider = provider;
    }

    @Override
    public void doFilter(ServletRequest request,
                          ServletResponse response,
                          FilterChain filterChain) throws IOException, ServletException {

        try {
            String token = provider.resolveToken((HttpServletRequest) request);

            if (token != null && provider.validateToken(token)) {
                Authentication auth = provider.getAuthentication(token);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }

        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
