package cz.novoj.ibatis.model.product;

import java.io.Serializable;

/**
 * Group of product.
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
public class Group implements Serializable {
	private static final long serialVersionUID = 790423051737371959L;
	private Integer id;
  	private String name;
  	private String groupType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	@SuppressWarnings({"ControlFlowStatementWithoutBraces", "RedundantIfStatement"})
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		Group that = (Group)o;

		if(groupType != null ? !groupType.equals(that.groupType) : that.groupType != null) return false;
		if(id != null ? !id.equals(that.id) : that.id != null) return false;
		if(name != null ? !name.equals(that.name) : that.name != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (groupType != null ? groupType.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("ProductGroup");
		sb.append("{id=").append(id);
		sb.append(", name='").append(name).append('\'');
		sb.append(", groupType='").append(groupType).append('\'');
		sb.append('}');
		return sb.toString();
	}

}
