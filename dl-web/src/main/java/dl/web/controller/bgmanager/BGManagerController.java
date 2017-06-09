package dl.web.controller.bgmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dl.base.product.model.ProductDO;
import dl.base.product.model.ProductTypeDO;
import dl.base.product.service.ProductService;
import dl.base.product.service.ProductTypeService;

@Controller
@RequestMapping(value="/bgmanager")
public class BGManagerController {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductTypeService productTypeService;
	/**
	 * @author houzm
	 * @time 2017年5月29日上午9:41:55
	 * @description 获取所有产品
	 * @return
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="/getproducts",method=RequestMethod.GET)
	public Map<String, Object> getProducts(){
		Map<String, Object> result = new HashMap<String,Object>();
		List<ProductDO> products = productService.getProducts(ProductDO.class);
		if (products!=null&&products.size()>0) {
			result.put("success", true);
			result.put("data", products);
		}else {
			result.put("success", false);
			result.put("data", "产品加载失败！");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/getproducttypestree",method=RequestMethod.GET)
	public Map<String, Object> getProductTypesTree(){
		//检索查询所有分类
		List<ProductTypeDO> productTypes = productTypeService.queryAll(ProductTypeDO.class);
		if (!productTypes.isEmpty()) {
			//检索查询结果中所有一级分类--parentId=0
			
		}
		return null;
	}
	
	private List<Object> buildProductTypesTree(List<ProductTypeDO> productTypes){
		if (productTypes.isEmpty()) {
			return null;
		}else {
			
		}
		/*for (int i = 0; i < array.length; i++) {
			
		}*/
		return null;
		
	}
	private List<ProductTypeDO> get(List<ProductTypeDO> productTypes){
		List<ProductTypeDO> result = new ArrayList<ProductTypeDO>();
		if (!productTypes.isEmpty()) {
			if (productTypes.size()>0) {
				for (int i = 0; i < productTypes.size(); i++) {
					ProductTypeDO productTypeDO = productTypes.get(i);
					if (productTypeDO.getParentId()==0) {
						result.add(productTypeDO);
					}
				}
				return result;
			}else {
				return null;
			}
		}else {
			
		}
		return result;
		
	}
}
