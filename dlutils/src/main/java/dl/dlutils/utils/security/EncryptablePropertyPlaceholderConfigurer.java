package dl.dlutils.utils.security;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import dl.dlutils.utils.encrypt.asymmetric.rsa.RSAUtil;


public class EncryptablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		try {
			String username = props.getProperty(DLConstant.JDBC_DATASOURCE_USERNAME_KEY.getMessage());
			if (username != null) {
				props.setProperty(DLConstant.JDBC_DATASOURCE_USERNAME_KEY.getMessage(), RSAUtil.decrypt(username));
//				props.setProperty(DLConstant.JDBC_DATASOURCE_USERNAME_KEY.getMessage(), "houzm_test");
		    }
		    
		    String password = props.getProperty(DLConstant.JDBC_DATASOURCE_PASSWORD_KEY.getMessage());
		    if (password != null) {
		    	props.setProperty(DLConstant.JDBC_DATASOURCE_PASSWORD_KEY.getMessage(), RSAUtil.decrypt(password));
//		        props.setProperty(DLConstant.JDBC_DATASOURCE_PASSWORD_KEY.getMessage(), "houzm");
		    }
		    
		    String schema = props.getProperty(DLConstant.JDBC_DATASOURCE_SCHEMA_KEY.getMessage());
		    if (schema != null) {
		    	props.setProperty(DLConstant.JDBC_DATASOURCE_SCHEMA_KEY.getMessage(), "dead_local");
		    }
			   /* String url = props.getProperty(DLConstant.JDBC_DATASOURCE_URL_KEY.getMessage());
			if (url != null) {
			    props.setProperty(DLConstant.JDBC_DATASOURCE_URL_KEY.getMessage(), RSAUtil.decrypt(url));
			}
			
			String driverClassName = props.getProperty(DLConstant.JDBC_DATASOURCE_DRIVERCLASSNAME_KEY.getMessage());
			if(driverClassName != null){
			    props.setProperty(DLConstant.JDBC_DATASOURCE_DRIVERCLASSNAME_KEY.getMessage(), RSAUtil.decrypt(driverClassName));
			}
			String dbtype = props.getProperty(DLConstant.JDBC_DATASOURCE_TYPE_KEY.getMessage());
			if(dbtype != null){
			    props.setProperty(DLConstant.JDBC_DATASOURCE_TYPE_KEY.getMessage(), RSAUtil.decrypt(dbtype));
			}*/
			super.processProperties(beanFactoryToProcess, props);
	    } catch (Exception e) {
			e.printStackTrace();
		}
	}
}
