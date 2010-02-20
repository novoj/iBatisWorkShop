package cz.novoj.ibatis;

import cz.novoj.ibatis.infrastructure.AbstractBaseTest;
import cz.novoj.ibatis.model.product.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * This test shows how to write easily conditional statement with iBatis. This part of iBatis has changed a lot since
 * 2nd version. And for better too.
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
public class D_ConditionalExpressionsTest extends AbstractBaseTest {
	@Autowired
	protected ConditionalProductMapper cndProductMapper;
	@Autowired
	protected ProductMapper productMapper;

	/**
	 * Implement basic selection statement in ConditionalProductMapper class and ConditionalProductMapper.xml config.
	 * Write a conditional count statement that counts product that contains in name or groupName specific string.
	 * Use <where> and <if> keywords.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetProducts() throws Exception {
		assertEquals(2, cndProductMapper.getProducts("%Samsung%", "%HDD%").size());
		assertEquals(5, cndProductMapper.getProducts(null, "%HDD%").size());
		assertEquals(4, cndProductMapper.getProducts("%Samsung%", null).size());
		assertEquals(18, cndProductMapper.getProducts(null, null).size());
	}

	/**
	 * Rewrite previous statement avoiding <where> keyword in the statement - to see that <where> is only specific
	 * derivation of <trim> keyword.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetProductsAlternativeWithTrim() throws Exception {
		assertEquals(2, cndProductMapper.getProductsAlternativeWithTrim("%Samsung%", "%HDD%").size());
		assertEquals(5, cndProductMapper.getProductsAlternativeWithTrim(null, "%HDD%").size());
		assertEquals(4, cndProductMapper.getProductsAlternativeWithTrim("%Samsung%", null).size());
		assertEquals(18, cndProductMapper.getProducts(null, null).size());
	}

	/**
	 * Implement basic selection statement in ConditionalProductMapper class and ConditionalProductMapper.xml config.
	 * Write a conditional count statement that counts product that contains in name or groupName specific string.
	 * Let say that only one of the conditions will apply at the time. Priority is driven by argument position in the
	 * signature. When no conditions is set list only product of the group with id = 1.
	 * Use <choose>, <when> and <otherwise> keywords.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetProductsByChoose() throws Exception {
		assertEquals(4, cndProductMapper.getProductsByChoose("%Samsung%", "%HDD%").size());
		assertEquals(5, cndProductMapper.getProductsByChoose(null, "%HDD%").size());
		assertEquals(4, cndProductMapper.getProductsByChoose("%Samsung%", null).size());
		assertEquals(5, cndProductMapper.getProductsByChoose(null, null).size());
	}

	/**
	 * Implement basic selection statement in ConditionalProductMapper class and ConditionalProductMapper.xml config.
	 * Load multiple products at the time by specifying their ids - would trigger need of in (?, ?, ?) in SQL statement.
	 * Use <foreach> keyword.
	 *
	 * Notice, that varargs are not yet supported due to bug reported as IBATIS-748.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetProductsById() throws Exception {
		List<Product> products = cndProductMapper.getProductsById(new Integer[] {1, 2, 3});
		assertEquals(3, products.size());
		assertEquals("Lenovo ThinkCentre 250GB Serial ATA Hard Disk Drive", products.get(0).getName());
		assertEquals("Samsung HDD 750GB Samsung SpinPointF1 32MB SATAII RAID 3RZ", products.get(1).getName());
		assertEquals("Samsung HDD 160GB Samsung SpinPoint F1 SATAII/300 3RZ", products.get(2).getName());
	}

	/**
	 * Implement basic update statement in ConditionalProductMapper class and ConditionalProductMapper.xml config.
	 * Update only those properties that are specified - let's say we do not want to touch group settings if group is
	 * not specified.
	 * Use <set> and <if> keywords.
	 */
	@Test
	public void testSelectiveUpdate() {
		Product product = productMapper.getProductById(1);
		product.setName("Different name");
		product.setGroup(null);
		cndProductMapper.selectiveUpdate(product);

		Product loadedProduct = productMapper.getProductById(1);
		assertEquals("Different name", loadedProduct.getName());
		assertEquals("HDD", loadedProduct.getGroup().getName());
	}

	/**
	 * Rewrite previous statement avoiding <set> keyword in the statement - to see that <set> is only specific
	 * derivation of <trim> keyword.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSelectiveUpdateAlternativeWithTrim() {
		Product product = productMapper.getProductById(1);
		product.setName("Different name");
		product.setGroup(null);
		cndProductMapper.selectiveUpdateAlternativeWithTrim(product);

		Product loadedProduct = productMapper.getProductById(1);
		assertEquals("Different name", loadedProduct.getName());
		assertEquals("HDD", loadedProduct.getGroup().getName());
	}



}