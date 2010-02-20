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
 * @author Jan Novotný
 * @version $Id: $
 */
public class E_AnnotationBasedProductMapperTest extends AbstractBaseTest {
	@Autowired
	protected AnnotationBasedProductMapper annProductMapper;

	/**
	 * Implement basic selection statement in AnnotationBasedProductMapper class and AnnotationBasedProductMapper.xml config.
	 * Very simple and elegant way to implement easy queries. 
	 *
	 * @throws Exception
	 */
	@Test
	public void testCountProducts() throws Exception {
		assertEquals(18, annProductMapper.countProducts());
	}

	/**
	 * Implement basic selection statement in AnnotationBasedProductMapper class and AnnotationBasedProductMapper.xml config.
	 * Group object is expected to be loaded along with product POJOs.
	 * Annotations there gets more complicated as you need to specify more @Results and @Result mappings.
	 *
	 * @throws Exception
	 */
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

	/**
	 * Implement basic selection statement in AnnotationBasedProductMapper class and AnnotationBasedProductMapper.xml config.
	 * Product with Group object and even Tag list is expected in the result.
	 * In this case you'd need two statements as join is not supported in annotations - you'd need to specify
	 * another method to select tags in extra statement and use @Many annotations.
	 * This gets ugly comparing to XML definition.
	 *
	 * @throws Exception
	 */
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

	/**
	 * Implement basic create statement in AnnotationBasedProductMapper class and AnnotationBasedProductMapper.xml config.
	 *
	 * Please note, that you cannot get generated id from annotation based create statement unless JDBC driver supports
	 * generated keys output and you use @Option to specify which property should be populated with the key.
	 *
	 * Proposal for @SelectKey annotation was raised in issue IBATIS-642.
	 */
	@Test
	public void testCreateProduct() {
		Product product = new Product("Some new HDD", new Group(1));
		annProductMapper.createProduct(product);
		assertNull(product.getId());

		Product loadedProduct = annProductMapper.getProductByName(product.getName());
		assertEquals("Some new HDD", loadedProduct.getName());
		assertEquals("HDD", loadedProduct.getGroup().getName());
	}

	/**
	 * Implement basic selection statement in AnnotationBasedProductMapper class and AnnotationBasedProductMapper.xml config.
	 * Nothing new here.
	 */
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

	/**
	 * Implement basic selection statement in AnnotationBasedProductMapper class and AnnotationBasedProductMapper.xml config.
	 * Nothing new here.
	 */
	@Test
	public void testDeleteProduct() {
		annProductMapper.deleteProduct(1);
		assertNull(annProductMapper.getProductById(1));
	}

}