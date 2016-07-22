package com.nforum.platform.validate;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.nforum.platform.exception.ValidationException;

public class ReflectiveFieldValidator implements Validator {

	@SuppressWarnings("rawtypes")
	private Class klass;
	private Map<String,List<Validator>> validators;


	public ReflectiveFieldValidator(@SuppressWarnings("rawtypes") Class klass,
			Map<String, List<Validator>> validators) {
		super();
		this.klass = klass;
		this.validators = validators;
	}


	@SuppressWarnings("unchecked")
	@Override
	public void validate(Object target)
	{
		for(Entry<String,List<Validator>> entry: validators.entrySet())
		{
			String fieldName = entry.getKey();
			List<Validator> validators  = entry.getValue();
			Object objValue;
			
			try {
				
				objValue =	klass.getDeclaredMethod("get" + fieldName).invoke(target);
				for(Validator validator:validators)
					validator.validate(objValue);	
				
			} catch (IllegalArgumentException e) {
				throw new ValidationException("illegal argument reflection error",e);
			} catch (SecurityException e) {
				throw new ValidationException("secutiry reflection error",e);
			} catch (IllegalAccessException e) {
				throw new ValidationException("method get" + fieldName +"() is not public",e);
			} catch (NoSuchMethodException e) {
				throw new ValidationException("object does not have field by name " + fieldName,e);
			} catch (InvocationTargetException e) {
				throw new ValidationException("object does not have method by name get" + fieldName +"()",e);
			}catch(ValidationException e){
				throw new ValidationException("validation on field - " + fieldName + " not succeeded",e);
			}
		}
	}
}
