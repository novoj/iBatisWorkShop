package cz.novoj.ibatis.model;

import java.io.Serializable;

/**
 * Some user POJO.
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
public class User implements Serializable {
	private static final long serialVersionUID = 838459347750327256L;
	public static final Integer USER_TYPE_EMPLOYEE = 0;
	public static final Integer USER_TYPE_EXTERNAL = 1;

	private Integer id;
	private String login;
	private String firstName;
	private String lastName;
	private String birthDate;
	private Integer state;
	private Integer type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@SuppressWarnings({"ControlFlowStatementWithoutBraces", "RedundantIfStatement"})
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		User user = (User)o;

		if(birthDate != null ? !birthDate.equals(user.birthDate) : user.birthDate != null) return false;
		if(firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
		if(id != null ? !id.equals(user.id) : user.id != null) return false;
		if(lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
		if(login != null ? !login.equals(user.login) : user.login != null) return false;
		if(state != null ? !state.equals(user.state) : user.state != null) return false;
		if(type != null ? !type.equals(user.type) : user.type != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (login != null ? login.hashCode() : 0);
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
		result = 31 * result + (state != null ? state.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("User");
		sb.append("{id=").append(id);
		sb.append(", login='").append(login).append('\'');
		sb.append(", firstName='").append(firstName).append('\'');
		sb.append(", lastName='").append(lastName).append('\'');
		sb.append(", birthDate='").append(birthDate).append('\'');
		sb.append(", state=").append(state);
		sb.append(", type=").append(type);
		sb.append('}');
		return sb.toString();
	}
}
