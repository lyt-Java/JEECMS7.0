package com.jeecms.common.util;
/**
 * @author Tom
 */
public class ArrayUtils {
	public static Integer[] convertStrArrayToInt(String[]strArray){
		if(strArray!=null&&strArray.length>0){
			Integer array[]=new Integer[strArray.length];
			for(int i=0;i<strArray.length;i++){  
			    array[i]=Integer.parseInt(strArray[i]);
			}
			return array;
		}else{
			return null;
		}
	}
}
