package com.pinyougou.sellergoods.service;

import java.util.List;
import java.util.Map;

import com.pinyougou.pojo.TbBrand;

import entity.BrandNameRepeatOrFirstCharNotFromatExection;
import entity.PageResult;

/**
 * 品牌接口
 * @author hejt
 *
 */
public interface BrandService {

	public List<TbBrand> findAll();
	/**
	 * 品牌分页
	 * @param pageName 当前页码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(int pageName, int pageSize);
	
	/**
	 * 品牌添加
	 * @param brand
	 * @throws BrandNameRepeatOrFirstCharNotFromatExection 
	 */
	public void add(TbBrand brand) throws BrandNameRepeatOrFirstCharNotFromatExection;
	
	/**
	 * 品牌编辑
	 * @throws BrandNameRepeatOrFirstCharNotFromatExection 
	 */
	public void updateBrand(TbBrand brand) throws BrandNameRepeatOrFirstCharNotFromatExection;
	
	/**
	 * 依据ID查找
	 */
	public TbBrand findOne(long id);
	
	/**
	 * 批量删除
	 */
	public void delete(Long[] ids);
	
	/**
	 * 模糊查询
	 * @param brand
	 * @param pageName
	 * @param pageSize
	 * @return
	 */
	public PageResult findPage(TbBrand brand, int pageName, int pageSize);
	
	/**
	 * select2 查询到的 id  text 数据
	 * @return  id，text
	 */
	public List<Map> selectOptionList();
}
