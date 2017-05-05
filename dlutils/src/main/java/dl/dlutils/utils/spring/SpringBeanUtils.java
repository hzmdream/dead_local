package dl.dlutils.utils.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;


public class SpringBeanUtils {
	
	@Autowired
	private static HibernateTemplate hibernateTemplate;
	
	public static HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	public static void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		SpringBeanUtils.hibernateTemplate = hibernateTemplate;
	}
	
}