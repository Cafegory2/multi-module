package com.cafegory.auth.implement;

import com.cafegory.auth.domain.JwtAccessToken;
import com.cafegory.auth.domain.JwtToken;
import com.cafegory.auth.domain.TokenClaims;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
public final class JwtCafegoryTokenManager {

	private static final int ACCESS_TOKEN_LIFETIME_SECONDS = 3600;
	private static final int REFRESH_TOKEN_LIFETIME_SECONDS = 3600 * 24 * 7;

	private final JwtTokenManager jwtTokenManager;

	public JwtToken createAccessAndRefreshToken(final Map<String, Object> memberInformation) {
		Date issuedAt = Date.from(Instant.now());
		String accessToken = createAccessToken(memberInformation, issuedAt);
		String refreshToken = createRefreshToken(memberInformation, issuedAt);

		return new JwtToken(accessToken, refreshToken);
	}

	public JwtAccessToken createAccessToken(final Map<String, Object> memberInformation) {
		Date issuedAt = Date.from(Instant.now());
		return JwtAccessToken.builder()
			.accessToken(createAccessToken(memberInformation, issuedAt))
			.build();
	}

	private String createAccessToken(final Map<String, Object> claims, final Date issuedAt) {
		return jwtTokenManager.newTokenBuilder()
			.issuedAt(issuedAt)
			.lifeTimeAsSeconds(ACCESS_TOKEN_LIFETIME_SECONDS)
			.addAllClaims(claims)
			.addClaim(TokenClaims.TOKEN_TYPE.getValue(), TokenClaims.ACCESS_TOKEN.getValue())
			.build();
	}

	private String createRefreshToken(final Map<String, Object> claims, final Date issuedAt) {
		return jwtTokenManager.newTokenBuilder()
			.issuedAt(issuedAt)
			.lifeTimeAsSeconds(REFRESH_TOKEN_LIFETIME_SECONDS)
			.addAllClaims(claims)
			.addClaim(TokenClaims.TOKEN_TYPE.getValue(), TokenClaims.REFRESH_TOKEN.getValue())
			.build();
	}
}
