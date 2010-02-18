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
 * Description
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
public class C_ProductMapperTest extends AbstractBaseTest {
	@Autowired
	protected ProductMapper productMapper;

	@Test
	public void testCountProducts() throws Exception {
		assertEquals(18, productMapper.countProducts());
	}

	@Test
	public void testGetProducts() throws Exception {
		List<Product> products = productMapper.getProducts();
		assertEquals(18, products.size());
	}

	@Test
	public void testGetProductsRowBounds() throws Exception {
		List<Product> products = productMapper.getProducts(new RowBounds(5, 2));
		assertEquals(2, products.size());
	}

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

	@Test
	public void testGetProductByNameAndGroup() throws Exception {
		Product product = productMapper.getProductByNameAndGroup("Lenovo ThinkPad 64GB Solid State Disk", "HDD");
		assertNotNull(product);
	}

	@Test
	public void testCreateProduct() {
		Product product = new Product("Some new HDD", new Group(1));
		productMapper.createProduct(product);
		assertNotNull(product.getId());

		Product loadedProduct = productMapper.getProductById(product.getId());
		assertEquals("Some new HDD", loadedProduct.getName());
		assertEquals("HDD", loadedProduct.getGroup().getName());
	}

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

	@Test
	public void testDeleteProduct() {
		productMapper.deleteProduct(1);
		assertNull(productMapper.getProductById(1));
	}

}