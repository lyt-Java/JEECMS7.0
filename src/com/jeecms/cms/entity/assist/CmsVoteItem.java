package com.jeecms.cms.entity.assist;

import java.util.Set;

import com.jeecms.cms.entity.assist.base.BaseCmsVoteItem;
import com.jeecms.common.hibernate3.PriorityInterface;

public class CmsVoteItem extends BaseCmsVoteItem implements PriorityInterface {
	private static final long serialVersionUID = 1L;

	/* 311版本调查只有选项 没有题目的情况
	public int getPercent() {
		Integer totalCount = getTopic().getTotalCount();
		if (totalCount != null && totalCount != 0) {
			return (getVoteCount() * 100) / totalCount;
		} else {
			return 0;
		}
	}
	*/
	//调查多题目情况下
	public int getPercent() {
		Integer totalCount = 0;
		Set<CmsVoteItem> subTopicVoteItems=getSubTopic().getVoteItems();
		for(CmsVoteItem vote:subTopicVoteItems){
			totalCount+=vote.getVoteCount();
		}
		if (totalCount != null && totalCount != 0) {
			return (getVoteCount() * 100) / totalCount;
		} else {
			return 0;
		}
	}

	public void init() {
		if (getPriority() == null) {
			setPriority(10);
		}
		if (getVoteCount() == null) {
			setVoteCount(0);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsVoteItem() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsVoteItem(java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsVoteItem(java.lang.Integer id,
			com.jeecms.cms.entity.assist.CmsVoteTopic topic,
			java.lang.String title, java.lang.Integer voteCount,
			java.lang.Integer priority) {

		super(id, topic, title, voteCount, priority);
	}

	/* [CONSTRUCTOR MARKER END] */

}