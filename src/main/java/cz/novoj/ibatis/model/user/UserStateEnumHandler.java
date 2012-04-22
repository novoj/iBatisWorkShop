package cz.novoj.ibatis.model.user;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

/**
 * This iBatis TypeHandler converts UserType object by ordinal representation into the database form and back again.
 *
 * @author Jan Novotný
 * @version $Id: $
 */
public class UserStateEnumHandler extends EnumOrdinalTypeHandler<UserState> {

	public UserStateEnumHandler() {
		super(UserState.class);
	}

}