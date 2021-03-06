/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.crawler.manager.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.crawler.manager.CrawlerFacable;
import com.ewcms.plugin.crawler.model.FilterBlock;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * 
 * @author wuzhijun
 *
 */
public class FilterBlockAction extends CrudBaseAction<FilterBlock, Long> {
	
	private static final long serialVersionUID = -7991291404500643405L;

	@Autowired
	private CrawlerFacable crawlerFac;
	
	private Long gatherId;
	private Long parentId;
	
	public Long getGatherId() {
		return gatherId;
	}

	public void setGatherId(Long gatherId) {
		this.gatherId = gatherId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<Long> getSelections() {
        return super.getOperatorPK();
    }
	
	public void setSelections(List<Long> selections) {
        super.setOperatorPK(selections);
    }
	
	public FilterBlock getFilterBlockVo(){
		return super.getVo();
	}
	
	public void setFilterBlockVo(FilterBlock filterBlockVo){
		super.setVo(filterBlockVo);
	}
	
	@Override
	protected Long getPK(FilterBlock vo) {
		return vo.getId();
	}

	@Override
	protected FilterBlock getOperator(Long pk) {
		return crawlerFac.findFilterBlock(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		crawlerFac.delFilterBlock(getGatherId(), pk);
	}

	@Override
	protected Long saveOperator(FilterBlock vo, boolean isUpdate) {
		return crawlerFac.addAndUpdFilterBlock(getGatherId(), getParentId(), vo);
	}

	@Override
	protected FilterBlock createEmptyVo() {
		return new FilterBlock();
	}
	
	public void up(){
		try{
			if (getGatherId() != null && getSelections() != null && getSelections().size() == 1){
				crawlerFac.upFilterBlock(getGatherId(), getSelections().get(0));
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void down(){
		try{
			if (getGatherId() != null && getSelections() != null && getSelections().size() == 1){
				crawlerFac.downFilterBlock(getGatherId(), getSelections().get(0));
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
}
