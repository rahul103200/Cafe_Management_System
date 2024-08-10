package com.in.cafe.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {

   // private String secret = "413F4428472B4B6250655368566D5970337336763979244226452948404D6351";

//    public String generateToken(UserDetails userDetails){
//        return Jwts.builder().setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() * 1000 * 60 * 24))
//                .signWith(getSiginKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }

    public String generateToken(String username,String role){
        Map<String,Object>claims = new HashMap<>();
        claims.put("role",role);
        return createToken(claims,username);
    }

    private String createToken(Map<String,Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256,getSiginKey()).compact();
    }

    public String extractUserName(String token){
        return extractClaims(token,Claims::getSubject);
    }

    private <T> T extractClaims(String token , Function<Claims,T> claimsResolvers){
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Key getSiginKey(){
        byte[] key = Decoders.BASE64.decode("413F4428472B4B6250655368566D5970337336763979244226452948404D6351");
        return Keys.hmacShaKeyFor(key);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getSiginKey()).build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
       return extractClaims(token,Claims::getExpiration).before(new Date());
    }

}
