package ch.howard.commonClass;

import java.io.FileNotFoundException;

import org.apache.struts2.StrutsSpringTestCase;
import org.springframework.util.Log4jConfigurer;

public class StrutsSpringTest extends StrutsSpringTestCase {
	
	protected String[] getContextLocations() {  
        return new String[] {"classpath*:conf/spring/applicationContext.xml"};  
    }  
}
