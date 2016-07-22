package com.nforum.platform.validate;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nforum.platform.commons.utils.RequestUtils;

public class HttpRequestBifercatingValidator implements Validator {

	Map<String,Validator> valueValidatorMap;
	String validatingType;

	@Override
	public void validate(Object objRequest) {
		if(objRequest instanceof HttpServletRequest)
		{
			HttpServletRequest httpRequest = (HttpServletRequest)objRequest;
			Map<String,String> parameters = RequestUtils.getParamaters(httpRequest);
			if(parameters.containsKey(validatingType)){
				Validator validator = valueValidatorMap.get(parameters.get(validatingType));
				if(validator!=null){
					validator.validate(objRequest);
					return;
				}
			}
			//Will break if request is not having type..thus commenting
			//throw new ValidationException("Un able to bifercate request for validating type : " + validatingType);
		}
	}

	public Map<String, Validator> getValueValidatorMap() {
		return valueValidatorMap;
	}

	public void setValueValidatorMap(Map<String, Validator> valueValidatorMap) {
		this.valueValidatorMap = valueValidatorMap;
	}

	public String getValidatingType() {
		return validatingType;
	}

	public void setValidatingType(String validatingType) {
		this.validatingType = validatingType;
	}



}
