package com.itface.star.system.uploadify.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value="/uploadify")
public class UploadifyController {


	@RequestMapping(value="/upload")
	public @ResponseBody String upload(HttpServletRequest request,HttpServletResponse response,@RequestParam MultipartFile[] uploadify) throws IOException {
		Subject currentUser = SecurityUtils.getSubject();
		String basePath = request.getSession().getServletContext().getRealPath("/");
		String savePath = basePath+"upload"+File.separator+"test";
		File dir = new File(savePath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		if(uploadify!=null){
			for(int i=0;i<uploadify.length;i++){
				MultipartFile mf = uploadify[i];
				String fileName = mf.getOriginalFilename();
				File uploadFile = new File(savePath +File.separator+ fileName);
				int count = 1;
				while(uploadFile.exists()){
					uploadFile = new File(savePath +File.separator+(count++)+"_"+fileName);
				}
				//uploadFile.createNewFile();
				FileCopyUtils.copy(mf.getBytes(), uploadFile);
			}
		}
		return "上传成功";
	}
}
