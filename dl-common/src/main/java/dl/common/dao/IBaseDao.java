package dl.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import dl.common.entity.BaseDO;
import dl.dlutils.utils.pagination.PageUtil;
import dl.dlutils.utils.pagination.Pagination;

public interface IBaseDao<T extends BaseDO> {
	public Serializable saveOrUpdate(final T obj);
	public boolean updateObject(final T obj);
	public boolean updateObjects(final Collection<T> collections);
	public Serializable insertObject(final T obj);
	public Boolean insertObjects(final Collection<T> collections);
	public PageUtil<T> queryObjectsByPage(String hql,Map<String,Object> params,Pagination pagination);
	public PageUtil<T> queryObjectsByPage(String hql,String countHql,Map<String,Object> params,Pagination pagination);
	public void deleteObjectLogical(final T obj);
	public void deleteObjectsLogical(final Collection<T> collections);
	public void internalUndelete(final T obj);
	public void internalUndelete(final Collection<T> collections);
	public List<T> queryAll(Class<T> clazz);
	public T queryObjectById(final Class<T> entity, Serializable id);
	public List<?> query(String hql, Object... parameters);
	public List<?> query(String hql, String[] paramNames, Object[] values);
	public List<T> getList();
	public List<Map<String, Object>> getList(Map<String, Object> map, Map<String, Object> searchMap, String order) throws Exception;
	public List<T> getExportList();
	public List<T> internalGetList(Map<String, Object> map, Map<String, Object> searchMap, String order);
	
}
