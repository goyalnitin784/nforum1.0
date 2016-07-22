package com.nforum.platform.xslt;

/**
 * A transformer provider that can reconfigure itself
 * @author goyaln
 *
 */
public interface IReloadableTransformerProvider extends ITransformerProvider {

	public void reset();
}
