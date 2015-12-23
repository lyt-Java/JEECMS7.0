package com.jeecms.cms.manager.assist;

import java.util.List;

import com.jeecms.cms.entity.assist.CmsAcquisitionHistory;
import com.jeecms.common.page.Pagination;

public interface CmsAcquisitionHistoryMng {
	public List<CmsAcquisitionHistory> getList(Integer siteId, Integer acquId);

	public Pagination getPage(Integer siteId, Integer acquId, Integer pageNo,
			Integer pageSize);

	public CmsAcquisitionHistory findById(Integer id);

	public CmsAcquisitionHistory save(CmsAcquisitionHistory bean);

	public CmsAcquisitionHistory update(CmsAcquisitionHistory bean);

	public CmsAcquisitionHistory deleteById(Integer id);

	public CmsAcquisitionHistory[] deleteByIds(Integer[] ids);
	
	public void deleteByAcquisition(Integer acquId);
	
	public Boolean checkExistByProperties(Boolean title, String value);
}