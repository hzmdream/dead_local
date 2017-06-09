package dl.base.product.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import dl.base.product.model.ProductTypeDO;
import dl.common.dao.BaseDao;
import dl.common.dao.IBaseDao;

@Repository
public class ProductTypeDao extends BaseDao<ProductTypeDO> implements IBaseDao<ProductTypeDO> {

	protected ProductTypeDao() {
		super(ProductTypeDO.class);
	}
	
	public List<ProductTypeDO> getChildNode(Long id){
		String hql = "from BASE_PRODUCT_TYPE where deleted = 0 and parentId = "+id;
		@SuppressWarnings("unchecked")
		List<ProductTypeDO> ret = (List<ProductTypeDO>) this.query(hql);
		return ret;
	}

}
