package mail.bean;

public class Register {
	String	name			= "", passWord = "", phone = "";
	String	mail			= "", gender = "", message = "";
	String	mailPassword	= "";
	Boolean	fMailPassWord	= false;
	String	backNews;

	public String getName() {
		return name;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public Boolean getfMailPassWord() {
		return fMailPassWord;
	}

	public void setfMailPassWord(Boolean fMailPassWord) {
		this.fMailPassWord = fMailPassWord;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBackNews() {
		return backNews;
	}

	public void setBackNews(String backNews) {
		this.backNews = backNews;
	}

	public void setMailPassword(boolean b) {
		// TODO Auto-generated method stub

	}

}
