package com.nforum.platform.xslt;

import javax.xml.transform.Transformer;
/**
 * A simple interface that provides transformer instance
 * @author goyaln
 *
 */
public interface ITransformerProvider {

	public Transformer getTransformer(String xslFileName);
}
