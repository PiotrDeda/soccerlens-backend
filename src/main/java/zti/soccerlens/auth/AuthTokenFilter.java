package zti.soccerlens.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter
{
	private final JwtUtils jwtUtils;
	private final UserDetailsService userDetailsService;

	public AuthTokenFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService)
	{
		this.jwtUtils = jwtUtils;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		try
		{
			String headerAuth = request.getHeader("Authorization");
			if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer "))
			{
				String jwt = headerAuth.substring(7);
				if (jwtUtils.validateJwtToken(jwt))
				{
					String username = jwtUtils.getUserNameFromJwtToken(jwt);
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		filterChain.doFilter(request, response);
	}
}
