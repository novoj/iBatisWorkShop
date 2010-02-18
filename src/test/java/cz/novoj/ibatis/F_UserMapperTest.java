package cz.novoj.ibatis;

import cz.novoj.ibatis.infrastructure.AbstractBaseTest;
import cz.novoj.ibatis.model.user.Employee;
import cz.novoj.ibatis.model.user.ExternalUser;
import cz.novoj.ibatis.model.user.User;
import cz.novoj.ibatis.model.user.UserState;
import cz.novoj.ibatis.model.user.UserType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Description
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
public class F_UserMapperTest extends AbstractBaseTest {
	@Autowired
	private UserMapper userMapper;

	@Test
	public void testGetUserByIdEmployee() throws Exception {
		User user = userMapper.getUserById(1);
		assertNotNull(user);
		assertEquals("novoj", user.getLogin());
		assertTrue(user instanceof Employee);
		assertEquals(UserState.ENABLED, user.getState());
		assertEquals(UserType.EMPLOYEE, user.getType());
		assertEquals("00012", ((Employee)user).getEmployeeNumber());
	}

	@Test
	public void testGetUserByIdExternalUser() throws Exception {
		User user = userMapper.getUserById(4);
		assertNotNull(user);
		assertEquals("rodrigez", user.getLogin());
		assertTrue(user instanceof ExternalUser);
		assertEquals(UserState.DISABLED, user.getState());
		assertEquals(UserType.EXTERNAL, user.getType());
		assertEquals("129837281", ((ExternalUser)user).getCompanyIdNumber());
		assertEquals("Fischer Scientific", ((ExternalUser)user).getCompanyName());
	}

	@Test
	public void testGetUsers() throws Exception {
		List<User> users = userMapper.getUsers();
		assertEquals(10, users.size());
	}
}
