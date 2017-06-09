package dl.base.product.service;

import java.util.List;

import dl.base.product.model.ProductDO;
import dl.common.service.BaseService;

public interface ProductService extends BaseService<ProductDO>{
	public List<ProductDO> getProducts(Class<ProductDO> clazz);
}
