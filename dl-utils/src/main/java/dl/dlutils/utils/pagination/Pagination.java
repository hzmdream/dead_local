package dl.dlutils.utils.pagination;

import java.io.Serializable;

/**
 * 分页基础类
 * @author lijian
 *
 */
public class Pagination implements Serializable {
	
	public Pagination() {
		super();
	}

	private static final long serialVersionUID = -6776384314040963719L;

	public static final int DEFAULT_PAGE_SIZE = 20; // 每页显示的记录数
	
	protected int pageSize = DEFAULT_PAGE_SIZE;
	
	

    protected int totalCount = 0; // 记录的总数

    protected int pageCount = 0; // 总页数

    protected int startIndex = 0; // 分页开始记录数 从0开始
	
	public Pagination(int totalCount) {
		this(totalCount, 0, DEFAULT_PAGE_SIZE);
    }
    
    public Pagination(int totalCount, int startIndex, int pageSize) {
    	setPageSize(pageSize);
    	setStartIndex(startIndex);
    	setTotalCount(totalCount);
    }
    
    public Pagination(Pagination pagination) {
    	this(pagination.getTotalCount(), pagination.getStartIndex(), pagination.getPageSize());
    }
    
    /**
     * 获取当前页
     * @return
     */
    public int getCurrentPage() {
		if (this.startIndex < 0 || this.totalCount < 1)
		    return 0;
		return (this.startIndex / this.pageSize) + 1;
    }
    
    /**
     * 计算总页数
     */
    protected void calculatePageCount() {
		if (this.totalCount < 1)
		    this.pageCount = 0;
		else {
		    if (this.totalCount % this.pageSize > 0)
			this.pageCount = (this.totalCount / this.pageSize) + 1;
		    else {
			this.pageCount = this.totalCount / this.pageSize;
		    }
		}
    }

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		if (totalCount > 0) {
		    this.totalCount = totalCount;
		} else {
		    this.totalCount = 0;
		}
		calculatePageCount();
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	
	/**
	 * 获取本次查询最后一条数据的序号
	 * @return
	 */
	public int getEndIndex() {
		int endIndex = getStartIndex() + this.pageSize;
		if (this.totalCount > 0 && endIndex > this.totalCount) {
		    endIndex = this.totalCount;
		}
		return endIndex;
    }
    
    
}
