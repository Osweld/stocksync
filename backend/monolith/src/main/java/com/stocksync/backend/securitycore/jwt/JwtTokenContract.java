package com.stocksync.backend.securitycore.jwt;

import org.springframework.security.core.Authentication;

public interface JwtTokenContract {
    
    String generateAccessToken(Authentication authentication);

   
    String generateRefreshToken(Authentication authentication);
}