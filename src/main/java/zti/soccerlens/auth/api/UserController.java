package zti.soccerlens.auth.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import zti.soccerlens.auth.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController
{
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;

	public UserController(AuthenticationManager authenticationManager, UserRepository userRepository,
						  PasswordEncoder passwordEncoder, JwtUtils jwtUtils)
	{
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtils = jwtUtils;
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest)
	{
		int usernameLength = registerRequest.getUsername().length();
		if (usernameLength < 4 || usernameLength > 48)
			return ResponseEntity.badRequest().body(
					new MessageResponse("Username must be between 4 and 48 characters long."));

		int passwordLength = registerRequest.getPassword().length();
		if (passwordLength < 8 || passwordLength > 48)
			return ResponseEntity.badRequest().body(
					new MessageResponse("Password must be between 8 and 48 characters long."));

		if (userRepository.existsByUsername(registerRequest.getUsername()))
			return ResponseEntity.badRequest().body(new MessageResponse("Username already taken."));

		userRepository.save(new User(registerRequest.getUsername(), passwordEncoder.encode(registerRequest.getPassword())));

		return authenticate(new UsernamePasswordAuthenticationToken(registerRequest.getUsername(),
				registerRequest.getPassword()));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest)
	{
		return authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
				loginRequest.getPassword()));
	}

	@GetMapping("/profile")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> profile()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		return ResponseEntity.ok(new ProfileResponse(userDetails.getUserId(), userDetails.getUsername(),
				userDetails.getAuthorities().iterator().next().getAuthority()));
	}

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> getAllUsers()
	{
		return ResponseEntity.ok(userRepository.findAll().stream().map(User::mapToResponse).toList());
	}

	@PatchMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest updateUserRequest)
	{
		User user = userRepository.findById(id).orElseThrow();
		if (updateUserRequest.getUsername() != null)
			user.setUsername(updateUserRequest.getUsername());
		if (updateUserRequest.getPassword() != null)
			user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
		if (updateUserRequest.getRole() != null)
			user.setRole(Role.valueOf(updateUserRequest.getRole()));
		userRepository.save(user);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> deleteUser(@PathVariable Long id)
	{
		userRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

	private ResponseEntity<?> authenticate(UsernamePasswordAuthenticationToken authenticationToken)
	{
		Authentication authentication;
		try
		{
			authentication = authenticationManager.authenticate(authenticationToken);
		}
		catch (AuthenticationException e)
		{
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid username or password."));
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUserId(), userDetails.getUsername(), roles));
	}
}
