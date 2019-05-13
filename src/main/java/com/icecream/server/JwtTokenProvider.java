package com.icecream.server;

import com.icecream.server.entity.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

  private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

  private static final String jwtSecret = "IceCreamServerJWTSuperSecretKey1234567890"
          + "IceCreamServerJWTSuperSecretKey123456789"
          + "0IceCreamServerJWTSuperSecretKey1234567890Ic"
          + "eCreamServerJWTSuperSecretKey1234567890IceCream"
          + "ServerJWTSuperSecretKey1234567890";

  private static final long jwtExpirationInMs = 604800000; // 1month

  /**
   * To generate a token.
   * @param user a user entity
   * @return the generated token
   */
  public String generateToken(User user) {

    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
    return Jwts.builder()
            .setSubject(Long.toString(user.getId()))
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
  }

  /**
   * To get a user id from token.
   * @param token a representation of a user
   * @return the id of this user
   */
  public Long getUserIdFromJWT(String token) {
    Claims claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody();

    return Long.parseLong(claims.getSubject());
  }

  /**
   * To check if the token is valid.
   * @param authToken a representation of a user
   * @return valid or not
   */
  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException ex) {
      logger.error("Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      logger.error("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      logger.error("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      logger.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      logger.error("JWT claims string is empty.");
    }
    return false;

  }
}
