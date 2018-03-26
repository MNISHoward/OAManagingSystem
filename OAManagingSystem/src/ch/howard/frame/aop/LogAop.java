package ch.howard.frame.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Aspect
@Component
public class LogAop {
	
	private static final transient Logger log = LoggerFactory.getLogger(LogAop.class);
	
	@Pointcut(value = "execution(* ch.howard..*.service.*.*(..))")
	public void myPoint() {
	}
	
	@Before("myPoint()")
	public void logStart(JoinPoint joinPoint) {
	    // 获取参数列表
		Object[] args = joinPoint.getArgs();
		Object target = joinPoint.getTarget();
		String className = target.getClass().getName();
		className = className.substring(className.lastIndexOf('.') + 1);
		// 获取方法签名
		Signature signature = joinPoint.getSignature();
		// 获取方法名
		String name = signature.getName();
		log.info("执行(" + className + "的" + name + ")开始," + "参数列表："
			            + Arrays.asList(args));
	}
	
	@After(value = "myPoint()")
    public void logEnd(JoinPoint joint) {
		Object target = joint.getTarget();
		String className = target.getClass().getName();
		className = className.substring(className.lastIndexOf('.') + 1);
        String name = joint.getSignature().getName();
        log.info("执行(" + className + "的" + name + ")结束");
    }
}
