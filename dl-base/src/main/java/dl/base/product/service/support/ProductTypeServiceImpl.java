package dl.base.product.service.support;

import org.springframework.stereotype.Service;

import dl.base.product.model.ProductTypeDO;
import dl.base.product.service.ProductTypeService;
import dl.common.service.BaseServiceImpl;

@Service(value="productTypeService")
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductTypeDO> implements ProductTypeService {

}
