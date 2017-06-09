package dl.base.product.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class CategoryHelper {

	private static  TreeNode root;
	
	public  static  HashSet<TreeNode> tempNodeList;
	
	private static CategoryHelper treeHelper;
	
	private final static Lock lock =new ReentrantLock();   //创建一个锁

	private CategoryHelper() {
		
		/**
		 * TODO 加载分类树
		*/
		
	}
	
	public static CategoryHelper getInstance() {
		if (treeHelper == null) {
			lock .lock();
			try {
				if (treeHelper == null) {
					treeHelper = new CategoryHelper();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				lock.unlock();
			}
		}
		return treeHelper;
	}
	

	/**
	 *  找到一颗树中某个节点 
	 * @param tree
	 * @param id
	 * @return
	 */
	public static TreeNode getTreeNodeById(TreeNode tree, int id) {
		if (tree == null)
			return null;
		TreeNode treeNode = tree.findTreeNodeById(id);
		return treeNode;
	}
	
	/**
	 * 根据节点ID找到TreeNote 
	 */
	public static TreeNode getTreeNodeById(int id){
		if(id==-1)
			return null;
		HashMap<String,TreeNode> hashMap=getNodesIntoMap();
		TreeNode node=hashMap.get(String.valueOf(id));
		return node;
	}
	

	 /** generate a tree from the given treeNode or entity list 
      *  生成树或实体由给定的treeNode名单
     *
     * */
	public static void generateTree() {
		HashMap nodeMap = getNodesIntoMap();
		Iterator it = nodeMap.values().iterator();
		while (it.hasNext()) {
			TreeNode treeNode = (TreeNode) it.next();
			int parentId = treeNode.getParentId();
			String parentKeyId = String.valueOf(parentId);
			if (nodeMap.containsKey(parentKeyId)) {
				TreeNode parentNode = (TreeNode) nodeMap.get(parentKeyId);
				if (parentNode == null) {
					return;
				} else {
					parentNode.addChildNode(treeNode);
				}
			}
		}
		
	}

	 /**
     * set the parent nodes point to the child nodes
     * 设置子节点集合添加到父节点上。
     * @param nodeMap
     *            a hashmap that contains all the treenodes by its id as the key
     *            一个hashmap,包括所有treenodes由其id作为关键
     */
	@Deprecated
	private  void putChildIntoParent(HashMap nodeMap) {
		Iterator it = nodeMap.values().iterator();
		while (it.hasNext()) {
			TreeNode treeNode = (TreeNode) it.next();
			int parentId = treeNode.getParentId();
			String parentKeyId = String.valueOf(parentId);
			if (nodeMap.containsKey(parentKeyId)) {
				TreeNode parentNode = (TreeNode) nodeMap.get(parentKeyId);
				if (parentNode == null) {
					return;
				} else {
					parentNode.addChildNode(treeNode);
				}
			}
		}
	}
	
	/**
     * put all the treeNodes into a hash table by its id as the key
     *  把所有的treeNodes成一张哈希表由其id作为关键
     * @return hashmap that contains the treenodes
     */
	protected static HashMap<String,TreeNode> getNodesIntoMap() {
		int maxId = Integer.MAX_VALUE;
		HashMap<String,TreeNode> nodeMap = new HashMap<String, TreeNode>();
		Iterator<TreeNode> it = tempNodeList.iterator();
		while (it.hasNext()) {
			TreeNode treeNode = (TreeNode) it.next();
			int id = treeNode.getSelfId();
			if (id < maxId) {
				maxId = id;
				root = treeNode;
			}
			String keyId = String.valueOf(id);
			nodeMap.put(keyId, treeNode);
		}
		return nodeMap;
	}


	/** 
	 * add a tree node to the tempNodeList 
     *  添加一个树节点的tempNodeList
     * 
     * */
	public static void addTreeNode(TreeNode treeNode) {
		lock.lock();
		try {
			 boolean insertFlag =root.insertJuniorNode(treeNode); //将已经产生的树节点插入到树
			   if (insertFlag) {
				   tempNodeList.add(treeNode);
			   }
			   else{
				   throw new RuntimeException(treeNode.getParentId()+"这父节点不存在");
			   }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	/* 删除节点和它下面的晚辈 */
	public static void delTreeNode(TreeNode treeNode){
		boolean absent = tempNodeList.contains(treeNode);
		if(absent){
			lock.lock();
			try {
				List<TreeNode> delTempNodeList = new ArrayList<TreeNode>();
				List<TreeNode> childList = treeNode.getJuniors();
				if (childList!=null&&childList.size()>0) {
					for (int i = 0; i < childList.size(); i++) {
						delTempNodeList.add(childList.get(i));
					}
				}
				delTempNodeList.add(treeNode);
				tempNodeList.removeAll(delTempNodeList); // 更新tempNodeList
				treeNode.deleteNode();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				lock.unlock();
			}
		}
	}
	
	/* 删除当前节点的某个子节点 */
	public static void delTreeNode(TreeNode treeNode,int childId){
		boolean absent = tempNodeList.contains(treeNode);
		if(absent){
			lock.lock();
			try {
				List<TreeNode> childList = treeNode.getChildList();
				if(childList!=null&&childList.size()>0){
					int childNumber = childList.size();
					for (int i = 0; i < childNumber; i++) {
						TreeNode child = childList.get(i);
						if (child.getSelfId() == childId) {
							tempNodeList.remove(child); 
							treeNode.deleteChildNode(childId);
						}
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				lock.unlock();
			}
		}
	}
	
	
	/**
	 * 更新树节点
	 * @param sourceTreeNode 源对象
	 * @param targetTreeNode 目标对象
	 */
	public static void updateTreeNode(TreeNode sourceTreeNode,String noteName){
		boolean absent = tempNodeList.contains(sourceTreeNode);
		if(absent){
			
			lock.lock();
			try {
				sourceTreeNode.setNodeName(noteName);
				Object obj=sourceTreeNode.getObj();
				if(obj!=null && obj instanceof OrganizationEntity){
					OrganizationEntity entity=(OrganizationEntity)obj;
					entity.setOrgName(noteName);
				}
			    tempNodeList.add(sourceTreeNode);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				lock.unlock();
			}
		}
	}
	
	/**
	 * 更新树节点
	 * @param sourceTreeNode 源对象
	 * @param targetTreeNode 目标对象
	 */
	public static void updateTreeNode(TreeNode sourceTreeNode,TreeNode targetTreeNode){
		boolean absent = tempNodeList.contains(sourceTreeNode);
		if(absent){
			lock.lock();
			try {
				sourceTreeNode.setNodeName(targetTreeNode.getNodeName());
				OrganizationEntity entity=new OrganizationEntity(targetTreeNode.getParentId(), targetTreeNode.getSelfId(), targetTreeNode.getNodeName());
				sourceTreeNode.setObj(entity);
				tempNodeList.add(sourceTreeNode);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				lock.unlock();
			}
		}
	}

	

	  /**
     * adapt the entities to the corresponding treeNode
     *  适应于相应的treeNode的实体
     * @param entityList
     *            list that contains the entities       列表包含的实体
     *@return the list containg the corresponding treeNodes of the entities
     *  containg相应的treeNodes名单的实体
     */
	public static HashSet<TreeNode> changeEnititiesToTreeNodes(Set entityList) {
		OrganizationEntity orgEntity = new OrganizationEntity();
		Set<TreeNode> tempNodeList = new HashSet<TreeNode>();
		Map<Integer, TreeNode> map=new HashMap<Integer, TreeNode>();
		TreeNode treeNode;

		Iterator<OrganizationEntity> it = entityList.iterator();
		while (it.hasNext()) {
			orgEntity = (OrganizationEntity) it.next();
			treeNode = new TreeNode();
			treeNode.setObj(orgEntity);
			treeNode.setParentId(orgEntity.getParentId());
			treeNode.setSelfId(orgEntity.getOrgId());
			treeNode.setNodeName(orgEntity.getOrgName());
			treeNode.setParentNode(map.get(orgEntity.getParentId()));
			tempNodeList.add(treeNode);
			map.put(orgEntity.getOrgId(), treeNode);
		}
		return (HashSet<TreeNode>) tempNodeList;
	}

	public static TreeNode getRoot() {
		return root;
	}

	public static HashSet<TreeNode> getTempNodeList() {
		return tempNodeList;
	}

	public  static void setTempNodeList(HashSet<TreeNode> tempNodeList) {
		CategoryHelper.tempNodeList = tempNodeList;
	}

	
	
}