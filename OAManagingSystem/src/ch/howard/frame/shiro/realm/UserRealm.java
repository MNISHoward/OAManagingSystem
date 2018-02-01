package ch.howard.frame.shiro.realm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import ch.howard.frame.dao.UserDAO;
import ch.howard.frame.model.User;
import ch.howard.frame.util.Util;
import ch.howard.rbac.model.Role;

public class UserRealm extends AuthorizingRealm {
	
	@Autowired
	private UserDAO userDao;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		System.out.println(111);
		Object principal = pc.getPrimaryPrincipal();
		User u = userDao.findByUsername(principal.toString());
		Set<String> rolesSet = new HashSet<String>();
		Set<Role> roles = u.getRoles();
		Iterator<Role> iterator = roles.iterator();
		while(iterator.hasNext()) {
			Role role = iterator.next();
			rolesSet.add(role.getName());
		}
		System.out.println(rolesSet);
		return new SimpleAuthorizationInfo(rolesSet);
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) 
			throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken)token;
		String username = upToken.getUsername();
		if(Util.isEmpty(username)) {
    		throw new UnknownAccountException();
    	}
		User u = userDao.findByUsername(username);
		if(u.getState() == 1) {
			throw new LockedAccountException();
		}
		String principal = upToken.getUsername();
		String realmName = getName();
		ByteSource credentialsSalt = ByteSource.Util.bytes(upToken.getUsername());
		
		return new SimpleAuthenticationInfo(username, u.getPassword(), credentialsSalt,realmName);
	}
	
	public static void main(String args[]) {
		String algorithmName = "MD5";
		Object source = "123456"; 
		ByteSource salt = ByteSource.Util.bytes("user");
		int hashIterations = 10;
		
		
		SimpleHash result = new SimpleHash(algorithmName, source, salt, hashIterations);
		System.out.println(result); 
	}
	
	
}
