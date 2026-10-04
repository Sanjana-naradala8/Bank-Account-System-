package Banking;

import java.io.Serializable;

// Used by IT / System Administrator: addStaffLogin(), removeStaffLogin()
public class StaffLogin implements Serializable {
	private static final long serialVersionUID = 1L;

	private String staffLoginId;
	private String staffName;
	private String role;

	public StaffLogin(String staffLoginId, String staffName, String role) {
		this.staffLoginId = staffLoginId;
		this.staffName = staffName;
		this.role = role;
	}

	public String getStaffLoginId() { return staffLoginId; }
	public String getStaffName() { return staffName; }
	public String getRole() { return role; }

	@Override
	public String toString() {
		return staffLoginId + " | " + staffName + " | " + role;
	}
}