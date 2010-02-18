package cz.novoj.ibatis;

import cz.novoj.ibatis.model.product.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Description
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
public class ConditionalExpressionsTest extends AbstractBaseTest {
	@Autowired
	protected ConditionalProductMapper cndProductMapper;
	@Autowired
	protected ProductMapper productMapper;

	@Test
	public void testCountProducts() throws Exception {
		assertEquals(2, cndProductMapper.getProducts("%Samsung%", "%HDD%").size());
		assertEquals(5, cndProductMapper.getProducts(null, "%HDD%").size());
		assertEquals(4, cndProductMapper.getProducts("%Samsung%", null).size());
		assertEquals(18, cndProductMapper.getProducts(null, null).size());
	}

	@Test
	public void testCountProductsAlternativeWithTrim() throws Exception {
		assertEquals(2, cndProductMapper.getProductsAlternativeWithTrim("%Samsung%", "%HDD%").size());
		assertEquals(5, cndProductMapper.getProductsAlternativeWithTrim(null, "%HDD%").size());
		assertEquals(4, cndProductMapper.getProductsAlternativeWithTrim("%Samsung%", null).size());
		assertEquals(18, cndProductMapper.getProducts(null, null).size());
	}

	@Test
	public void testCountProductsByChoose() throws Exception {
		assertEquals(4, cndProductMapper.getProductsByChoose("%Samsung%", "%HDD%").size());
		assertEquals(5, cndProductMapper.getProductsByChoose(null, "%HDD%").size());
		assertEquals(4, cndProductMapper.getProductsByChoose("%Samsung%", null).size());
		assertEquals(5, cndProductMapper.getProductsByChoose(null, null).size());
	}

	@Test
	public void testGetProductsById() throws Exception {
		List<Product> products = cndProductMapper.getProductsById(new Integer[] {1, 2, 3});
		assertEquals(3, products.size());
		assertEquals("Lenovo ThinkCentre 250GB Serial ATA Hard Disk Drive", products.get(0).getName());
		assertEquals("Samsung HDD 750GB Samsung SpinPointF1 32MB SATAII RAID 3RZ", products.get(1).getName());
		assertEquals("Samsung HDD 160GB Samsung SpinPoint F1 SATAII/300 3RZ", products.get(2).getName());
	}

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