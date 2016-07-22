package com.nforum.platform.mvc;

import org.springframework.ui.Model;

public interface ModelPopulator<T> {

	void populateModel(Model model, T object);
}
