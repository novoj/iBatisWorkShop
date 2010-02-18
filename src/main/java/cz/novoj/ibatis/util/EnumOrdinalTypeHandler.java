package cz.novoj.ibatis.util;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class converts enumeration type into the database form by ordinal value (and back again).
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
public abstract class EnumOrdinalTypeHandler extends BaseTypeHandler {
	private final Class type;
	private final Method values;

	protected EnumOrdinalTypeHandler(Class type) {
		this.type = type;
		try {
			this.values = type.getDeclaredMethod("values");
		} catch(Exception ex) {
			throw new IllegalArgumentException("Class " + type.getSimpleName() + " is not an enumeration class.", ex);
		}
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, ((Enum)parameter).ordinal());
	}

	@Override
	public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
		int i = rs.getInt(columnName);
		if(rs.wasNull()) {
			return null;
		} else {
			try {
				Object[] enums = (Object[])values.invoke(null);
				return enums[i];
			} catch (Exception ex) {
				throw new IllegalArgumentException(
						"Cannot convert " + i + " to " + type.getSimpleName() + " by ordinal value.", ex
				);
			}
		}
	}

	@Override
	public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		int i = cs.getInt(columnIndex);
		if(cs.wasNull()) {
			return null;
		} else {
			try {
				Object[] enums = (Object[])values.invoke(null);
				return enums[i];
			} catch (Exception ex) {
				throw new IllegalArgumentException(
						"Cannot convert " + i + " to " + type.getSimpleName() + " by ordinal value.", ex
				);
			}
		}
	}

}
