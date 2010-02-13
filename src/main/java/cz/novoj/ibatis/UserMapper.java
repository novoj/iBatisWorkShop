package cz.novoj.ibatis;

import cz.novoj.ibatis.model.user.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Contains DAO methods for user manipulation and querying.
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
@SuppressWarnings({"InterfaceNeverImplemented"})
public interface UserMapper {

	@Select("select * from user where id = #{id}")
	User getUserById(Integer id);

	@Select("select * from user")
	List<User> getUsers();

}
