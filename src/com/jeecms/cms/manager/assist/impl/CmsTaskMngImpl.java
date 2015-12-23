package com.jeecms.cms.manager.assist.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.common.hibernate3.Updater;
import com.jeecms.common.page.Pagination;
import com.jeecms.cms.dao.assist.CmsTaskDao;
import com.jeecms.cms.entity.assist.CmsTask;
import com.jeecms.cms.manager.assist.CmsTaskMng;

@Service
@Transactional
public class CmsTaskMngImpl implements CmsTaskMng {
	@Transactional(readOnly = true)
	public Pagination getPage(Integer siteId,int pageNo, int pageSize) {
		Pagination page = dao.getPage(siteId,pageNo, pageSize);
		return page;
	}
	
	@Transactional(readOnly = true)
	public List<CmsTask> getList(){
		return dao.getList();
	}

	@Transactional(readOnly = true)
	public CmsTask findById(Integer id) {
		CmsTask entity = dao.findById(id);
		return entity;
	}

	public CmsTask save(CmsTask bean) {
		dao.save(bean);
		return bean;
	}

	public CmsTask update(CmsTask bean, Map<String, String> attr) {
		Updater<CmsTask> updater = new Updater<CmsTask>(bean);
		updater.include("intervalUnit");
		updater.include("cronExpression");
		updater.include("dayOfMonth");
		updater.include("dayOfWeek");
		updater.include("hour");
		updater.include("minute");
		updater.include("repeatCount");
		updater.include("intervalHour");
		updater.include("intervalMinute");
		bean = dao.updateByUpdater(updater);
		// 更新属性表
		if (attr != null) {
			Map<String, String> attrOrig = bean.getAttr();
			attrOrig.clear();
			attrOrig.putAll(attr);
		}
		return bean;
	}

	public CmsTask deleteById(Integer id) {
		CmsTask bean = dao.deleteById(id);
		return bean;
	}

	public CmsTask[] deleteByIds(Integer[] ids) {
		CmsTask[] beans = new CmsTask[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public String getCronExpressionFromDB(Integer taskId) {
		CmsTask task = findById(taskId);
		if (task.getExecycle().equals(CmsTask.EXECYCLE_CRON)) {
			return task.getCronExpression();
		} else {
			Integer execycle = task.getIntervalUnit();
			String excep="" ;
			if (execycle.equals(CmsTask.EXECYCLE_MONTH)) {
				excep="0  "+task.getMinute() +" "+task.getHour()+" "+ task.getDayOfMonth() +" * ?";
			} else if (execycle.equals(CmsTask.EXECYCLE_WEEK)) {
				excep="0  "+task.getMinute() +" "+task.getHour()+" "+" ? " +" * "+task.getDayOfWeek();
			} else if (execycle.equals(CmsTask.EXECYCLE_DAY)) {
				excep="0  "+task.getMinute() +" "+task.getHour()+" "+" * * ?";
			} else if (execycle.equals(CmsTask.EXECYCLE_HOUR)) {
				excep="0 0 */"+task.getIntervalHour()+" * * ?";
			} else if (execycle.equals(CmsTask.EXECYCLE_MINUTE)) {
				excep="0  */"+task.getIntervalMinute() +" * * * ?";
			}
			return excep;
		}
	}

	private CmsTaskDao dao;

	@Autowired
	public void setDao(CmsTaskDao dao) {
		this.dao = dao;
	}
}