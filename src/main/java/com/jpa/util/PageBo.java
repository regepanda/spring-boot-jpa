package com.jpa.util;

import java.util.List;
import org.springframework.data.domain.Page;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageBo<T> {
	/**
	 * 当前页
	 */
	private int pageNow;
	
	/**
	 * 每页多少条数据
	 */
	private int pageSize;
	
	/**
	 * 一共多少条数据
	 */
	private int totalNum;
	
	/**
	 * 总页数
	 */
	private int totalPage;
	
	/**
	 * 是否有上一页
	 */
	private boolean hasPrev;
	
	/**
	 * 是否有下一页
	 */
	private boolean hasNext;
	
	/**
	 * 列表数据
	 */
	private List<T> dataList;
	
	/**
	 * 构造方法
	 * @param page
	 */
	public PageBo(Page<T> page) {
		this.pageNow = page.getNumber() + 1;
		this.pageSize = page.getSize();
		this.totalNum = ((Long) page.getTotalElements()).intValue();
		this.totalPage = page.getTotalPages();
		this.hasPrev = page.isFirst();
		this.hasNext = page.isLast();
		this.dataList = page.getContent();
	}
}
