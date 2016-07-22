package com.nforum.platform.commons.property.change;

import com.nforum.platform.commons.property.PropertyChangeListener;

/**
 * Classes interested in property changes should regsiter themselves with instace of this
 * class
 * @author goyaln
 *
 */
public interface PropertyChangeNotifier {

	public void registerForChanges(PropertyChangeListener listener);
	public void addPropertyManager(ReloadableProperty properties);
	public void notifyListeners();
}
