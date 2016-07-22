package com.nforum.platform.commons.property.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nforum.platform.commons.property.change.PropertyChangeNotifier;

@Controller
public class PropertyController {

	@Autowired PropertyChangeNotifier changeNotifier;
	
	@ResponseBody @RequestMapping(value = "touch", method = RequestMethod.GET)
	public String touch()
	{
		changeNotifier.notifyListeners();
		return "Done";
	}
}
