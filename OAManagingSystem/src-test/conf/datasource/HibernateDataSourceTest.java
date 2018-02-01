package conf.datasource;

import java.util.HashSet;
import java.util.Set;

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
	public void testDept2() {
		Department department = deptDao.findOne(2);
		System.out.println(department.getDepartments());
	}
	
	@Test
	public void testDept() {
		Department dept = new Department("业务一部", deptDao.findOne(3),null);
		Department dept2 = new Department("技术一部", deptDao.findOne(2), null);
		Department dept3 = new Department("财务一部", deptDao.findOne(5), null);
		Department dept4 = new Department("行政一部", deptDao.findOne(4), null);
		Set<Department> set = new HashSet<>();
		set.add(dept);
		set.add(dept2);
		set.add(dept3);
		set.add(dept4);
		deptDao.save(set);
	}
	
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
