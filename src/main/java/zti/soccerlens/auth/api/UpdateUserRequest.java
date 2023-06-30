package zti.soccerlens.auth.api;

import lombok.Value;

@Value
public class UpdateUserRequest
{
	String username;
	String password;
	String role;
}
