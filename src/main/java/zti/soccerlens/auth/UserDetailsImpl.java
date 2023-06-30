package zti.soccerlens.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class UserDetailsImpl implements UserDetails
{
	private final Long userId;
	private final String username;
	@JsonIgnore
	private final String password;
	private final Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities)
	{
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(User user)
	{
		return new UserDetailsImpl(user.getUserId(), user.getUsername(), user.getPassword(),
				List.of(new SimpleGrantedAuthority(user.getRole().name())));
	}


	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}
}
