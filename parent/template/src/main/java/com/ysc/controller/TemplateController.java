package com.ysc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ysc.entity.Comment;
import com.ysc.service.CommentService;
import com.ysc.util.Result;

@RestController
public class TemplateController {

	public static Logger log = LoggerFactory.getLogger(TemplateController.class);
	
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	CommentService commentService;
	
	
	private void aaa(List<Comment> A, Integer id, List<Comment> B) {
		
		for(int i=0;i<A.size();i++) {
			if(A.get(i).getParent() == id) {
				Comment pComment = A.remove(i);
				B.add(pComment);
				/*
				 * 这里需要递归处理 但是如何递归
				 * 1为父id 已经搜索到一个子id是2了   但是这个 2有没有子id呢
				 * 递归参数是   集合A   还有这个子id 的id 还需要另一个集合B
				 * 一开始我用的remove来 不断从A集合取 放到B集合   然后 思考发现不对
				 * 因为你逐渐递归的过程元素的id会逐渐变少
				 * 第一次循环如果跑到i=2  进入递归  如果第一轮的i=3符合条件
				 * 在逐层递归会把i=3这个元素变成i=1 或者i=0  你第一轮便取不到了
				 * 所以不能用remove 而且还容易报错啊   逐层递归如果这个集合元素都被remove
				 * 的差不多了     回到第一层循环的  i可能大于 你集合最大的长度   A.get(i)
				 * 会出错的,也可以remove   更好的方法就是 在出递归的时候 将 0赋值给i
				 * */
				aaa(A,pComment.getId(),B);
				i=-1;
			}
		}
	}
	
	// 这个是排序版   时间略长   数据量大的话   会非常费时间  所以 在commentController 里面 有一个非排序版查询
	@RequestMapping(value = "/getComments", method = { RequestMethod.POST, RequestMethod.GET }, produces = {
	"application/json;charset=UTF-8" })
	public Map<String, Object> getComments() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> datamap = new HashMap<String, Object>();
		
		try {
			System.out.println("yes");
			
			List<Comment> A = commentService.getComments();
			/*
			 *  评论逻辑是什么呢
			 *  因为 我要的是   一直都固定顺序  从小到大  从时间过去到现在  评论的显示
			 *  我新建一个list 名字为 A
			 *  就是查出一个list 名字是B  之后   取出第一个最小的id 加入是 1  放进A里面 遍历的时候第一个出现
			 *  然后     遍历B 查看他们的 父id  有没有 是 1的    假如查出来   id 分别是 2 3 4的 说明 2 3 4 评论了 1
			 *   2 3 4 要按照 id 从大到小 放在1的下面     但是有个前提  是 没有id 评论2 3 4 了  所以需要递归
			 *    递归来 查询 B集合  有没有 父 id 是 2 或者3 或者4 的 以此类推  来插入A集合
			 * 
			 * */
			List<Comment> B = new ArrayList<>();
			for(int i=0;i<A.size();i++) {
				Comment firstComment = A.remove(i);
				B.add(firstComment);
				Integer firstId = firstComment.getId();
				aaa(A,firstId,B); //此处递归
				// 错误是因为 i无法等于0   最后设置i = 0  之后 i++  i就会变成1了
				
				i = -1;
			}
			map.put("code", 20000);
			datamap.put("list", B);
			map.put("data", datamap);
			log.info("getComments成功了");
			
		} catch (Exception e) {
			map.put("code", 0);
			log.info("getComments失败了");
		}
		return map;
	}
	


	@RequestMapping("/send")
	public void sendMsg() {
		String url = "http://127.0.0.1:8766/send";
		restTemplate.getForEntity(url, null);
	}

	@RequestMapping(value = "/comment", method = { RequestMethod.POST, RequestMethod.GET }, produces = {
			"application/json;charset=UTF-8" })
	public Map<String, Object> comment(Comment comment) throws IOException {
		Map<String, Object> map = new HashMap<>();
		try {
		
			int i = commentService.add(comment);
			if(i == 1) {
				map.put("code", 20000);
			} else {
				map.put("code", 0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 0);
		}
		return map;
	}

	@RequestMapping(value = "/sendMsg", produces = { "application/json;charset=UTF-8" })
	public Map<String, Object> send() {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> datamap = new HashMap<>();
		map.put("code", 20000);
		datamap.put("validateCode", new Random().nextInt(9999));
		map.put("data", datamap);
		return map;
	}
	
	
	
	
}
