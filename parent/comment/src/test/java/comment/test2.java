package comment;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ysc.entity.Comment;
import com.ysc.mapper.CommentMapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class test2 {
	
	private static Logger logger = LoggerFactory.getLogger(test2.class);
	@Autowired
	CommentMapper commentMapper;
	
	@Test
	public void test() {
		Comment comment = new Comment();
		comment.setName("1");
		comment.setText("2TEXT");
		int insert = commentMapper.insert(comment);
		System.out.println(insert);
	}

}
