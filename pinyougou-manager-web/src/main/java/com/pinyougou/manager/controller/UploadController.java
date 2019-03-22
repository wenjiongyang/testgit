package com.pinyougou.manager.controller;

import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import utils.DataFormatUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@RestController
public class UploadController {
	
	@RequestMapping("/upload")
	public Result upload(MultipartFile file,MultipartHttpServletRequest request){
		Result rr = null;
		String url = null;
		//用户上传头像路在服务器端的路径
			//上传头像
			//验证
			if (!file.isEmpty()) {
				String contentType = file.getContentType();
				if (!"image/png".equals(contentType) && !"image/jpeg".equals(contentType)
						&& !"image/bmp".equals(contentType)) {
					rr = new Result(false, "不支持" + contentType + "类型文件！");
					return rr;
				}
				long size = file.getSize();
				if (size > 5 * 1024 * 1024) {
					rr = new Result(false, "上传的头像文件不允许超过" + 5 + "MB！");
					return rr;
				}
				//保存头像的文件夹，所有用户头像都在这个文件夹中
				String avatarDirPath = request.getSession().getServletContext().getRealPath("/upload/contentimage");
				File avatarDir = new File(avatarDirPath);
				//头像文件名，每个用户的头像文件都应该不相同
				Date date = new Date();
				//获得商家ID
				String name = SecurityContextHolder.getContext().getAuthentication().getName();
				//获取文件后缀名
				String string = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
				String filename = getDateString(date)+ name + string;
				//目标文件，即在服务端保存的用户头像文件
				File dest = new File(avatarDir, filename);
				DataFormatUtil dataFormatUtil = new DataFormatUtil();

				//将用户上传的头像数据保存到文件
				try {
					file.transferTo(dest);
					url = "../upload/contentimage/" + filename;
					rr = new Result(true, url);
					return rr;
				} catch (Exception e) {
					e.printStackTrace();
					rr = new Result(false, "上传失败");
					return rr;
				} 
			} 
		return new Result(false, "文件不存在！");
	}
	
	private final String pattern = "yyyyMMddHHmmss";
	private final SimpleDateFormat sdf = new SimpleDateFormat(pattern,Locale.CHINA);
	
	private String getDateString(Date date){
		return sdf.format(date);
	}

}
