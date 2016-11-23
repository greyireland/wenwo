package com.finalx;

import com.finalx.dao.QuestionDAO;
import com.finalx.dao.UserDAO;
import com.finalx.model.Question;
import com.finalx.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTest {

	@Autowired
	UserDAO userDao;

	@Autowired
	QuestionDAO questionDAO;
	@Test
	public void initDataBase() {
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
			User user = new User();
			user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
			user.setName(String.format("小白%d号", i));
			user.setPassword("123456");
			user.setSalt("salt1");
			userDao.addUser(user);
		}
		//Assert.assertEquals("xxx", userDao.selectById(11).getPassword());
		//userDao.deleteById(1);

	}

	@Test
	public void testgetRand() {
		Random random = new Random();
		List<Map<String,String>> list=getRandomNameAndPwd();
		int len=list.size();
		System.out.println(len);
		for (int i = 0; i < 3; i++) {
			System.out.println(list.get(random.nextInt(len)).get("name").toString());
			System.out.println(list.get(random.nextInt(len)).get("pwd").toString());
			//System.out.println(list.get(random.nextInt(len)).entrySet().toString());
		}
	}
	private List<Map<String,String>> getRandomNameAndPwd() {
		try {
			InputStream inputStream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("name_pwd.txt");
			InputStreamReader reader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String lineText;
			List<Map<String, String>> list = new ArrayList<>();
			Map<String, String> map = new HashMap<>();
			while ((lineText = bufferedReader.readLine()) != null) {
				String[] nameAndPwd = lineText.trim().split(" ");
				map.put("name",nameAndPwd[0]);
				map.put("pwd",nameAndPwd[1]);
				list.add(map);
			}
			reader.close();
			return list;
		} catch (Exception e) {
			//logger.error("读取文件失败" + e.getMessage());
		}
		return null;
	}
	@Test
	public void testQuestion() {
		//System.out.println(questionDAO.selectLatestQuestions(1,0,10));
		/*for (int i = 4; i < 10; i++) {
			Question question = new Question();
			question.setCommentCount(2);
			Date date=new Date();
			question.setContent("haha");
			question.setCreatedDate(date);
			question.setTitle(String.format("question %d：why are you so diao?",i));
			question.setUserId(1);
			questionDAO.addQuestion(question);
		}
		for (int i = 4; i < 5; i++) {
			Question question2 = new Question();
			question2.setCommentCount(3);
			Date date=new Date();
			question2.setContent("The answer is you are bbb.");
			question2.setCreatedDate(date);
			question2.setTitle(String.format("question %d：why are you so diao?",i));
			question2.setUserId(2);
			questionDAO.addQuestion(question2);
		}
		System.out.println(questionDAO.selectLatestQuestions(1,0,10).get(0).toString());*/
	}
	@Test
	public void testSelectQuestion() {

	}
	@Test
	public void contextLoads() {
	}

}
