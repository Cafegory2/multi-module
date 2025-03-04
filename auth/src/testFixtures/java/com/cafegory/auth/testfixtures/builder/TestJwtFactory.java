package com.cafegory.auth.testfixtures.builder;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static com.cafegory.auth.domain.TokenClaims.*;

public class TestJwtFactory {

	private static final String TEST_SECRET = "01234567890123456789012345678901234567890123456789";

	public static String createAccessToken(Map<String, Object> claims, Date issuedAt, int lifeTimeAsSeconds,
		String testSecret) {
		return Jwts.builder()
			.header().type("JWT").and()
			.claims(claims)
			.issuedAt(issuedAt)
			.expiration(Date.from(issuedAt.toInstant().plusSeconds(lifeTimeAsSeconds)))
			.signWith(Keys.hmacShaKeyFor(testSecret.getBytes()))
			.compact();
	}

	public static String createAccessToken(Long memberId, String testSecret) {
		Date issuedAt = Date.from(Instant.now());
		return Jwts.builder()
			.header().type("JWT").and()
			.claim(TOKEN_TYPE.getValue(), ACCESS_TOKEN.getValue())
			.claim(SUBJECT.getValue(), String.valueOf(memberId))
			.issuedAt(issuedAt)
			.expiration(Date.from(issuedAt.toInstant().plusSeconds(3600)))
			.signWith(Keys.hmacShaKeyFor(testSecret.getBytes()))
			.compact();
	}

	public static String createAccessToken(Long memberId) {
		Date issuedAt = Date.from(Instant.now());
		return Jwts.builder()
			.header().type("JWT").and()
			.claim(TOKEN_TYPE.getValue(), ACCESS_TOKEN.getValue())
			.claim(SUBJECT.getValue(), String.valueOf(memberId))
			.issuedAt(issuedAt)
			.expiration(Date.from(issuedAt.toInstant().plusSeconds(3600)))
			.signWith(Keys.hmacShaKeyFor(TEST_SECRET.getBytes()))
			.compact();
	}

	public static String createRefreshToken(Long memberId, String testSecret) {
		Date issuedAt = Date.from(Instant.now());
		return Jwts.builder()
			.header().type("JWT").and()
			.claim(TOKEN_TYPE.getValue(), REFRESH_TOKEN.getValue())
			.claim(SUBJECT.getValue(), String.valueOf(memberId))
			.issuedAt(issuedAt)
			.expiration(Date.from(issuedAt.toInstant().plusSeconds(3600)))
			.signWith(Keys.hmacShaKeyFor(testSecret.getBytes()))
			.compact();
	}

	public static String createExpiredAccessToken(Long memberId, String testSecret) {
		Date issuedAt = Date.from(Instant.now());
		return Jwts.builder()
			.header().type("JWT").and()
			.claim(TOKEN_TYPE.getValue(), ACCESS_TOKEN.getValue())
			.claim(SUBJECT.getValue(), String.valueOf(memberId))
			.issuedAt(issuedAt)
			.expiration(Date.from(issuedAt.toInstant().plusSeconds(0)))
			.signWith(Keys.hmacShaKeyFor(testSecret.getBytes()))
			.compact();
	}

	public static String createExpiredRefreshToken(Long memberId, String testSecret) {
		Date issuedAt = Date.from(Instant.now());
		return Jwts.builder()
			.header().type("JWT").and()
			.claim(TOKEN_TYPE.getValue(), REFRESH_TOKEN.getValue())
			.claim(SUBJECT.getValue(), String.valueOf(memberId))
			.issuedAt(issuedAt)
			.expiration(Date.from(issuedAt.toInstant().plusSeconds(0)))
			.signWith(Keys.hmacShaKeyFor(testSecret.getBytes()))
			.compact();
	}
}
