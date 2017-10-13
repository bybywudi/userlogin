package dao.impl;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import dao.UserDao;

import java.text.SimpleDateFormat;
import java.util.*;

import domain.User;
import utils.XmlUtils;

public class UserDaoImpl implements UserDao {
	@Override
	public void add(User user) {
		//user对象的数据写到xml里
		try {
			Document document = XmlUtils.getDocument();
			Element root = document.getRootElement();//获取文档的根节点
			
			Element user_tag = root.addElement("user");
			user_tag.setAttributeValue("id", user.getId());
			user_tag.setAttributeValue("username", user.getUsername());
			user_tag.setAttributeValue("password", user.getPassword());
			user_tag.setAttributeValue("email", user.getEmail());
			user_tag.setAttributeValue("nickname", user.getNickname());
			user_tag.setAttributeValue("birthday", user.getBirthday()==null?"":user.getBirthday().toString());
			
			XmlUtils.write2Xml(document);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public User find(String username,String password) {
		
		try {
			Document document = XmlUtils.getDocument();
			
			//System.out.println(document.getPath()+"documentpath");
			
			Element e = (Element) document.selectSingleNode("//user[@username='"+username+"'and @password='"+password+"']");
			if(e==null) {
				return null;
			}
			User user = new User();//数据封装到Bean
			String date = e.attributeValue("birthday");
			if(date==null || date.equals("")) {
			user.setBirthday(null);
			}else {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				user.setBirthday(df.parse(date));
			}
			
			user.setEmail(e.attributeValue("email"));
			user.setId(e.attributeValue("id"));
			user.setNickname(e.attributeValue("nickname"));
			user.setUsername(e.attributeValue("username"));
			user.setPassword(e.attributeValue("password"));
			
			return user;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		
		
	}
	
	@Override
	public boolean find(String username) {
		try{
		Document document = XmlUtils.getDocument();
		Element e = (Element) document.selectSingleNode("//user[@username='"+username+"']");
		
		if(e==null) {
			return false;
		}else {
			return true;
			}
		
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public boolean findNickname(String nickname) {
		try{
		Document document = XmlUtils.getDocument();
		Element e = (Element) document.selectSingleNode("//user[@nickname='"+nickname+"']");
		
		if(e==null) {
			return false;
		}else {
			return true;
			}
		
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		
	}
}

