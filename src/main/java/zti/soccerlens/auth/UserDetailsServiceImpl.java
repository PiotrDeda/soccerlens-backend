package zti.soccerlens.auth;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
	private final UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username)
	{
		return UserDetailsImpl.build(userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username)));
	}
}
