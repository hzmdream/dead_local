package dl.base.product.util;

public class OrganizationEntity {

	public int parentId;
	
	public int orgId;
	
	public String orgName;

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public OrganizationEntity(){
		
	}
	
	
	public OrganizationEntity(int parentId, int orgId, String orgName) {
		super();
		this.parentId = parentId;
		this.orgId = orgId;
		this.orgName = orgName;
	}
	
	
}