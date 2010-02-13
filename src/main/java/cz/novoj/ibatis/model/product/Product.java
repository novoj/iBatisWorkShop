package cz.novoj.ibatis.model.product;

import java.io.Serializable;
import java.util.List;

/**
 * Product in some virtual shop.
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
public class Product implements Serializable {
	private static final long serialVersionUID = -700916846569097670L;
	private Integer id;
  	private String name;
  	private Group group;
	private List<Tag> tags;

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

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@SuppressWarnings({"ReturnOfCollectionOrArrayField"})
	public List<Tag> getTags() {
		return tags;
	}

	@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter"})
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	@SuppressWarnings({"ControlFlowStatementWithoutBraces", "RedundantIfStatement"})
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		Product product = (Product)o;

		if(group != null ? !group.equals(product.group) : product.group != null) return false;
		if(id != null ? !id.equals(product.id) : product.id != null) return false;
		if(name != null ? !name.equals(product.name) : product.name != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (group != null ? group.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Product");
		sb.append("{id=").append(id);
		sb.append(", name='").append(name).append('\'');
		sb.append(", group=").append(group);
		sb.append('}');
		return sb.toString();
	}
}
