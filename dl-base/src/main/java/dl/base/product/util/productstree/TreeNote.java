package dl.base.product.util.productstree;

import java.io.Serializable;
import java.util.List;

/**
 * @author houzm
 * @time 2017年6月7日下午11:42:06
 * @description 
 */
public class TreeNote implements Serializable{

	private static final long serialVersionUID = 5666217628408787887L;
	
	private Long selfId;
	private Long parentId;
	private String title;
	private List<TreeNote> childrenNotes;
	
	public Long getSelfId() {
		return selfId;
	}
	public void setSelfId(Long selfId) {
		this.selfId = selfId;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<TreeNote> getChildrenNotes() {
		return childrenNotes;
	}
	public void setChildrenNotes(List<TreeNote> childrenNotes) {
		this.childrenNotes = childrenNotes;
	}
	
}
