package zti.soccerlens.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import zti.soccerlens.auth.api.ProfileResponse;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User
{
	@Id
	@GeneratedValue
	private Long userId;
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	protected User() {}

	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
		this.role = Role.ROLE_USER;
	}

	public ProfileResponse mapToResponse()
	{
		return new ProfileResponse(this.userId, this.username, this.role.toString());
	}
}
