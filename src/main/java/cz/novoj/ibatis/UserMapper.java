package cz.novoj.ibatis;

import cz.novoj.ibatis.model.User;
import org.apache.ibatis.annotations.Select;

/**
 * Contains DAO methods for user manipulation and querying.
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
public interface UserMapper {

	@Select("select * from user where id = #{id}")
	User getUserById(Integer id);

}
