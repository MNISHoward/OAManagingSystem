package conf.datasource;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.howard.commonClass.BaseJunit4Test;
import ch.howard.frame.dao.DeptDAO;
import ch.howard.frame.model.Department;
import ch.howard.frame.service.DeptService;
import ch.howard.frame.service.StaffService;

public class HibernateDataSourceTest extends BaseJunit4Test {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	DeptService deptService;
	
	@Autowired
	StaffService staffService;
	
	@Autowired
	DeptDAO deptDao;
	
	@Test
	public void testStaff() {
		staffService.insertStaff();
	}
	
	@Test
	public void testUtf8() {
		Department dept = deptDao.findOne(1);
		System.out.println(dept.getName());
	}
	
	@Test
	public void testJPA() {
		deptService.insertDept();
	}
	
	
	@Test
	public void testDataSource() {
		System.out.println(dataSource);
	}
	
}
