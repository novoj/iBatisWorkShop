package cz.novoj.ibatis;

import cz.novoj.ibatis.model.product.Group;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Description
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
public class ProductGroupManagerTest extends AbstractBaseTest {
	@Autowired
	private ProductGroupMapper productGroupMapper;

	@Test
	public void testCountGroups() throws Exception {
		assertEquals(5, productGroupMapper.countGroups());
	}

	@Test
	public void testGetGroupById() throws Exception {
		Group group = productGroupMapper.getGroupById(1);
		assertNotNull(group);
		assertEquals(1, (int)group.getId());
		assertEquals("HDD", group.getName());		
	}

	@Test
	public void testCreateGroup() throws Exception {
		Group group = new Group("mojeNováSkupina", "aNovýTyp");
		productGroupMapper.createGroup(group);
		assertNotNull(group.getId());

		Group loadedGroup = productGroupMapper.getGroupById(group.getId());
		assertEquals(group, loadedGroup);
	}

	@Test
	public void testUpdateGroup() throws Exception {
		Group group = productGroupMapper.getGroupById(1);
		group.setName("úplně nové jméno");
		group.setGroupType("úplně nový typ");
		productGroupMapper.updateGroup(group);

		Group loadedGroup = productGroupMapper.getGroupById(1);
		assertEquals(group, loadedGroup);
	}

	@Test
	public void testDeleteTag() throws Exception {
		productGroupMapper.deleteGroup(5);
		assertEquals(4, productGroupMapper.countGroups());
	}

}