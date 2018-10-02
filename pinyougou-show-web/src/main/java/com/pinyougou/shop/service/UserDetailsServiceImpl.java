package com.pinyougou.shop.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
/**
 * 认证类
 * @author tedu
 *
 */
public class UserDetailsServiceImpl implements UserDetailsService {

	//以配置的方式注入，不要写注解，  因为SellerService在别的包里
	private SellerService sellerService;
	//必须要有set
	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<GrantedAuthority> grantedAuthority = new ArrayList<GrantedAuthority>();
		grantedAuthority.add(new SimpleGrantedAuthority("ROLE_SELLER"));
		//username即为ID
		TbSeller seller = sellerService.findOne(username);
		
		if(seller!=null){
			if(seller.getStatus().equals("1")){
				return new User(username, seller.getPassword(), grantedAuthority);
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
}
