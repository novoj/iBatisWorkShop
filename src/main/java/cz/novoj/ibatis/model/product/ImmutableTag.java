package cz.novoj.ibatis.model.product;

import java.io.Serializable;

/**
 * An immutable version of the tag.
 *
 * @author Jan Novotný
 * @version $Id: $
 */
public class ImmutableTag implements Serializable {
	private static final long serialVersionUID = 207179445508501356L;
	private final Integer id;
	private final String name;

	public ImmutableTag(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@SuppressWarnings({"ControlFlowStatementWithoutBraces", "RedundantIfStatement"})
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		ImmutableTag tag = (ImmutableTag)o;

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
		sb.append('}');
		return sb.toString();
	}
}
