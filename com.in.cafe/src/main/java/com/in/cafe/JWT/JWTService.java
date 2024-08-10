package com.in.cafe.JWT;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {

    String generateToken(String username,String role);

    String extractUserName(String token);

    boolean isTokenValid(String token,UserDetails userDetails);

    Claims extractAllClaims(String token);

}
