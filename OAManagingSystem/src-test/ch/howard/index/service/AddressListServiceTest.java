package ch.howard.index.service;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.howard.commonClass.BaseJunit4Test;
import ch.howard.frame.model.User;
import ch.howard.index.dao.AddressListDAO;
import ch.howard.index.model.AddressList;

public class AddressListServiceTest extends BaseJunit4Test {

	@Autowired
	private AddressListDAO addressListDao; 
	
	@Test
	public void test() {
		for(int i = 0; i < 20; i++) {
			int number = (int) (Math.random() * 1000);
			int sex = (int) Math.round(Math.random());
			AddressList al = new AddressList(null, "联系人" + number + "号", sex, 0, 13 + "" + number + "" + number + "" + number, new Date(), "友好合作" + number, "广州市二沙岛" + number + "号别墅", "京东快递" );
			al.setUser(new User(1));
			addressListDao.save(al);
		}
	}

}
