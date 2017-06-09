package dl.base.product.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import dl.common.entity.BaseDO;


/**
 * @author houzm
 * @time 2017年5月15日下午9:44:35
 * @description 商品类目表
 * 
 * CREATE TABLE `tb_item_cat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '类目ID',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父类目ID=0时，代表的是一级的类目',
  `name` varchar(50) DEFAULT NULL COMMENT '类目名称',
  `status` int(1) DEFAULT '1' COMMENT '状态。可选值:1(正常),2(删除)',
  `sort_order` int(4) DEFAULT NULL COMMENT '排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数',
  `is_parent` tinyint(1) DEFAULT '1' COMMENT '该类目是否为父类目，1为true，0为false',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `updated` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`,`status`) USING BTREE,
  KEY `sort_order` (`sort_order`)
) ENGINE=InnoDB AUTO_INCREMENT=1183 DEFAULT CHARSET=utf8 COMMENT='商品类目';

 */
@Entity
@Table(name="BASE_PRODUCT_TYPE")
public class ProductTypeDO extends BaseDO{
	
	private static final long serialVersionUID = -7784169768023186479L;
	
	@Column(name="parent_id")
	private Long parentId;//父类目ID=0时，代表的是一级的类目
	/**
	 * @Column length属性表示字段的长度，当字段的类型为varchar时，该属性才有效，默认为255个字符
	 */
	@Column(name="name",length=50)
	private String name;//类目名称
	@Column(name="status")
	private Integer status;//状态。可选值:1(正常),2(删除)
	@Column(name="sort_order")
	private Integer sortOrder;//排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
	@Column(name="is_parent")
	private Boolean isParent;//该类目是否为父类目，1为true，0为false
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	
	
}
