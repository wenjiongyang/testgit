package com.pinyougou.shop.controller;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;

import entity.PageResult;
import entity.Result;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/seller")
public class SellerController {

	@Reference
	private SellerService sellerService;

	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSeller> findAll(){			
		return sellerService.findAll();
	}


	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return sellerService.findPage(page, rows);
	}

	/**
	 * 增加
	 * @param seller
	 * @return
	 */
	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
	@RequestMapping("/add")
	public Result add(@RequestBody TbSeller seller){
		String password = bc.encode(seller.getPassword());
		seller.setPassword(password);
		try {
			sellerService.add(seller);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}

	/**
	 * 修改
	 * @param seller
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbSeller seller){
		try {
			sellerService.update(seller);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	

	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbSeller findOne(String id){
		id = getSellerId();
		return sellerService.findOne(id);		
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(String [] ids){
		try {
			sellerService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}

	/**
	 * 查询+分页
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbSeller seller, int page, int rows  ){
		return sellerService.findPage(seller, page, rows);		
	}


	@RequestMapping("/updatePwd")
	public Result updatePwd(@RequestBody TbSeller seller){
		
		if(!bc.matches(seller.getPassword(), findOne(getSellerId()).getPassword())){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordEncoder.encode(seller.getPassword());
		seller.setPassword(password);
		seller.setSellerId(getSellerId());
		try {
			sellerService.updatePwd(seller);
			return new Result(true, "修改密码成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改密码失败！");
		}
		}else{
			return new Result(false, "新密码不能与原密码相同！");
		}
	}

	@RequestMapping("/updateAll")
	public Result updateAll(@RequestBody TbSeller seller){
		try {
			sellerService.updatePwd(seller);
			return new Result(true, "修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败！");
		}
	}
	
	@RequestMapping("/findPwdBySellerId")
	public Result findPwdBySellerId(@RequestBody TbSeller seller){
		String name = getSellerId();
		String oldPassword = sellerService.findPwdBySellerId(name);
		if((bc.matches(seller.getPassword(), oldPassword))){
			return new Result(true, "原密码正确！");
		}else{
			return new Result(false, "原密码错误！");
		}

	}

	private String getSellerId(){
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		return name;
	}

}
