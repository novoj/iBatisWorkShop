package cz.novoj.ibatis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * Description
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
public class ConditionalExpressionsTest extends AbstractBaseTest {
	@Autowired
	protected ProductMapper productMapper;

	@Test
	public void testCountProducts() throws Exception {
		assertEquals(18, productMapper.countProducts());
	}

}