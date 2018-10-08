package com.pinyougou.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbBrandExample;
import com.pinyougou.pojo.TbBrandExample.Criteria;
import com.pinyougou.sellergoods.service.BrandService;

import entity.BrandNameRepeatOrFirstCharNotFromatExection;
import entity.PageResult;

@Service
public class BrandServiceImpl implements BrandService {

	@Autowired //本地调用
	private TbBrandMapper brandMapper;
	
	@Override
	public List<TbBrand> findAll() {
		return brandMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		//调用myBatise中的page插件
		PageHelper.startPage(pageNum, pageSize);
		Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(null);
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public void add(TbBrand brand) throws BrandNameRepeatOrFirstCharNotFromatExection {
		boolean flag = charNotFromate(brand);
		if(flag){
			brandMapper.insert(brand);
		}
	}
	private boolean charNotFromate(TbBrand brand) throws BrandNameRepeatOrFirstCharNotFromatExection{
		List<TbBrand> list = findAll();
		String regex = "^[a-zA-Z]{1}$";
		boolean flag = true;
		for (TbBrand tbBrand : list) {
			if(tbBrand.getName().equals(brand.getName())||brand.getName().length()<=0||(!brand.getFirstChar().matches(regex))){
				flag = false;
				throw new BrandNameRepeatOrFirstCharNotFromatExection("品牌名已存在或者首字母不符合格式!");
				/*System.out.println("品牌名重复或者首字母不符合格式异常，已处理");*/
			}
		}
		return flag;
	}
	@Override
	public void updateBrand(TbBrand brand) throws BrandNameRepeatOrFirstCharNotFromatExection {
		boolean flag = charNotFromate(brand);
		if(flag){
			brandMapper.updateByPrimaryKey(brand);
		}
		
	}

	@Override
	public TbBrand findOne(long id) {
		return brandMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			brandMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageResult findPage(TbBrand brand, int pageNum, int pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		TbBrandExample example =new TbBrandExample();
		Criteria criteria = example.createCriteria();
		
		if(brand!=null){
			if(brand.getName()!=null&&brand.getName().length()>0){
				criteria.andNameLike("%"+brand.getName()+"%");
			}
			if(brand.getFirstChar()!=null&&brand.getFirstChar().length()>0){
				criteria.andFirstCharLike("%"+brand.getFirstChar()+"%");
			}
		}
		Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(example);
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public List<Map> selectOptionList() {
		return brandMapper.selectOptionList();
	}
}
