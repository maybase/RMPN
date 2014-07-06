package com.pawineept.ptm.util;

public class NumberUtil {
	public static Long getLong(String num){
		Long result = null;
		try{
			result = new Long(num);
		}catch(Exception e){
			result = null;
		}
		return result;
	}
}
