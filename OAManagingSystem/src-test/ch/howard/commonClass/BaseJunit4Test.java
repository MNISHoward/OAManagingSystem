package ch.howard.commonClass;


import org.apache.logging.log4j.core.config.Configurator;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试  
@WebAppConfiguration
@ContextConfiguration   
({"classpath:conf/spring/applicationContext.xml"}) //加载配置文件  
public class BaseJunit4Test {
	static {
		Configurator.initialize("Log4j2", "classpath:conf/log/log4j2.xml");
	}
}