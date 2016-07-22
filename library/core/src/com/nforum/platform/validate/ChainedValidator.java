package com.nforum.platform.validate;

import java.util.List;

public class ChainedValidator implements Validator {

	List<Validator> validators;
	
	public ChainedValidator(List<Validator> validators) {
		super();
		this.validators = validators;
	}

	@Override
	public void validate(Object obj) {

		for(Validator validator:validators)
		{
			validator.validate(obj);
		}
	}

}
