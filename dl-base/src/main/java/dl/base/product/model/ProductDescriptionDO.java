package dl.base.product.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import dl.common.entity.BaseDO;
/**
 * 
 * @author houzm
 * @time 2017年5月15日下午10:17:51
 * @description
 * 
 * DROP TABLE IF EXISTS `tb_item_desc`;
	CREATE TABLE `tb_item_desc` (
	  `item_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
	  `item_desc` text COMMENT '商品描述',
	  `created` datetime DEFAULT NULL COMMENT '创建时间',
	  `updated` datetime DEFAULT NULL COMMENT '更新时间',
	  KEY `item_id` (`item_id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品描述表';

 */
@Entity
@Table(name="BASE_PRODUCT_DESCRIPTION")
public class ProductDescriptionDO extends BaseDO{

	private static final long serialVersionUID = 1654851386735192992L;
	@Column(name="product_id")
	private Long productId;//商品id
	@Lob
	@Column(name="product_desc")
	private String productDesc;//商品描述
	
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	
}
