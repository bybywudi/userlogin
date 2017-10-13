package utils;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

public class WebUtils {
	public static <T> T request2Bean(HttpServletRequest request,Class<T> beanClass) {//泛型
		
		
		try {
			//1、创建要封装数据的bean
			T bean = beanClass.newInstance();
			
			//2、数据整到上边的Bean中
			Enumeration e = request.getParameterNames();
			while(e.hasMoreElements()) {
				String name = (String) e.nextElement();
				String value = request.getParameter(name);
				
				BeanUtils.setProperty(bean, name, value);
			}
			
			return bean;
			
		} catch (Exception e) {
			throw new RuntimeException();
		} 
		
		
	}
	
	public static void copyBean(Object src,Object dest) {
			//注册一个日期转换器
			ConvertUtils.register(new Converter() {
			public Object convert(Class type,Object value) {
				
				if(value==null) {
					return null;
				}
				
				String str = (String) value;
				
				if(str.trim().equals("")) {
					return null;
				}
				
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					return df.parse(str);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				
			}
			
		}, Date.class);
			
			try {
				BeanUtils.copyProperties(dest,src);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			} 
	}
	
	//生成全球唯一ID
	public static String generateID() {
		
		
		return UUID.randomUUID().toString();
		
	}
}
