package cz.novoj.ibatis;

import cz.novoj.ibatis.model.product.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Description
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
public class ProductGroupManagerTest extends AbstractBaseTest {
	@Autowired
	private ProductMapper productMapper;

	@Test
	public void testCountProducts() throws Exception {
		assertEquals(18, productMapper.countProducts());
	}

	@Test
	public void testGetProductById() throws Exception {
		Product product = productMapper.getProductById(1);
		assertNotNull(product);
		assertEquals(1, (int)product.getId());
		assertEquals("Lenovo ThinkCentre 250GB Serial ATA Hard Disk Drive", product.getName());
		assertNull(product.getGroup());
		assertNull(product.getTags());
	}
}