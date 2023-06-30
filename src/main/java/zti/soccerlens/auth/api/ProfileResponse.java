package zti.soccerlens.auth.api;

import lombok.Value;

@Value
public class ProfileResponse
{
	long userId;
	String username;
	String role;
}
