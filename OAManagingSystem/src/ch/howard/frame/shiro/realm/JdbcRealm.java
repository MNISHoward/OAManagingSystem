package ch.howard.frame.shiro.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
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

import ch.howard.frame.util.Util;

public class JdbcRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		Object principal = pc.getPrimaryPrincipal();
		Set<String> roles = new HashSet<String>();
		roles.add("user");
		if("admin".equals(principal)) {
			roles.add("admin");
		}
		
		return new SimpleAuthorizationInfo(roles);
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) 
			throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken)token;
		String username = upToken.getUsername();
		if(Util.isEmpty(username)) {
    		throw new UnknownAccountException();
    	}
		String principal = upToken.getUsername();
		String credentials;
		if(principal.equals("admin")) {
			credentials = "cf2f84b6b83710fd7442ede509c95012";
		}else {
			credentials = "e4216961e03f91a7632f9fee1444d3bb";
		}
		String realmName = getName();
		ByteSource credentialsSalt = ByteSource.Util.bytes(upToken.getUsername());
		
		return new SimpleAuthenticationInfo(principal, credentials, credentialsSalt,realmName);
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
