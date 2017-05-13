package dl.dlutils.utils.security;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import dl.dlutils.utils.encrypt.asymmetric.rsa.RSAUtil;
import dl.dlutils.utils.encrypt.copy.AESSecurityUtil;


public class EncryptablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private static String key = "m71qQKYOGQ9omsB3jppTNQ==";
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		try {
			String username = props.getProperty(DLConstant.JDBC_DATASOURCE_USERNAME_KEY.getMessage());
			if (username != null) {
				System.out.println("AESSecurityUtil.decrypt(username, key)>>>>>>"+AESSecurityUtil.decrypt(username, key));
				props.setProperty(DLConstant.JDBC_DATASOURCE_USERNAME_KEY.getMessage(), AESSecurityUtil.decrypt(username, key));
//				props.setProperty(DLConstant.JDBC_DATASOURCE_USERNAME_KEY.getMessage(), RSAUtil.decrypt(username));
//				props.setProperty(DLConstant.JDBC_DATASOURCE_USERNAME_KEY.getMessage(), "houzm_test");
		    }
		    
		    String password = props.getProperty(DLConstant.JDBC_DATASOURCE_PASSWORD_KEY.getMessage());
		    if (password != null) {
		    	System.out.println("AESSecurityUtil.decrypt(password, key)>>>>>>"+AESSecurityUtil.decrypt(password, key));
		    	props.setProperty(DLConstant.JDBC_DATASOURCE_PASSWORD_KEY.getMessage(),  AESSecurityUtil.decrypt(password, key));
//		    	props.setProperty(DLConstant.JDBC_DATASOURCE_PASSWORD_KEY.getMessage(), RSAUtil.decrypt(password));
//		        props.setProperty(DLConstant.JDBC_DATASOURCE_PASSWORD_KEY.getMessage(), "houzm");
		    }
		    
		    String schema = props.getProperty(DLConstant.JDBC_DATASOURCE_SCHEMA_KEY.getMessage());
		    if (schema != null) {
		    	System.out.println("AESSecurityUtil.decrypt(schema, key)>>>>>>"+AESSecurityUtil.decrypt(schema, key));
		    	props.setProperty(DLConstant.JDBC_DATASOURCE_SCHEMA_KEY.getMessage(),  AESSecurityUtil.decrypt(schema, key));
//		    	props.setProperty(DLConstant.JDBC_DATASOURCE_SCHEMA_KEY.getMessage(), "dead_local");
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
