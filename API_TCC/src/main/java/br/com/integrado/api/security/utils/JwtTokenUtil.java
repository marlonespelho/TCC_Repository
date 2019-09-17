package br.com.integrado.api.security.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {
	
	static final String CLAIM_KEY_USERNAME = "sub";
	static final String CLAIM_KEY_ROLE = "role";
	static final String CLAIM_KEY_CREATED = "created";
	@Value("${jwt.expiration}")
	private String secret;
	@Value("${jwt.expiration}")
	private Long expiration;
	
	public String getUsernameFromToken(String token) {
		try {
			Claims claims = getClaimsFromToken(token);
			return claims.getSubject();
		} catch (Exception e) {
			return null;
		}
	}
	
	public Date getExpirationDateFromToken(String token) {
		try {
			Claims claims = getClaimsFromToken(token);
			return claims.getExpiration();
		} catch (Exception e) {
			return null;
		}
	}
	
	public String refreshToken(String token) {
		try {
			Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED, new Date());
			return gerarToken(claims);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Boolean tokenValido(String token) {
		return !tokenExpirado(token);
	}
	
	public String obterToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		userDetails.getAuthorities().forEach(authority -> claims.put(CLAIM_KEY_ROLE, authority.getAuthority()));
		claims.put(CLAIM_KEY_CREATED, new Date());
		return gerarToken(claims);
	}
	
	private Claims getClaimsFromToken(String token) {
		try {
			return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}
	
	private Date gerarDataExpiracao() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}
	
	private boolean tokenExpirado(String token) {
		if (this.getExpirationDateFromToken(token)==null) {
			return false;
		}
		return this.getExpirationDateFromToken(token).before(new Date());
	}
	
	private String gerarToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(gerarDataExpiracao())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
}
