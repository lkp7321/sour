package com.ylxx.fx.utils;
import java.lang.reflect.Field;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class BeanToMap<T> {
	static final Logger log = LoggerFactory.getLogger(BeanToMap.class);
    //反射对象的属性
    private static Field getFieldByClasss(String fieldName, Object object) {  
        Field field = null;  
        Class<?> clazz = object.getClass();  
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {  
            try {  
                field = clazz.getDeclaredField(fieldName);  
            } catch (Exception e) {  
            }  
        }  
        return field;  
    }  	
    
    //如果是bean是Map集合,和bean是实体对象
    public Map<String,Object> getMaps(T entity,String []keys) throws IllegalArgumentException, IllegalAccessException{
    	 Map<String, Object> mapValue = new HashMap<String, Object>();
    	 if(entity instanceof Map){
    		 for (int k = 0; k < keys.length; k++) {
	         	mapValue.put(keys[k], ((Map<String, Object>)entity).get(keys[k]));
    		}
    	 }else{
    		 for (int k = 0; k < keys.length; k++) {
	         	Field field = getFieldByClasss(keys[k],entity);
	         	if(field!=null) {
	         		field.setAccessible(true);
		         	mapValue.put(keys[k], field.get(entity));
	         	}else {
	         		mapValue.put(keys[k], null);
	         	}
	         	
    		 }
    	 }
    	 return mapValue;
    }
    //测试
    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "123");
		map.put("sex", "wom");
		map.put("id", "abc");
		String [] cols = {"name","id"};
		Map<String, Object> mapx = new BeanToMap().getMaps(map, cols);
		System.out.println(mapx.size());
	}
    
}

