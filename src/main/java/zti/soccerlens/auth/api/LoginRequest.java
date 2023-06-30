package zti.soccerlens.auth.api;

import lombok.Value;

@Value
public class LoginRequest
{
	String username;
	String password;
}
