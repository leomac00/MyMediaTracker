package com.leomac00.MyMediaTracker.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.leomac00.MyMediaTracker.data.dtos.TokenDto;
import com.leomac00.MyMediaTracker.exceptions.InvalidJwtAuthException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtProvider {
    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";
    @Value("${security.jwt.token.expire-length:3600000}")
    private long expireLength = 3600000; //1h in ms
    @Autowired
    private UserDetailsService userDetailsService;
    private final static String BEARER = "Bearer ";
    private Algorithm algorithm = null;

    @PostConstruct
    protected void setup(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey);
    }

    public TokenDto createAccessToken(String username, List<String> roles){
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expireLength);
        var accessToken = getAccessToken(username, roles, now, expiry);
        var refreshToken = getRefreshToken(username, roles, now);


        return new TokenDto(username, true, accessToken, refreshToken, now, expiry);
    }
    public TokenDto createRefreshToken(String token){
        if (token.contains(BEARER)) token = token.substring(BEARER.length());

        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        var username = decodedJWT.getSubject();
        var roles = decodedJWT.getClaim("roles").asList(String.class);

        return createAccessToken(username, roles);
    }
    private DecodedJWT decodeJwt(String token){
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
    private String getAccessToken(String username, List<String> permissions, Date now, Date expiry) {
        String issuerUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .build()
                .toUriString();
        return JWT.create()
                .withClaim("authorities", permissions)
                .withIssuedAt(now)
                .withExpiresAt(expiry)
                .withSubject(username)
                .withIssuer(issuerUrl)
                .sign(algorithm)
                .strip();
    }
    public Authentication getAuthentication(String token){
        DecodedJWT decodedJWT = decodeJwt(token);
        UserDetails userDetails = this.userDetailsService
                .loadUserByUsername(decodedJWT.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    private String getRefreshToken(String username, List<String> permissions, Date now) {
        var expiry = new Date(now.getTime() + (expireLength * 3));
        return JWT.create()
                .withClaim("authorities", permissions)
                .withIssuedAt(now)
                .withExpiresAt(expiry)
                .withSubject(username)
                .sign(algorithm)
                .strip();
    }
    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        return bearerToken != null && bearerToken.startsWith(BEARER)
                ? bearerToken.substring(BEARER.length())
                : null;
    }
    public boolean validateToken(String token){
        DecodedJWT decodedJWT = decodeJwt(token);
        try{
            return decodedJWT.getExpiresAt().before(new Date())
                    ? false
                    : true;
        } catch (Exception e){
            return false;
        }
    }

}
