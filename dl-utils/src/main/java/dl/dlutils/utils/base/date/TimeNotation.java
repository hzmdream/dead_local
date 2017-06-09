package dl.dlutils.utils.base.date;

/**
 * Date formats.
 */
public enum TimeNotation {
	
	H12("H12"), H24("H24");
	
	private String key;
	
	TimeNotation(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
}
