package com.github.to2mbn.jyal;

public interface SessionService {

	Session login(String username, String password) throws AuthenticationException;

	Session loginWithToken(String username, String token) throws AuthenticationException;

}
