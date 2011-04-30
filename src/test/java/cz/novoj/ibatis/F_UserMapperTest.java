package cz.novoj.ibatis;

import cz.novoj.ibatis.infrastructure.AbstractBaseTest;
import cz.novoj.ibatis.model.product.Product;
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
import static org.junit.Assert.fail;

/**
 * This test would require using two enum TypeHandlers - for converting UserState and UserType enums. One gets converted
 * by value to varchar column, second by ordinal value to integer column. Next challenge is to use discriminator facility
 * to load different types of user POJOs as User class is made abstract. 
 *
 * @author Jan Novotný
 * @version $Id: $
 */
public class F_UserMapperTest extends AbstractBaseTest {
	@Autowired
	private UserMapper userMapper;

	/**
	 * Implement basic selection statement in UserMapper class and UserMapper.xml config.
	 * User is expected to be Employee instance with state = enabled.
	 *
	 * @throws Exception
	 */
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

	/**
	 * Implement basic selection statement in UserMapper class and UserMapper.xml config.
	 * User is expected to be ExternalUser instance with state = disabled.
	 *
	 * @throws Exception
	 */
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
		assertEquals(0, user.getBelongings().size());
	}

	/**
	 * Implement basic selection statement in UserMapper class and UserMapper.xml config.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetUsers() throws Exception {
		List<User> users = userMapper.getUsers();
		assertEquals(10, users.size());
		int employees = 0;
		int externalUsers = 0;
		for(User user : users) {
			for(Product product : user.getBelongings()) {
				System.out.println(product.getId() + " - " + product.getName() + "\n");
			}
			if (user instanceof Employee) {
				employees++;
			} else if (user instanceof ExternalUser) {
				externalUsers++;
			} else {
				fail("No third type of user supported?!");
			}
		}
		assertEquals(5, employees);
		assertEquals(5, externalUsers);
	}
}
