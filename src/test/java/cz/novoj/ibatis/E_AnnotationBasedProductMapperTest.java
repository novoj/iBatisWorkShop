package cz.novoj.ibatis;

import cz.novoj.ibatis.infrastructure.AbstractBaseTest;
import cz.novoj.ibatis.model.product.Group;
import cz.novoj.ibatis.model.product.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * This test contains same tests as C_ProductMapperTest, but the business logic should be rewritten by using
 * iBatis annotations instead of XML declaration. 
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
public class E_AnnotationBasedProductMapperTest extends AbstractBaseTest {
	@Autowired
	protected AnnotationBasedProductMapper annProductMapper;

	@Test
	public void testCountProducts() throws Exception {
		assertEquals(18, annProductMapper.countProducts());
	}

	@Test
	public void testGetProductById() throws Exception {
		Product product = annProductMapper.getProductById(1);
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
		Product product = annProductMapper.getFullProductById(1);
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
	public void testCreateProduct() {
		Product product = new Product("Some new HDD", new Group(1));
		annProductMapper.createProduct(product);
		assertNull(product.getId());

		Product loadedProduct = annProductMapper.getProductByName(product.getName());
		assertEquals("Some new HDD", loadedProduct.getName());
		assertEquals("HDD", loadedProduct.getGroup().getName());
	}

	@Test
	public void testUpdateProduct() {
		Product product = annProductMapper.getProductById(1);
		product.setName("Different name");
		product.setGroup(new Group(3));
		annProductMapper.updateProduct(product);

		Product loadedProduct = annProductMapper.getProductById(1);
		assertEquals("Different name", loadedProduct.getName());
		assertEquals("Monitory", loadedProduct.getGroup().getName());
	}

	@Test
	public void testDeleteProduct() {
		annProductMapper.deleteProduct(1);
		assertNull(annProductMapper.getProductById(1));
	}

}