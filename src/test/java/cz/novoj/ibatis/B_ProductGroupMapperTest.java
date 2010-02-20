package cz.novoj.ibatis;

import cz.novoj.ibatis.infrastructure.AbstractBaseTest;
import cz.novoj.ibatis.model.product.Group;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This tests practices the same things stated in A_ProductTagMapperTest with the exception of selectKey usage that
 * in case of groups needs to be preselected from sequence.
 *
 * @author Jan Novotný
 * @version $Id: $
 */
public class B_ProductGroupMapperTest extends AbstractBaseTest {
	@Autowired
	private ProductGroupMapper productGroupMapper;

	/**
	 * Implement basic create statement in ProductGroupMapper class and ProductGroupMapper.xml config.
	 * Be aware, that group type has no autoincrement primary key, you need to preselect new unique key from sequence.
	 *
	 * @throws Exception
	 */
	@Test
	public void testCreateGroup() throws Exception {
		Group group = new Group("mojeNováSkupina", "aNovýTyp");
		productGroupMapper.createGroup(group);
		assertNotNull(group.getId());

		Group loadedGroup = productGroupMapper.getGroupById(group.getId());
		assertEquals(group.getName(), loadedGroup.getName());
		assertEquals(group.getGroupType(), loadedGroup.getGroupType());
	}

	/**
	 * Implement basic create statement in ProductGroupMapper class and ProductGroupMapper.xml config.
	 * Be aware, that null values needs to be taken care of in a special way - jdbc type needs to be specified.
	 *
	 * @throws Exception
	 */
	@Test
	public void testCreateGroupWithNullValue() throws Exception {
		Group group = new Group("mojeNováSkupina");
		productGroupMapper.createGroup(group);
		assertNotNull(group.getId());

		Group loadedGroup = productGroupMapper.getGroupById(group.getId());
		assertEquals(group.getName(), loadedGroup.getName());
		assertEquals(group.getGroupType(), loadedGroup.getGroupType());
	}

	/**
	 * Implement basic selection statement in ProductGroupMapper class and ProductGroupMapper.xml config.
	 * Nothing new here.
	 *
	 * @throws Exception
	 */
	@Test
	public void testCountGroups() throws Exception {
		assertEquals(5, productGroupMapper.countGroups());
	}

	/**
	 * Implement basic selection statement in ProductGroupMapper class and ProductGroupMapper.xml config.
	 * Nothing new here.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetGroupById() throws Exception {
		Group group = productGroupMapper.getGroupById(1);
		assertNotNull(group);
		assertEquals(1, (int)group.getId());
		assertEquals("HDD", group.getName());		
	}

	/**
	 * Implement basic update statement in ProductGroupMapper class and ProductGroupMapper.xml config.
	 * Nothing new here.
	 *
	 * @throws Exception
	 */
	@Test
	public void testUpdateGroup() throws Exception {
		Group group = productGroupMapper.getGroupById(1);
		group.setName("úplně nové jméno");
		group.setGroupType("úplně nový typ");
		productGroupMapper.updateGroup(group);

		Group loadedGroup = productGroupMapper.getGroupById(1);
		assertEquals(group.getName(), loadedGroup.getName());
		assertEquals(group.getGroupType(), loadedGroup.getGroupType());
	}

	/**
	 * Implement basic delete statement in ProductGroupMapper class and ProductGroupMapper.xml config.
	 * Nothing new here.
	 *
	 * @throws Exception
	 */
	@Test
	public void testDeleteGroup() throws Exception {
		productGroupMapper.deleteGroup(5);
		assertEquals(4, productGroupMapper.countGroups());
	}

}