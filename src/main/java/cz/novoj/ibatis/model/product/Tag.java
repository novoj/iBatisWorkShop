package cz.novoj.ibatis.model.product;

import java.io.Serializable;
import java.util.List;

/**
 * Tag used for cross cutting selection of products.
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
public class Tag implements Serializable {
	private static final long serialVersionUID = -6910974005310386126L;
	private Integer id;
	private String name;
	private List<Product> products;

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

	@SuppressWarnings({"ReturnOfCollectionOrArrayField"})
	public List<Product> getProducts() {
		return products;
	}

	@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter"})
	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@SuppressWarnings({"ControlFlowStatementWithoutBraces", "ControlFlowStatementWithoutBraces"})
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		Tag tag = (Tag)o;

		if(id != null ? !id.equals(tag.id) : tag.id != null) return false;
		if(name != null ? !name.equals(tag.name) : tag.name != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Tag");
		sb.append("{id=").append(id);
		sb.append(", name='").append(name).append('\'');
		sb.append(", products=").append(products);
		sb.append('}');
		return sb.toString();
	}

}
