package dl.base.product.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import dl.common.entity.BaseDO;

/**
 * @author houzm
 * @time 2017年5月15日下午10:05:57
 * @description 
 * 
 * DROP TABLE IF EXISTS `tb_item`;
	CREATE TABLE `tb_item` (
	  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '商品id，同时也是商品编号',
	  `title` varchar(100) NOT NULL COMMENT '商品标题',
	  `sell_point` varchar(150) DEFAULT NULL COMMENT '商品卖点',
	  `price` bigint(20) NOT NULL COMMENT '商品价格，单位为：分',
	  `num` int(10) NOT NULL COMMENT '库存数量',
	  `barcode` varchar(30) DEFAULT NULL COMMENT '商品条形码',
	  `image` varchar(500) DEFAULT NULL COMMENT '商品图片',
	  `cid` bigint(10) NOT NULL COMMENT '所属类目，叶子类目',
	  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '商品状态，1-正常，2-下架，3-删除',
	  `created` datetime NOT NULL COMMENT '创建时间',
	  `updated` datetime NOT NULL COMMENT '更新时间',
	  PRIMARY KEY (`id`),
	  KEY `cid` (`cid`),
	  KEY `status` (`status`),
	  KEY `updated` (`updated`)
	) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='商品表';
 */
@Entity
@Table(name="BASE_PRODUCT")
public class ProductDO extends BaseDO{

	private static final long serialVersionUID = -2584906276981747466L;
	
	@Column(name="title",length=100)
	private String title;//商品标题
	@Column(name="sell_point",length=150)
	private String sellPoint;//商品卖点
	@Column(name="price")
	private Long price;//商品价格，单位为：分
	@Column(name="num")
	private int num;//库存数量
	@Column(name="barcode",length=30)
	private String barcode;//商品条形码
	@Column(name="image",length=500)
	private String image;//商品图片
	@Column(name="cid",length=500)
	private String cid;//所属类目，叶子类目
	@Column(name="status",length=500)
	private String status;//COMMENT '商品状态，1-正常，2-下架，3-删除
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
