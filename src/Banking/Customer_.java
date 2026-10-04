package Banking;

import java.io.Serializable;
import java.util.Random;

public class Customer_ implements Serializable {

	private static final long serialVersionUID = 1L;

	// ==========================================
	// DATA MEMBERS
	// ==========================================

	private String customerId;
	private String name;
	private String mobile;
	private String address;
	private int age;
	private String password;
	private String accountNumber;

	// ==========================================
	// CONSTRUCTOR 1
	// ==========================================

	public Customer_(String name, String mobile, String address, int age, String password) {

		if (!isValidMobile(mobile)) {
			throw new IllegalArgumentException("Invalid mobile number! " + mobileRule());
		}

		if (age < 18) {
			throw new IllegalArgumentException("Customer must be 18 years or above.");
		}

		if (!isValidPassword(password)) {
			throw new IllegalArgumentException("Invalid password! " + passwordRule());
		}

		this.customerId = generateCustomerId();
		this.name = name;
		this.mobile = mobile;
		this.address = address;
		this.age = age;
		this.password = password;

		this.accountNumber = generateAccountNumber();
	}

	// ==========================================
	// CONSTRUCTOR 2
	// Used by BankApp
	// ==========================================

	public Customer_(String customerId, String name, String mobile, String address, String password) {

		if (customerId == null || customerId.trim().isEmpty()) {

			throw new IllegalArgumentException("Customer ID cannot be empty.");
		}

		if (!isValidMobile(mobile)) {
			throw new IllegalArgumentException("Invalid mobile number! " + mobileRule());
		}

		if (!isValidPassword(password)) {
			throw new IllegalArgumentException("Invalid password! " + passwordRule());
		}

		this.customerId = customerId;
		this.name = name;
		this.mobile = mobile;
		this.address = address;
		this.password = password;

		this.age = 0;

		this.accountNumber = generateAccountNumber();
	}

	// ==========================================
	// CONSTRUCTOR 3
	// Customer ID + Age
	// ==========================================

	public Customer_(String customerId, String name, String mobile, String address, int age, String password) {

		if (customerId == null || customerId.trim().isEmpty()) {

			throw new IllegalArgumentException("Customer ID cannot be empty.");
		}

		if (!isValidMobile(mobile)) {
			throw new IllegalArgumentException("Invalid mobile number! " + mobileRule());
		}

		if (age < 18) {
			throw new IllegalArgumentException("Customer must be 18 years or above.");
		}

		if (!isValidPassword(password)) {
			throw new IllegalArgumentException("Invalid password! " + passwordRule());
		}

		this.customerId = customerId;
		this.name = name;
		this.mobile = mobile;
		this.address = address;
		this.age = age;
		this.password = password;

		this.accountNumber = generateAccountNumber();
	}

	// ==========================================
	// COPY CONSTRUCTOR
	// ==========================================

	public Customer_(Customer_ other) {

		if (other == null) {
			throw new IllegalArgumentException("Customer object cannot be null.");
		}

		this.customerId = other.customerId;
		this.name = other.name;
		this.mobile = other.mobile;
		this.address = other.address;
		this.age = other.age;
		this.password = other.password;
		this.accountNumber = other.accountNumber;
	}

	// ==========================================
	// MOBILE VALIDATION
	// ==========================================

	public static boolean isValidMobile(String mobile) {

		return mobile != null && mobile.matches("[6-9][0-9]{9}");
	}

	public static String mobileRule() {

		return "Mobile number must contain exactly " + "10 digits and must start with " + "6, 7, 8 or 9.";
	}

	// ==========================================
	// PASSWORD VALIDATION
	// ==========================================

	public static boolean isValidPassword(String password) {

		if (password == null || password.length() < 8) {

			return false;
		}

		// Capital letter
		if (!password.matches(".*[A-Z].*")) {
			return false;
		}

		// Small letter
		if (!password.matches(".*[a-z].*")) {
			return false;
		}

		// Number
		if (!password.matches(".*[0-9].*")) {
			return false;
		}

		// Special character
		if (!password.matches(".*[^a-zA-Z0-9].*")) {

			return false;
		}

		return true;
	}

	public static String passwordRule() {

		return "Password must contain minimum " + "8 characters, one CAPITAL letter, "
				+ "one small letter, one number and " + "one special character.";
	}

	// ==========================================
	// PASSWORD CHECK
	// ==========================================

	public boolean checkPassword(String password) {

		return this.password != null && this.password.equals(password);
	}

	// ==========================================
	// RANDOM CUSTOMER ID
	// Example: SBI-C5832
	// ==========================================

	private static String generateCustomerId() {

		Random random = new Random();

		int number = 1000 + random.nextInt(9000);

		return "SBI-C" + number;
	}

	// ==========================================
	// RANDOM ACCOUNT NUMBER
	// 12 DIGITS
	// ==========================================

	private static String generateAccountNumber() {

		Random random = new Random();

		StringBuilder accountNumber = new StringBuilder();

		// First 4 digits
		accountNumber.append(1000 + random.nextInt(9000));

		// Next 8 digits
		accountNumber.append(10000000 + random.nextInt(90000000));

		return accountNumber.toString();
	}

	// ==========================================
	// GETTERS
	// ==========================================

	public String getCustomerId() {
		return customerId;
	}

	public String getName() {
		return name;
	}

	public String getMobile() {
		return mobile;
	}

	public String getAddress() {
		return address;
	}

	public int getAge() {
		return age;
	}

	public String getPassword() {
		return password;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	// ==========================================
	// SETTERS
	// ==========================================

	public void setMobile(String mobile) {

		if (!isValidMobile(mobile)) {
			throw new IllegalArgumentException("Invalid mobile number! " + mobileRule());
		}

		this.mobile = mobile;
	}

	public void setAddress(String address) {

		this.address = address;
	}

	public void setAge(int age) {

		if (age < 18) {
			throw new IllegalArgumentException("Age must be 18 years or above.");
		}

		this.age = age;
	}

	public void setPassword(String password) {

		if (!isValidPassword(password)) {
			throw new IllegalArgumentException("Invalid password! " + passwordRule());
		}

		this.password = password;
	}

	// ==========================================
	// DISPLAY CUSTOMER DETAILS
	// ==========================================

	public void displayCustomerDetails() {

		System.out.println();
		System.out.println("========== CUSTOMER DETAILS ==========");

		System.out.println("Customer ID    : " + customerId);

		System.out.println("Account Number : " + accountNumber);

		System.out.println("Name           : " + name);

		System.out.println("Mobile         : " + mobile);

		System.out.println("Address        : " + address);

		System.out.println("Age            : " + age);

		System.out.println("Password       : ********");

		System.out.println("======================================");
	}

	// ==========================================
	// TO STRING
	// ==========================================

	@Override
	public String toString() {

		return "Customer_{" + "customerId='" + customerId + '\'' + ", accountNumber='" + accountNumber + '\''
				+ ", name='" + name + '\'' + ", mobile='" + mobile + '\'' + ", address='" + address + '\'' + ", age="
				+ age + '}';
	}
}