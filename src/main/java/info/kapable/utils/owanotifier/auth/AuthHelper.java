/**
The MIT License (MIT)

Copyright (c) 2017 Mathieu GOULIN

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package info.kapable.utils.owanotifier.auth;

import info.kapable.utils.owanotifier.JacksonConverter;
import info.kapable.utils.owanotifier.OwaNotifier;
import info.kapable.utils.owanotifier.RestfullAcessProxy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.OkClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;

public class AuthHelper {
	private static final String authority = "https://login.microsoftonline.com";
	private static final String authorizeUrl = authority
			+ "/common/oauth2/v2.0/authorize";

	private static String[] scopes = { "openid", "offline_access", "profile",
			"User.Read", "Mail.Read" };

	private static String appId = null;
	private static String appPassword = null;
	private static String redirectUrl = null;

	// The logger
    private static Logger logger = LoggerFactory.getLogger(AuthHelper.class);
    
	private static String getAppId() {
		if (appId == null) {
			try {
				appId = OwaNotifier.getProps().getProperty("appId");
			} catch (Exception e) {
				return null;
			}
		}
		return appId;
	}

	private static String getAppPassword() {
		if (appPassword == null) {
			try {
				appPassword = OwaNotifier.getProps().getProperty("appPassword");
			} catch (Exception e) {
				return null;
			}
		}
		return appPassword;
	}

	private static String getRedirectUrl() throws NumberFormatException,
			IOException {
		int listenPort = Integer.parseInt(OwaNotifier.getProps().getProperty(
				"listenPort"));
		if (redirectUrl == null) {
			try {
				redirectUrl = OwaNotifier.getProps().getProperty("redirectUrl");
				redirectUrl = redirectUrl.replace("8080", listenPort + "");
			} catch (Exception e) {
				return null;
			}
		}
		return redirectUrl;
	}

	private static String getScopes() {
		StringBuilder sb = new StringBuilder();
		for (String scope : scopes) {
			sb.append(scope + " ");
		}
		return sb.toString().trim();
	}

	public static String getLoginUrl(UUID state, UUID nonce, int listenPort)
			throws NumberFormatException, IOException {

		StringBuilder urlBuilder = new StringBuilder(authorizeUrl);
		urlBuilder.append("?client_id=" + getAppId());
		urlBuilder.append("&redirect_uri=" + getRedirectUrl());
		urlBuilder.append("&response_type=" + "code%20id_token");
		urlBuilder.append("&scope=" + getScopes().replaceAll(" ", "%20"));
		urlBuilder.append("&state=" + state);
		urlBuilder.append("&nonce=" + nonce);
		urlBuilder.append("&response_mode=" + "form_post");

		return urlBuilder.toString();
	}

	public static TokenResponse getTokenFromRefresh(TokenResponse tokens,
			String tenantId) {
		TokenService tokenService = RestfullAcessProxy.getTokenService(authority);

		try {
			JacksonConverter c = new JacksonConverter(new ObjectMapper());
			return (TokenResponse) c.fromBody(
					tokenService.getAccessTokenFromRefreshToken(tenantId,
							getAppId(), getAppPassword(), "refresh_token",
							tokens.getRefreshToken(), getRedirectUrl())
							.getBody(), TokenResponse.class);
		} catch (IOException e) {
			TokenResponse error = new TokenResponse();
			error.setError("IOException");
			error.setErrorDescription(e.getMessage());
			return error;
		} catch (retrofit.RetrofitError e) {
			TokenResponse error = new TokenResponse();
			error.setError("RetrofitError");
			error.setErrorDescription(e.getMessage());
			return error;
		}
	}

	public static TokenResponse getTokenFromAuthCode(String authCode,
			String tenantId) {
		TokenService tokenService = RestfullAcessProxy.getTokenService(authority);
		
		try {
			JacksonConverter c = new JacksonConverter(new ObjectMapper());
			return (TokenResponse) c.fromBody(
					tokenService.getAccessTokenFromAuthCode(tenantId,
							getAppId(), getAppPassword(), "authorization_code",
							authCode, getRedirectUrl()).getBody(),
					TokenResponse.class);
		} catch (IOException e) {
			TokenResponse error = new TokenResponse();
			error.setError("IOException");
			error.setErrorDescription(e.getMessage());
			return error;
		} catch (retrofit.RetrofitError e) {
			TokenResponse error = new TokenResponse();
			error.setError("RetrofitError");
			error.setErrorDescription(e.getMessage());
			return error;
		}
	}

}