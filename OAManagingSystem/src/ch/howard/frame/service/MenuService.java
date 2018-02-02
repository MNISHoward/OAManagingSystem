package ch.howard.frame.service;

import java.util.Iterator;

import org.springframework.stereotype.Service;

import ch.howard.frame.model.Menu;

@Service
public class MenuService {

	public String queryMenuMethod(Integer mid, Iterable<Menu> menus) {
		Iterator<Menu> iterator = menus.iterator();
		String method = null;
		while(iterator.hasNext()) {
			Menu next = iterator.next();
			if(next.getId().intValue() == mid.intValue()) {
				method = next.getMethod();
			}
		}
		return method;
	}
	
}
