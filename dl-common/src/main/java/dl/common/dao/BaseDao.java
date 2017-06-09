package dl.common.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import dl.common.entity.BaseDO;
import dl.dlutils.utils.base.bean.BeanHelper;
import dl.dlutils.utils.base.bean.IDisplayable;
import dl.dlutils.utils.base.date.DateUtils;
import dl.dlutils.utils.pagination.PageUtil;
import dl.dlutils.utils.pagination.Pagination;

public abstract class BaseDao<T extends BaseDO> extends HibernateDaoSupport implements IBaseDao<T>{

    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseDao.class);

    protected Class<T> clazz;

    protected BaseDao(final Class<T> clazz) {
        this.clazz = clazz;
    }

    public Serializable saveOrUpdate(final T obj) {
        Serializable id = null;
        if (obj.getId() != null) {
            updateObject(obj);
        } else {
            id = insertObject(obj);
        }
        return id;
    }

    /**
     * 更新单个对象
     * @param obj
     * @return
     */
    public boolean updateObject(final T obj) {
        final Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        obj.setLastUpdate();
        //obj.setLastModifier(UserContext.getUserId());
        session.update(obj);
        LOGGER.info("Object updated: " + obj.toString());
        session.flush();
        return true;
    }

    /**
     * 更新Collection
     * @param collections
     * @return
     */
    public boolean updateObjects(final Collection<T> collections) {
        final Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        for (T object : collections) {
            object.setLastUpdate();
            //object.setLastModifier(UserContext.getUserId());
            session.update(object);
            LOGGER.info("Object updated: " + object.toString());
        }
        session.flush();
        return true;
    }

    /**
     *
     * @param obj
     * @return
     */
    public Serializable insertObject(final T obj) {
        Validate.notNull(obj);
        obj.setCreated();
        obj.setDeleted(false);//是否删除
        obj.setLastUpdate(new Date());//最后修改时间
//        obj.setLastModifier(0L);//最后修改用户ID
        //obj.setCreator(UserContext.getUserId());//创建用户ID
        final Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        final Serializable id = session.save(obj);
        LOGGER.info("New object added (" + id + "): " + obj.toString());
        session.flush();
        return id;
    }

    /**
     * 插入多条记录
     * @param obj
     * @return
     */
    public Boolean insertObjects(final Collection<T> collections) {
        Validate.notNull(collections);
        final Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        for (T object : collections) {
            object.setCreated();
            //object.setCreator(UserContext.getUserId());
            session.save(object);
            LOGGER.info("New object added " + object.toString());
        }
        session.flush();
        return true;
    }

    /**
     * 分页查询
     * @param hql
     * @param pagination
     * @return
     */
    @SuppressWarnings("unchecked")
    public PageUtil<T> queryObjectsByPage(String hql,Map<String,Object> params,Pagination pagination){
        final Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query queryCount = session.createQuery("select count(*) "+hql);
        queryCount = this.getQuery(queryCount, params);
		/*Long c = (Long)queryCount.iterate().next();*/
        Object object = queryCount.uniqueResult();
        int totalCount = 0;
        if(object!=null){
            totalCount = ((Long)object).intValue();
        }
        pagination.setTotalCount(totalCount);

        Query query = session.createQuery(hql);
        this.getQuery(query,params);
        List<T> pageList = query.setFirstResult((pagination.getCurrentPage()-1)*pagination.getPageSize())
                .setMaxResults(pagination.getPageSize()).list();
        return getPageUtil(pageList,pagination);
    }

    @SuppressWarnings("unchecked")
    /**
     *
     * @param hql
     * @param countHql(获取总条数hql)
     * @param params
     * @param pagination
     * @return
     */
    public PageUtil<T> queryObjectsByPage(String hql,String countHql,Map<String,Object> params,Pagination pagination){
        final Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query queryCount = session.createQuery(countHql);
        queryCount = this.getQuery(queryCount, params);
		/*Long c = (Long)queryCount.iterate().next();*/
        Object object = queryCount.uniqueResult();
        int totalCount = 0;
        if(object!=null){
            totalCount = ((Long)object).intValue();
        }
        pagination.setTotalCount(totalCount);

        Query query = session.createQuery(hql);
        this.getQuery(query,params);
        List<T> pageList = query.setFirstResult((pagination.getCurrentPage()-1)*pagination.getPageSize())
                .setMaxResults(pagination.getPageSize()).list();
        return getPageUtil(pageList,pagination);
    }

    protected PageUtil<T> getPageUtil(List<T> list,Pagination pagination){
        PageUtil<T> pageUtil = new PageUtil<T>();
        pageUtil.setData(list);
        pageUtil.setCurrPage(pagination.getCurrentPage());
        pageUtil.setPageSize(pagination.getPageSize());
        pageUtil.setPageCount(pagination.getPageCount());
        pageUtil.setTotalCount(pagination.getTotalCount());
        return pageUtil;
    }

    private Query getQuery(Query query,Map<String,Object> params){
        if(params!=null){
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }
        return query;
    }
    /**
     * 逻辑删除
     * @param obj
     */
    public void deleteObjectLogical(final T obj) {
        final Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        obj.setDeleted(true);
        obj.setLastUpdate();
        //obj.setLastModifier(UserContext.getUserId());
        session.update(obj);
        LOGGER.info("Object marked as deleted: " + obj.toString());
        session.flush();
    }

    /**
     * list 逻辑删除
     * @param col
     */
    public void deleteObjectsLogical(final Collection<T> collections) {
        for (final T object : collections) {
            deleteObjectLogical(object);
        }
    }

    public void internalUndelete(final T obj) {
        final Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        obj.setDeleted(false);
        obj.setLastUpdate();
        LOGGER.info("Object undeleted: " + obj.toString());
        session.update(obj);
        session.flush();
    }

    /**
     * 针对逻辑删除 数据恢复
     * @param col
     */
    public void internalUndelete(final Collection<T> collections) {
        for (final T obj : collections) {
            internalUndelete(obj);
        }
    }

    /**
     * 加载该表所有信息
     * @param clazz
     * @return
     */
    public List<T> queryAll(Class<T> clazz) {
        @SuppressWarnings("unchecked")
        final List<T> list = (List<T>) getHibernateTemplate().find("from " + clazz.getSimpleName() + " t");
        return list;
    }

    /**
     * 根据id加载object
     * @param entity
     * @param id
     * @return
     */
	public T queryObjectById(final Class<T> entity, Serializable id) {
        return (T) this.getHibernateTemplate().get(entity, id);
    }

    /**
     *
     * @param hql
     * @param parameters
     * @return
     */
    public List<?> query(String hql, Object... parameters) {
        return this.getHibernateTemplate().find(hql, parameters);
    }

    public List<?> query(String hql, String[] paramNames, Object[] values) {
        return this.getHibernateTemplate().findByNamedParam(hql, paramNames, values);
    }

    public List<T> getList() {
        return internalGetList(null, null, null);
    }

    public List<Map<String, Object>> getList(Map<String, Object> map, Map<String, Object> searchMap, String order) throws Exception {
        beforeGetList(map, searchMap, order);
        List<T> list = internalGetList(map, searchMap, order);
        List<Map<String, Object>> mapList = convert(list);
        afterGetList(mapList, map, searchMap, order);
        return mapList;
    }

    public List<T> getExportList() {
        return getList();
    }


    public List<T> internalGetList(Map<String, Object> map, Map<String, Object> searchMap, String order) {
        List<String> paramNames = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        String hql = generateHql(map, searchMap, order, paramNames, values);
        @SuppressWarnings("unchecked")
		List<T> list = (List<T>) this.getHibernateTemplate().findByNamedParam(hql.toString(), paramNames.toArray(new String[0]), values.toArray());
        sortList(list);
        return list;
    }

    protected void sortList(List<T> list) {
        // do nothing
    }

    private String generateHql(Map<String, Object> map, Map<String, Object> searchMap, String order, List<String> paramNames, List<Object> values) {
        StringBuilder hql = new StringBuilder(getBaseHql(map));
        int length = hql.length();
        if (map != null) {
            @SuppressWarnings("unchecked")
            List<List<Map<String, Object>>> ruleList = (List<List<Map<String, Object>>>) map.get("rule");
            if (ruleList != null && ruleList.size() > 0) {
                int i = 0;
                for (List<Map<String, Object>> andRule : ruleList) {
                    hql.append("(");
                    boolean flag = false;
                    for (Map<String, Object> orRule : andRule) {
                        String key = (String) orRule.get("key");
                        Object value = orRule.get("value");
                        if (value == null || "".equals(value)) continue;
                        value = getQueryParamValue(key, value);
                        String op = getOperation(orRule, "op");
                        if ("like".equals(op)) {
                            value = "%" + value + "%";
                        }
                        hql.append(getQueryParamName(key)).append(" ").append(op);
                        if ("in".equals(op)) {
                            hql.append(" (:").append(key.replace(".", "_") + i).append(")");
                        } else if ("is null".equals(op)) {
                        } else if ("is not null".equals(op)) {
                        } else {
                            hql.append(" :").append(key.replace(".", "_") + i);
                        }
                        hql.append(" or ");
                        try {
                            if (clazz.getDeclaredField(key).getType() == Date.class) {
                                value = DateUtils.parseIsoDate((String) value);
                            }
                        } catch (Exception e) {
                            // do nothing
                        }
                        if (!"is null".equals(op) && !"is not null".equals(op)) {
                            paramNames.add(key.replace(".", "_") + i);
                            values.add(value);
                            i++;
                        }
                        flag = true;
                    }
                    int end = hql.length();
                    int start = end - (flag ? 4 : 1);
                    hql.delete(start, end);
                    if (flag) hql.append(") and ");
                }
            }
        }
        if (length == hql.length()) {
            int end = hql.length();
            int start = end - 5;
            hql.delete(start, end);
        } else {
            int end = hql.length();
            int start = end - 4;
            hql.delete(start, end).append(")");
        }
        if (searchMap != null && !searchMap.isEmpty() && searchMap.get("value") != null && searchMap.get("value").toString().trim().length() > 0) {
            String[] fields = getSearchFields();
            if (fields != null && fields.length > 0) {
                hql.append(" and (");
                for (String field : fields) {
                    hql.append(getQueryParamName(field)).append(" like '%").append(searchMap.get("value")).append("%' or ");
                }
                hql.delete(hql.length() - 4, hql.length());
                hql.append(")");
            }
        }
        if (order != null && order.trim().length() > 0) {
            hql.append(" order by ").append(order);
        }
        return hql.toString();
    }

    protected String[] getSearchFields() {
        // TODO: 全文检索字段获取方式临时方法?
        return null;
    }

    protected String getBaseHql(Map<String, Object> map) {
        return "from " + clazz.getSimpleName() + " t where t.deleted = false and (";
    }

    protected String getQueryParamName(String key) {
        try {
            if (BaseDO.class.isAssignableFrom(this.clazz.getDeclaredField(key).getType()))
                return "t." + key + ".id";
        } catch (NoSuchFieldException e) {
            // do nothing
        } catch (SecurityException e) {
            // do nothing
        }
        return "t." + key;
    }

    protected Object getQueryParamValue(String key, Object value) {
        try {
            if (BaseDO.class.isAssignableFrom(this.clazz.getDeclaredField(key).getType()))
                return ((Number) value).intValue();
        } catch (NoSuchFieldException e) {
            // do nothing
        } catch (SecurityException e) {
            // do nothing
        }
        return value;
    }

    protected String getOperation(Map<String, Object> opMap, String key) {
        String operation = opMap == null ? null : (String) opMap.get(key);
        if (operation == null || operation.trim().length() == 0 || operation.trim().equals("==")) operation = "=";
        return operation;
    }



    protected void beforeGetList(Map<String, Object> map, Map<String, Object> searchMap, String order) {
        // do nothing
    }

    protected void afterGetList(List<?> list, Map<String, Object> paramMap, Map<String, Object> searchMap, String order) {
        // do nothing
    }

    public List<Map<String, Object>> convert(List<T> list) throws Exception {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (T o : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            setFields(map, o, clazz, true);
            mapList.add(map);
        }
        return mapList;
    }

    protected void setFields(Map<String, Object> map, Object obj, Class<?> claz, boolean multiple) throws Exception {
        for (Field field : claz.getDeclaredFields()) {
            setField(map, obj, claz, multiple, field);
        }
        final Class<?> superClazz = claz.getSuperclass();
        if (superClazz != null) setFields(map, obj, superClazz, multiple);
    }

    protected void setField(Map<String, Object> map, Object obj, Class<?> claz, boolean multiple, Field field) throws Exception {
        Class<?> type = field.getType();
        if (Modifier.isStatic(field.getModifiers())) return;
        if (Modifier.isFinal(field.getModifiers())) return;
        if (type == String.class || type == Integer.class || type == Long.class || type == Double.class || type == Boolean.class) {
            Method getter = BeanHelper.determineGetter(claz, field.getName());
            map.put(field.getName(), getter.invoke(obj));
        } else if (type == Date.class || type == Timestamp.class) {
            Method getter = BeanHelper.determineGetter(claz, field.getName());
            Date date = (Date) getter.invoke(obj);
            if (date != null) {
                map.put(field.getName(), DateUtils.formatIsoDate(date));
            } else {
                map.put(field.getName(), null);
            }
        } else if (Enum.class.isAssignableFrom(type)) {
            Method getter = BeanHelper.determineGetter(claz, field.getName());
            map.put(field.getName(), getter.invoke(obj));
        } else if (BaseDO.class.isAssignableFrom(type)) {
            Method getter = BeanHelper.determineGetter(claz, field.getName());
            BaseDO baseDO = (BaseDO) getter.invoke(obj);
            // modify by wayne， 懒加载
            if (baseDO != null) {
                if (multiple) {
                    if (isHibernateObjectProxyLoaded(baseDO)) {
                        map.put(field.getName() + "Id", baseDO.getId());
                        map.put(field.getName() + "Deleted", baseDO.isDeleted());
                        if (baseDO instanceof IDisplayable) {
                            map.put(field.getName(), ((IDisplayable) baseDO).getDisplayName());
                        }
                    } else {
                        map.put(field.getName(), null);
                    }
                } else {
                    if (isHibernateObjectProxyLoaded(baseDO)) {
                        map.put(field.getName(), baseDO.getId());
                    } else {
                        map.put(field.getName(), null);
                    }
                }
            } else {
                map.put(field.getName(), null);
            }
            // modify end
        } else if (Collection.class.isAssignableFrom(type)) {
            Method getter = BeanHelper.determineGetter(claz, field.getName());
            Collection<?> collection = (Collection<?>) getter.invoke(obj);
            // modify by wayne
            if (isHibernateCollectionProxyLoaded(collection)) {
                if (collection != null && collection.size() > 0) {
                    List<Map<String, Object>> ids = new ArrayList<Map<String, Object>>();
                    for (Object subObj : collection) {
                        Map<String, Object> subMap = new HashMap<String, Object>();
                        subMap.put("id", ((BaseDO) subObj).getId());
                        if (subObj instanceof IDisplayable)
                            subMap.put("name", ((IDisplayable) subObj).getDisplayName());
                        ids.add(subMap);
                    }
                    map.put(field.getName(), ids);
                } else {
                    map.put(field.getName(), null);
                }
            } else {
                map.put(field.getName(), null);
            }
            // modify end
        }
    }

    private boolean isHibernateCollectionProxyLoaded(Collection<?> collection) {
        if (collection instanceof PersistentCollection) {
            if (((PersistentCollection) collection).wasInitialized()) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    private boolean isHibernateObjectProxyLoaded(Object baseDO) {
        // 代理
        if (baseDO instanceof HibernateProxy) {
            HibernateProxy proxy = (HibernateProxy) baseDO;
            // 已加载  
            if (!proxy.getHibernateLazyInitializer().isUninitialized()) {
                return true;
            }
            // 未加载  
            else {
                return false;
            }
        }
        return true;
    }
}

