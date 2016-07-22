package com.nforum.platform.util.cache;

public class DataCache<T> {

	private T data=null;
	private DataFetcher<T> fetcher;
	
	public DataCache(DataFetcher<T> fetcher)
	{
		this.fetcher = fetcher;
	}
	
	public T get()
	{
		if(data==null){
			synchronized (this) {
				if(data==null){
					data = fetcher.fetch();
				}
			}
		}
		return data;
	}
	
	public void reset()
	{
		synchronized (this) {
			data = fetcher.fetch();
		}
	}
}
