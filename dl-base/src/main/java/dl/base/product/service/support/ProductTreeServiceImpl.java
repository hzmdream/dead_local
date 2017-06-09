package dl.base.product.service.support;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;

import dl.base.product.dao.ProductTypeDao;
import dl.base.product.model.ProductTypeDO;
import dl.base.product.service.ProductTreeService;
import dl.base.product.util.productstree.TreeNote;
import dl.common.dao.BaseDao;
import dl.common.service.BaseServiceImpl;

public class ProductTreeServiceImpl extends BaseServiceImpl<ProductTypeDO> implements ProductTreeService {

	@Autowired
	private ProductTypeDao productTypeDao;
	
	@Override
	public List<TreeNote> getProductTreeNote(List<ProductTypeDO> data) {
		if (data==null||data.size()==0) {
			return null;
		}else {
			Set<TreeNote> tree = new TreeSet<TreeNote>();
			for (int i = 0; i < data.size(); i++) {
				ProductTypeDO productTypeDO = data.get(i);
				if (productTypeDO.getIsParent()) {
					//根据数据库中保存的ProductTypeDO产品某一类数据，组建对应TreeNote，树节点对象
//					tree.add();
				}
			}
		}
		return null;
	}
	
	private TreeNote buildTreeNote(ProductTypeDO productTypeDO){
		TreeNote treeNote = new TreeNote();
		treeNote.setSelfId(productTypeDO.getId());
		treeNote.setParentId(productTypeDO.getParentId());
		treeNote.setTitle(productTypeDO.getName());
		
		//查找子节点
		List<ProductTypeDO> childNodes = this.productTypeDao.getChildNode(productTypeDO.getId());
		if (childNodes==null) {
			return treeNote;
		}else {
			for (int i = 0; i < childNodes.size(); i++) {
				buildTreeNote(childNodes.get(i) );
			}
		}
//		treeNote.setChildrenNotes(childrenNotes);
		return null;
	}

}
