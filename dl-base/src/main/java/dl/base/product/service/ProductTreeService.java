package dl.base.product.service;

import java.util.List;

import dl.base.product.model.ProductTypeDO;
import dl.base.product.util.productstree.TreeNote;
import dl.common.service.BaseService;

public interface ProductTreeService extends BaseService<ProductTypeDO> {

	public abstract List<TreeNote> getProductTreeNote(List<ProductTypeDO> productTypeDOs);
}
