package com.pe.pgn.clubpgn.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CollectionUtil {

	public static List<Map<String, Object>> setFirstMapFromList(List<Map<String, Object>> list, String field, String value){
		
		List<Map<String, Object>> collection = new ArrayList<Map<String,Object>>();
		for(Iterator<Map<String, Object>> iter = list.iterator(); iter.hasNext(); ){
			
			Map<String, Object> map = iter.next();
			String deField = map.get(field).toString();
			if(deField.equalsIgnoreCase(value)){
				collection.add(map);
				collection.addAll(list);
				
				list.clear();
				list.addAll(collection);
				break;
			}
		}	
		return list;
	}
}
