package com.blog.security;

import com.blog.exception.BlogApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    //values taken from application.properties
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    //generate JWT token:
    public String generateToken(Authentication authentication){
        String username = authentication.getName();

        Date currentDate = new Date();
        Date expiredDate = new Date(currentDate.getTime()+jwtExpirationDate);

        //creating and returning JWT token
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .signWith(key())
                .compact();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }


    //get username from JWT token
    public String getUsername(String token){
        return Jwts.parserBuilder()
                //using same key to generate the token:
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                //return exact username:
                .getSubject();

    }

    //validate JWT token
    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);

            return true;
        }
        catch (MalformedJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
        } catch (ExpiredJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Expired JWT Token");
        } catch (UnsupportedJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT Token");
        } catch (IllegalArgumentException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        }
    }

}
