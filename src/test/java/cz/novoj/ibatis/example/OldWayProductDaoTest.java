package cz.novoj.ibatis.example;

import cz.novoj.ibatis.OldWayProductDao;
import cz.novoj.ibatis.infrastructure.AbstractBaseTest;
import cz.novoj.ibatis.model.product.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Description
 *
 * @author Jan Novotný
 * @version $Id: $
 */
public class OldWayProductDaoTest extends AbstractBaseTest {
	@Autowired
	private OldWayProductDao oldWayProductDao;

	@Test
	public void testCountProducts() throws Exception {
		assertEquals(18, oldWayProductDao.countProducts());
	}

	@Test
	public void testGetProductById() throws Exception {
		Product product = oldWayProductDao.getProductById(1);
		assertNotNull(product);
		assertEquals(1, (int)product.getId());
		assertEquals("Lenovo ThinkCentre 250GB Serial ATA Hard Disk Drive", product.getName());
		assertNotNull(product.getGroup());
		assertEquals("HDD", product.getGroup().getName());
		assertNull(product.getTags());
	}

	@Test
	public void testGetProductByNameAndGroup() throws Exception {
		Product product = oldWayProductDao.getProductByNameAndGroup("Lenovo ThinkPad 64GB Solid State Disk", "HDD");
		assertNotNull(product);
	}

}
