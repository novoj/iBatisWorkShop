package cz.novoj.ibatis;

import cz.novoj.ibatis.model.user.User;

import java.util.List;

/**
 * Contains DAO methods for user manipulation and querying.
 *
 * @author Jan Novotný
 * @version $Id: $
 */
@SuppressWarnings({"InterfaceNeverImplemented"})
public interface UserMapper {
	
	User getUserById(Integer id);

	List<User> getUsers();

}
