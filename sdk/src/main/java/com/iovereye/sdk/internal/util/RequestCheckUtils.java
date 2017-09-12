package com.iovereye.sdk.internal.util;

import java.io.IOException;

import com.iovereye.sdk.ApiRuleException;
import com.iovereye.sdk.FileItem;



public class RequestCheckUtils {
	
	public static final String ERROR_CODE_ARGUMENTS_MISS="40";	//Missing Required Arguments
	
	public static final String ERROR_CODE_ARGUMENTS_INVALID="41";//Invalid Arguments
	
	public static void checkNotEmpty(Object value,String fieldName)throws ApiRuleException{
		if(value==null){
			throw new ApiRuleException(ERROR_CODE_ARGUMENTS_MISS,"client-error:Missing Required Arguments:"+fieldName+".");
		}
		if(value instanceof String){
			if(((String) value).trim().length()==0){
				throw new ApiRuleException(ERROR_CODE_ARGUMENTS_MISS,"client-error:Missing Required Arguments:"+fieldName+".");
			}
		}
	}
	
	public static void checkMaxLength(String value,int maxLength,String fieldName)throws ApiRuleException{		
		if(value!=null){
			if(value.length()>maxLength){
				throw new ApiRuleException(ERROR_CODE_ARGUMENTS_INVALID,"client-error:Invalid Arguments:the length of "+fieldName+" can not be larger than "+maxLength+".");
			}
		}
	}
	
	public static void checkMaxLength(FileItem fileItem,int maxLength,String fieldName)throws ApiRuleException{	
		try {
			if(fileItem!=null && fileItem.getContent()!=null){
				
				if(fileItem.getContent().length>maxLength){
					throw new ApiRuleException(ERROR_CODE_ARGUMENTS_INVALID,"client-error:Invalid Arguments:the length of "+fieldName+" can not be larger than "+maxLength+".");
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void checkMaxListSize(String value,int maxSize,String fieldName)throws ApiRuleException{		
		if(value!=null){
			String[] list=value.split(",");
			if(list!=null&&list.length>maxSize){
				throw new ApiRuleException(ERROR_CODE_ARGUMENTS_INVALID,"client-error:Invalid Arguments:the listsize(the string split by \",\") of "+fieldName+" must be less than "+maxSize+".");
			}
		}
	}
	
	public static void checkMaxValue(Long value,long maxValue,String fieldName)throws ApiRuleException{	
		if(value!=null){
			if(value>maxValue){
				throw new ApiRuleException(ERROR_CODE_ARGUMENTS_INVALID,"client-error:Invalid Arguments:the value of "+fieldName+" can not be larger than "+maxValue+".");
			}
		}
	}
	
	public static void checkMinValue(Long value,long minValue,String fieldName)throws ApiRuleException{
		if(value!=null){
			if(value<minValue){
				throw new ApiRuleException(ERROR_CODE_ARGUMENTS_INVALID,"client-error:Invalid Arguments:the value of "+fieldName+" can not be less than "+minValue+".");
			}
		}
	}
	
	public static void checkEmailValue(String value, String fieldName) throws ApiRuleException {
		if (!RegExpValidator.isEmail(value)) {
			throw new ApiRuleException(ERROR_CODE_ARGUMENTS_INVALID,"client-error:Invalid Arguments:The value of "+fieldName+" is not Email format.");
		}
	}
	
	public static void checkNicknameValue(String value, String fieldName) throws ApiRuleException {
		if (!RegExpValidator.isNickName(value)) {
			throw new ApiRuleException(ERROR_CODE_ARGUMENTS_INVALID,"client-error:Invalid Arguments:The value of "+fieldName+" is not nickname format.");
		}
	}

}
