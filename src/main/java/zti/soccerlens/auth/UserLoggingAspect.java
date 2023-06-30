package zti.soccerlens.auth;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import zti.soccerlens.auth.api.LoginRequest;
import zti.soccerlens.auth.api.RegisterRequest;

@Aspect
@Component
public class UserLoggingAspect
{
	@Around("execution(* zti.soccerlens.auth.api.UserController.register(..))")
	public Object logRegister(ProceedingJoinPoint joinPoint) throws Throwable
	{
		RegisterRequest registerRequest = (RegisterRequest) joinPoint.getArgs()[0];
		System.out.println("New registration request: " + registerRequest.getUsername());
		Object result = joinPoint.proceed();
		System.out.println("User successfully registered: " + registerRequest.getUsername());
		return result;
	}

	@Around("execution(* zti.soccerlens.auth.api.UserController.login(..))")
	public Object logLogin(ProceedingJoinPoint joinPoint) throws Throwable
	{
		LoginRequest loginRequest = (LoginRequest) joinPoint.getArgs()[0];
		System.out.println("New login request: " + loginRequest.getUsername());
		Object result = joinPoint.proceed();
		System.out.println("User successfully logged in: " + loginRequest.getUsername());
		return result;
	}
}
