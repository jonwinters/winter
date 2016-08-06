package top.sourcecode.winter.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TestAspect {

	private static final Logger LOG = LoggerFactory.getLogger(TestAspect.class);
	
	@Pointcut("execution(* top.sourcecode.winter.service.impl.AspectServiceImpl.*(..))")
	private void test() {
		
	}
	
	@Before("test()")
	public void beforeAdvice() {
		System.out.println("before advice");
	}
	
	@After("test()")
	public void afterAdvice() {
		System.out.println("after advice");
	}
	
	@AfterReturning(pointcut = "test()", returning="retVal")
	public void afterReturningAdvice(Object retVal){
		if(retVal == null) {
			System.out.println("return null");
			return;
		}
	    System.out.println("Returning:" + retVal.toString() );
	}
	
	@AfterThrowing(pointcut = "test()", throwing = "ex")
    public void AfterThrowingAdvice(Exception ex){
		LOG.error("There has been an exception: {}", ex.toString());
    }
}
