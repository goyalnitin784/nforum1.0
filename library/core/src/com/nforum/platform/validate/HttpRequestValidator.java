package com.nforum.platform.validate;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.nforum.platform.commons.utils.RequestUtils;
import com.nforum.platform.exception.ValidationException;

public class HttpRequestValidator implements Validator {

	private Map<String,List<Validator>> validators;
	boolean validateAllParameters;

	public HttpRequestValidator(Map<String, List<Validator>> validators) {
		super();
		this.validators = validators;
		this.validateAllParameters=false;
	}
	
	public HttpRequestValidator(Map<String, List<Validator>> validators,boolean validateAllParameters) {
		super();
		this.validators = validators;
		this.validateAllParameters = validateAllParameters;
	}

	@Override
	public void validate(Object objRequest) {

		if(objRequest instanceof HttpServletRequest)
		{
			HttpServletRequest httpRequest = (HttpServletRequest)objRequest;
			Map<String,String> parameters = RequestUtils.getParamaters(httpRequest);

			for(Entry<String,List<Validator>> fieldValditorsEntry:validators.entrySet())
			{
				String expectedParameter = fieldValditorsEntry.getKey();
				if(parameters.get(expectedParameter) == null){
					if(validateAllParameters){
						throw new ValidationException("expectedParameter is missing :" + expectedParameter);
					}else{
						continue;
					}
						
				}
				List<Validator> validatorsForThisField = validators.get(expectedParameter);
				for(Validator validator:validatorsForThisField)
				{
					validator.validate(parameters.get(expectedParameter));
				}
			}
		}
	}
}
