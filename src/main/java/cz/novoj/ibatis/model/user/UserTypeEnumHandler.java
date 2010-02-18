package cz.novoj.ibatis.model.user;

import org.apache.ibatis.type.EnumTypeHandler;

/**
 * This iBatis TypeHandler converts UserType object by string representation into the database form and back again.
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
public class UserTypeEnumHandler extends EnumTypeHandler {

	public UserTypeEnumHandler() {
		super(UserType.class);
	}
	
}
