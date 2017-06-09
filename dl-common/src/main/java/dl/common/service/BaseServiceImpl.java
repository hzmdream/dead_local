package dl.common.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import dl.common.dao.BaseDao;
import dl.common.entity.BaseDO;
import dl.dlutils.utils.pagination.PageUtil;
import dl.dlutils.utils.pagination.Pagination;

public abstract class BaseServiceImpl<T extends BaseDO> implements BaseService<T>{

	@Autowired
	public BaseDao<T> repository;
	
	@Override
	public void saveOrUpdate(T entity) {
		repository.saveOrUpdate(entity);
	}

	@Override
	public boolean updateObject(T obj) {
		return repository.updateObject(obj);
	}

	@Override
	public boolean updateObjects(Collection<T> collections) {
		return repository.updateObjects(collections);
	}

	@Override
	public Serializable insertObject(T obj) {
		return repository.insertObject(obj);
	}

	@Override
	public Boolean insertObjects(Collection<T> collections) {
		return repository.insertObjects(collections);
	}

	@Override
	public PageUtil<T> queryObjectsByPage(String hql, Map<String, Object> params, Pagination pagination) {
		return repository.queryObjectsByPage(hql, params, pagination);
	}

	@Override
	public PageUtil<T> queryObjectsByPage(String hql, String countHql, Map<String, Object> params,
			Pagination pagination) {
		return repository.queryObjectsByPage(hql, countHql, params, pagination);
	}

	@Override
	public void deleteObjectLogical(T obj) {
		repository.deleteObjectLogical(obj);
	}

	@Override
	public void deleteObjectsLogical(Collection<T> collections) {
		repository.deleteObjectsLogical(collections);
	}

	@Override
	public void internalUndelete(T obj) {
		repository.insertObject(obj);
	}

	@Override
	public void internalUndelete(Collection<T> collections) {
		repository.internalUndelete(collections);
	}

	@Override
	public T queryObjectById(Class<T> entity, Serializable id) {
		return repository.queryObjectById(entity, id);
	}

	@Override
	public List<?> query(String hql, Object... parameters) {
		return repository.query(hql, parameters);
	}

	@Override
	public List<T> queryAll(Class<T> clazz) {
		return repository.queryAll(clazz);
	}

	@Override
	public List<?> query(String hql, String[] paramNames, Object[] values) {
		return repository.query(hql, paramNames, values);
	}

	@Override
	public List<T> getList() {
		return repository.getList();
	}

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> map, Map<String, Object> searchMap, String order)
			throws Exception {
		return repository.getList(map, searchMap, order);
	}

	@Override
	public List<T> getExportList() {
		return repository.getExportList();
	}

	@Override
	public List<T> internalGetList(Map<String, Object> map, Map<String, Object> searchMap, String order) {
		return repository.internalGetList(map, searchMap, order);
	}
	
}
