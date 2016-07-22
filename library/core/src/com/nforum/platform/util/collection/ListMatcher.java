package com.nforum.platform.util.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ListMatcher {

	public List<String> match(List<String> matchingList, String key, int maxResults)
	{
		/**
		 * LOGIC:
		 * For each item in original list we shall see if it "contains" given key
		 * We shall collect all such candidates and keep them sorted based on their index
		 * This means a match found at index "2" gets priority above a match found at index "5"   
		 */
		
		Map<Integer,List<String>> autoSuggestionMap = new HashMap<Integer,List<String>>();
		int maxIndex=-1;
		String keyUpperCase = key.toUpperCase();
		
		//Go through all  international cities
		for(int i=0;i < matchingList.size();i++)
		{
			
			String prospectUpperCase = matchingList.get(i).toUpperCase();
			String prospect = matchingList.get(i);
			
			//City contains key
			if(prospectUpperCase.contains(keyUpperCase))
			{
				//Keep this match in a set mapped in index of key location
				int keyIndex = prospectUpperCase.indexOf(keyUpperCase);
				
				if(keyIndex> maxIndex)
					maxIndex = keyIndex;
				
				//See if list exists at index n
				List<String> list;
				if(!autoSuggestionMap.containsKey(keyIndex))
				{
					list = new ArrayList<String>();
					autoSuggestionMap.put(keyIndex, list);
				}
				else
					list = autoSuggestionMap.get(keyIndex);
				
				//Add to the list
				list.add(prospect); 
			}
		}

		//Go though all matched items, in increasing order of their index, lower index gets priority
		List<String> finalAutoSuggestList = new ArrayList<String>();
		boolean maxItemsCollected=false;
		for(int i=0;i<=maxIndex;i++)
		{
			List<String> matchesWithIndexN = autoSuggestionMap.get(i);
			if(matchesWithIndexN!=null)
			{
				for(String s:matchesWithIndexN)
				{
					finalAutoSuggestList.add(s);
					
					//If collected maxResults break loops
					if(finalAutoSuggestList.size()==maxResults)
					{
						maxItemsCollected=true;
						break;
					}
				}
			}
			if(maxItemsCollected)
				break;
		}
		
		return finalAutoSuggestList;
	}
}
