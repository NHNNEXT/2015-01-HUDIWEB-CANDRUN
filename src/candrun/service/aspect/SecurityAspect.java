package candrun.service.aspect;

import java.security.PrivateKey;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import candrun.service.SecurityService;
import candrun.support.enums.Security;

@Aspect
public class SecurityAspect {
	private static final Logger logger = LoggerFactory
			.getLogger(SecurityAspect.class);

	@Pointcut("execution(public * candrun.controller.UsersController.create(..)) "
			+ "|| execution(public * candrun.controller.AuthController.signIn(..))")
	public void decrypUserInfo() {
	};

	@SuppressWarnings("unchecked")
	@Around("decrypUserInfo()")
	public Map<String, String> beforeRegister(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		logger.debug("before start method");
		PrivateKey privateKey = (PrivateKey) ((HttpSession) args[0])
				.getAttribute(Security.RSA_PRI_KEY.getValue());
		args[1] = SecurityService.decrytRsa(privateKey, (String) args[1]);
		args[2] = SecurityService.decrytRsa(privateKey, (String) args[2]);
		logger.debug("email: " + (String) args[1] + "/" + "password: " + (String) args[2]); 
		return (Map<String, String>) pjp.proceed(args);
	}
}
