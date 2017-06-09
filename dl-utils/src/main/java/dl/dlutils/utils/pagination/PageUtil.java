package dl.dlutils.utils.pagination;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 * @author h
 *
 */
public class PageUtil<T> implements Serializable {
	
	private static final long serialVersionUID = 1368060527204258745L;

	private List<T> data;
	
	/**
	 * 总条数
	 */
	private int totalCount;
	
	/**
	 * 总页数
	 */
	private int pageCount;
	
	/**
	 * 每页大小
	 */
	private int pageSize;
	
	/**
	 * 当前第几页
	 */
	private int currPage;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	
	
	
}
