package dl.dlutils.utils.security;

public enum DLConstant {

	JDBC_DATASOURCE_USERNAME_KEY("jdbc.name"),
	JDBC_DATASOURCE_PASSWORD_KEY("jdbc.password"),
	JDBC_DATASOURCE_SCHEMA_KEY("jdbc.schema");
	
	private String message;
	
	private DLConstant(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
