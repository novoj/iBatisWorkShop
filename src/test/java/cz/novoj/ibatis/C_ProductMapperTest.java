package cz.novoj.ibatis;

import cz.novoj.ibatis.infrastructure.AbstractBaseTest;
import cz.novoj.ibatis.model.product.Group;
import cz.novoj.ibatis.model.product.Product;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * This test practices association and collection loading and lazy loading behaviour.
 *
 * @author Jan Novotný
 * @version $Id: $
 */
public class C_ProductMapperTest extends AbstractBaseTest {
	@Autowired
	protected ProductMapper productMapper;

	/**
	 * Implement basic selection statement in ProductMapper class and ProductMapper.xml config.
	 * Just warm up ourselves before diving into the other tests.
	 * Nothing new here.
	 *
	 * @throws Exception
	 */
	@Test
	public void testCountProducts() throws Exception {
		assertEquals(18, productMapper.countProducts());
	}

	/**
	 * Implement basic selection statement in ProductMapper class and ProductMapper.xml config.
	 * Product with Group object is expected in the result, tags are not yet needed.
	 * Group is expected to be loaded lazily - with one extra select.
	 *
	 * With setting aggressiveLazyLoading = true (default) objects are not loaded lazily but completely when they are
	 * first touched. In opposite setting objects are loaded one by one when touching appropriate property.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetLazyProductById() throws Exception {
		Product product = productMapper.getLazyProductById(1);
		assertNotNull(product);
		assertEquals(1, (int)product.getId());
		assertEquals("Lenovo ThinkCentre 250GB Serial ATA Hard Disk Drive", product.getName());
		assertNotNull(product.getGroup());
		assertEquals("HDD", product.getGroup().getName());
		assertEquals("HARDWARE", product.getGroup().getGroupType());
		assertNull(product.getTags());
	}

	/**
	 * Implement basic selection statement in ProductMapper class and ProductMapper.xml config.
	 * Product with Group object and with Tag listing is expected in the result.
	 * Group and tags are expected to be loaded lazily - with one extra select.
	 *
	 * With setting aggressiveLazyLoading = true (default) objects are not loaded lazily but completely when they are
	 * first touched. In opposite setting objects are loaded one by one when touching appropriate property.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetFullLazyProductById() throws Exception {
		Product product = productMapper.getFullLazyProductById(1);
		assertNotNull(product);
		assertEquals(1, (int)product.getId());
		assertEquals("Lenovo ThinkCentre 250GB Serial ATA Hard Disk Drive", product.getName());
		assertNotNull(product.getGroup());
		assertEquals("HDD", product.getGroup().getName());
		assertEquals("HARDWARE", product.getGroup().getGroupType());
		assertNotNull(product.getTags());
		assertEquals(2, product.getTags().size());
		assertEquals("Lenovo", product.getTags().get(0).getName());
		assertEquals("SATA", product.getTags().get(1).getName());
	}

	/**
	 * Implement basic selection statement in ProductMapper class and ProductMapper.xml config.
	 * Group object is expected to be loaded along with product POJOs.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetProducts() throws Exception {
		List<Product> products = productMapper.getProducts();
		assertEquals(18, products.size());
		for(Product product : products) {
			assertNotNull(product);
			assertNotNull(product.getGroup());
		}
	}

	/**
	 * Implement basic selection statement in ProductMapper class.
	 * Use iBatis Paging facility to crop results from 5th record to the 7th.
	 * See, we don't even need to make a new statement in the ProductMapper.xml config.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetProductsRowBounds() throws Exception {
		List<Product> products = productMapper.getProducts(new RowBounds(5, 2));
		assertEquals(2, products.size());
		for(Product product : products) {
			assertNotNull(product);
			assertNotNull(product.getGroup());
		}
	}

	/**
	 * Implement basic selection statement in ProductMapper class and ProductMapper.xml config.
	 * Product with Group object is expected in the result, tags are not yet needed.
	 * Implement logic via SQL join.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetProductById() throws Exception {
		Product product = productMapper.getProductById(1);
		assertNotNull(product);
		assertEquals(1, (int)product.getId());
		assertEquals("Lenovo ThinkCentre 250GB Serial ATA Hard Disk Drive", product.getName());
		assertNotNull(product.getGroup());
		assertEquals("HDD", product.getGroup().getName());
		assertEquals("HARDWARE", product.getGroup().getGroupType());
		assertNull(product.getTags());
	}

	/**
	 * Implement basic selection statement in ProductMapper class and ProductMapper.xml config.
	 * Product with Group object and even Tag list is expected in the result.
	 * Implement logic via SQL join.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetFullProductById() throws Exception {
		Product product = productMapper.getFullProductById(1);
		assertNotNull(product);
		assertEquals(1, (int)product.getId());
		assertEquals("Lenovo ThinkCentre 250GB Serial ATA Hard Disk Drive", product.getName());
		assertNotNull(product.getGroup());
		assertEquals("HDD", product.getGroup().getName());
		assertEquals("HARDWARE", product.getGroup().getGroupType());
		assertNotNull(product.getTags());
		assertEquals(2, product.getTags().size());
		assertEquals("Lenovo", product.getTags().get(0).getName());
		assertEquals("SATA", product.getTags().get(1).getName());
	}

	/**
	 * Implement basic selection statement in ProductMapper class and ProductMapper.xml config.
	 * Pass multiple arguments to the query statement. User @Param annotations to name them or use indexed var names.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetProductByNameAndGroup() throws Exception {
		Product product = productMapper.getProductByNameAndGroup("Lenovo ThinkPad 64GB Solid State Disk", "HDD");
		assertNotNull(product);
	}

	/**
	 * Implement basic create statement in ProductMapper class and ProductMapper.xml config.
	 * Nothing new here.
	 */
	@Test
	public void testCreateProduct() {
		Product product = new Product("Some new HDD", new Group(1));
		productMapper.createProduct(product);
		assertNotNull(product.getId());

		Product loadedProduct = productMapper.getProductById(product.getId());
		assertEquals("Some new HDD", loadedProduct.getName());
		assertEquals("HDD", loadedProduct.getGroup().getName());
	}

	/**
	 * Implement basic selection statement in ProductMapper class and ProductMapper.xml config.
	 * Nothing new here.
	 */
	@Test
	public void testUpdateProduct() {
		Product product = productMapper.getProductById(1);
		product.setName("Different name");
		product.setGroup(new Group(3));
		productMapper.updateProduct(product);

		Product loadedProduct = productMapper.getProductById(1);
		assertEquals("Different name", loadedProduct.getName());
		assertEquals("Monitory", loadedProduct.getGroup().getName());
	}

	/**
	 * Implement basic selection statement in ProductMapper class and ProductMapper.xml config.
	 * Nothing new here.
	 */
	@Test
	public void testDeleteProduct() {
		productMapper.deleteProduct(1);
		assertNull(productMapper.getProductById(1));
	}

}