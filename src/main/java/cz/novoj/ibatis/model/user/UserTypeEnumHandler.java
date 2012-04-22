package cz.novoj.ibatis.model.user;

import org.apache.ibatis.type.EnumTypeHandler;

/**
 * This iBatis TypeHandler converts UserType object by string representation into the database form and back again.
 *
 * @author Jan Novotný
 * @version $Id: $
 */
public class UserTypeEnumHandler extends EnumTypeHandler<UserType> {

	public UserTypeEnumHandler() {
		super(UserType.class);
	}
	
}
