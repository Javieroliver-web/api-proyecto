package gateway.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

/**
 * Validador de tokens JWT
 * IMPORTANTE: Debe usar la MISMA clave secreta que el backend
 */
public class JWTValidator {
    
    // DEBE SER LA MISMA que en tu backend (JWTUtil.java)
    private static final String SECRET_KEY = "mi_clave_secreta_super_segura_para_jwt_con_256_bits_minimo_requerido_12345";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    
    /**
     * Valida un token JWT
     */
    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.err.println("⚠️ Token expirado");
            return false;
        } catch (JwtException e) {
            System.err.println("⚠️ Token inválido: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene el ID del usuario desde el token
     */
    public static int getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
            
            return Integer.parseInt(claims.getSubject());
        } catch (Exception e) {
            throw new RuntimeException("Error extrayendo userId del token", e);
        }
    }
    
    /**
     * Obtiene el email del usuario desde el token
     */
    public static String getEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
            
            return claims.get("email", String.class);
        } catch (Exception e) {
            throw new RuntimeException("Error extrayendo email del token", e);
        }
    }
    
    /**
     * Obtiene el rol del usuario desde el token
     */
    public static String getRolFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
            
            return claims.get("rol", String.class);
        } catch (Exception e) {
            throw new RuntimeException("Error extrayendo rol del token", e);
        }
    }
}