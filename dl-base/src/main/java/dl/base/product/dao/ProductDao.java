package dl.base.product.dao;

import org.springframework.stereotype.Repository;

import dl.base.product.model.ProductDO;
import dl.common.dao.BaseDao;
import dl.common.dao.IBaseDao;

@Repository("productDao")
public class ProductDao extends BaseDao<ProductDO> implements IBaseDao<ProductDO>{

	protected ProductDao() {
		super(ProductDO.class);
	}
	
}
