package com.jeecms.cms.entity.assist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.jeecms.cms.entity.assist.base.BaseCmsScoreGroup;



public class CmsScoreGroup extends BaseCmsScoreGroup {
	private static final long serialVersionUID = 1L;
	
	public List<CmsScoreItem>getOrderItems(){
		Set<CmsScoreItem>items=super.getItems();
		Iterator<CmsScoreItem>it=items.iterator();
		List<CmsScoreItem>list=new ArrayList<CmsScoreItem>();
		while(it.hasNext()){
			list.add(it.next());
		}
		Collections.sort(list, new ItemComparator());
		return list;
	}
	
	private class ItemComparator implements Comparator<CmsScoreItem> {
		public int compare(CmsScoreItem o1, CmsScoreItem o2) {
			return o1.getPriority()-o2.getPriority();  
		}  
	}
	
	public Boolean getEnable(){
		return super.isEnable();
	}
	
	public Boolean getDef(){
		return super.isDef();
	}

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsScoreGroup () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsScoreGroup (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsScoreGroup (
		java.lang.Integer id,
		com.jeecms.core.entity.CmsSite site,
		java.lang.String name,
		boolean enable,
		boolean def) {

		super (
			id,
			site,
			name,
			enable,
			def);
	}

/*[CONSTRUCTOR MARKER END]*/


}