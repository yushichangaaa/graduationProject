package com.ysc.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.ysc.entity.Food;
import com.ysc.service.MasterService;
import com.ysc.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="用户controller",tags= {"用户Api"})
@RestController
public class MasterController {

	@Autowired
	private MasterService service;
	
	public static Logger log = LoggerFactory.getLogger(MasterController.class);
	
	
	@ApiOperation("姓名模糊查询")
	@RequestMapping(value = "/findByLikeName", method = { RequestMethod.POST })
	public Map<String, Object> findByLikeName(@RequestParam("name")String name) {
		Map<String,Object> map = new HashMap<String, Object>();
		
		try {
			List<Food> list = service.getFoodByLikeName(name);
			map.put("list", list);
			map.put("code", 20000);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 0);
		}
		return map;
	}
	@ApiOperation("upload")
	@RequestMapping(value = "/upload", method = { RequestMethod.POST },produces = { "application/json;charset=UTF-8" })
	public Result upload(@RequestParam("picture") MultipartFile picture, HttpServletRequest request) throws IOException {
		
		 //获取文件在服务器的储存位置
//        String path = request.getSession().getServletContext().getRealPath("/upload");
		String path = "E:\\毕设\\前端\\服务端\\static";
		String path2 = "E:\\毕设\\前端\\客户端\\static";
        File filePath = new File(path);
        System.out.println("文件的保存路径：" + path);
        if (!filePath.exists() && !filePath.isDirectory()) {
            System.out.println("目录不存在，创建目录:" + filePath);
            filePath.mkdir();
        }

        //获取原始文件名称(包含格式)
        String originalFileName = picture.getOriginalFilename();
        System.out.println("原始文件名称：" + originalFileName);

        //获取文件类型，以最后一个`.`为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        System.out.println("文件类型：" + type);
        //获取文件名称（不包含格式）
        String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));

        //设置文件新名称: 当前时间+文件名称（不包含格式）
        String fileName = name + "." + type;
        System.out.println("新文件名称：" + fileName);

        //在指定路径下创建一个文件
        File targetFile = new File(path, fileName);
        File targetFile2 = new File(path2, fileName);

        //将文件保存到服务器指定位置
        try {
            picture.transferTo(targetFile);
            picture.transferTo(targetFile2);
            System.out.println("上传成功");
            //将文件在服务器的存储路径返回
            return new Result(true,"static\\" + fileName);
        } catch (IOException e) {
            System.out.println("上传失败");
            e.printStackTrace();
            return new Result(false, "上传失败");
        }
	}
	
	@ApiOperation("add")
	@RequestMapping(value = "/add", method = { RequestMethod.POST })
	public Result addFood(Food food) {
		
		try {
		
			service.insert(food);
			
			log.info("添加成功");
			
			return new Result(true, food.toString());
		} catch (Exception e) {

			log.info("添加失败");
			
			e.printStackTrace();
            
			return new Result(false, e.toString());
		}
		
	}
	@ApiOperation("getFoods")
	@RequestMapping(value = "/getFoods", method = { RequestMethod.POST })
	public Map<String,Object> getFoods(@RequestParam(value="page", required =false) Integer page,
			@RequestParam(value="rows",required = false) Integer rows) {
		// 客户端服务端公用  getFoods
		Map<String,Object> map = new HashMap<String, Object>();
		PageInfo<Food> pageInfo;
		try {
			if(page!=null && rows!= null) {
				PageHelper.startPage(page, rows);
			}
			List<Food> list = service.getFood();
			pageInfo = new PageInfo<>(list);
			map.put("code", 20000);
			map.put("list", pageInfo.getList());
			map.put("total", pageInfo.getTotal());
			map.put("msg", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 0);
			map.put("msg", "失败");
		}
		return map;
	}
	@ApiOperation("delete")
	@PostMapping("/delete")
	public Result deleteFood(@RequestParam("id") Integer id) {

		try {
			service.delete(id);
			log.info("删除成功");
			return new Result(true, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("删除失败");
			return new Result(false, "失败");
		}
	}
	@ApiOperation("edit")
	@RequestMapping(value = "/edit",method = {RequestMethod.POST},produces = { "application/json;charset=UTF-8" })
	public Result editFood(@RequestParam("id")Integer id,
			@RequestParam("name")String name,@RequestParam("describe")String describe,
			@RequestParam("price")Double price) {
		//判断非空
		Food food = new Food();
		food.setId(id);
		food.setDescribe(describe);
		food.setName(name);
		food.setPrice(price);
		try {
			service.edit(food);
			log.info("修改成功");
			return new Result(true, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("修改失败");
			return new Result(false, "失败");
		}
	}
	
}
