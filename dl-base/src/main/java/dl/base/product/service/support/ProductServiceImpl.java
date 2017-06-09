package dl.base.product.service.support;

import java.util.List;

import org.springframework.stereotype.Service;

import dl.base.product.model.ProductDO;
import dl.base.product.service.ProductService;
import dl.common.service.BaseServiceImpl;

@Service("productService")
public class ProductServiceImpl extends BaseServiceImpl<ProductDO> implements ProductService {
	
	public List<ProductDO> getProducts(Class<ProductDO> clazz){
		List<ProductDO> products = repository.queryAll(clazz);
		return products;
	}
	
}
