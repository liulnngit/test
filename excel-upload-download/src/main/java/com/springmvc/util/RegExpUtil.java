package com.springmvc.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * @author jesse
 *
 */
public class RegExpUtil {
	
	public static final String CHINESE_STRING="[\u4e00-\u9fa5]";
	
	public static final String NUM_STRING="[0-9.]";
	
	public static Matcher MatchChinese(String value){
		Pattern pattern = Pattern.compile(CHINESE_STRING);
		Matcher mat = pattern.matcher(value);
		return mat;
	}
	
	public static Matcher MatchNum(String value){
		Pattern pattern = Pattern.compile(NUM_STRING);
		Matcher mat = pattern.matcher(value);
		return mat;
	}
	
	/**
	 * 查找匹配中文字符串，并剔除数字和特殊字符
	 * @param value
	 * @return
	 */
	public static String FindAndReplace(String value){
		Matcher mat = RegExpUtil.MatchChinese(value);
		if(mat.find()){
			Matcher m = RegExpUtil.MatchNum(value);
			return m.replaceAll("").trim();
		}
		return null;
	}
	
}
