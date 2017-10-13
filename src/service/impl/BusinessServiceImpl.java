package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import domain.User;
import exception.NicknameExitException;
import exception.UserExitException;
import utils.ServiceUtils;

//提供对WEB层的所有业务
public class BusinessServiceImpl {
	
	private UserDao dao = new UserDaoImpl();
	
	public void register(User user) throws UserExitException, NicknameExitException {
		
		boolean b = dao.find(user.getUsername());
		if(b) {
			throw new UserExitException();//注册用户已存在，则必须给WEB层抛出编译时异常，让WEB层必须处理异常，给用户友好提示
		}
		
		boolean b1 = dao.findNickname(user.getNickname());
		if(b1) {
			throw new NicknameExitException();//注册用户已存在，则必须给WEB层抛出编译时异常，让WEB层必须处理异常，给用户友好提示
		}
		
		user.setPassword(ServiceUtils.md5(user.getPassword()));//将密码用Md5编码加密
		dao.add(user);
		
	}
	
	public User login(String username,String password) {
		
		password = ServiceUtils.md5(password);
		return dao.find(username, password);
			
	}
	
}
