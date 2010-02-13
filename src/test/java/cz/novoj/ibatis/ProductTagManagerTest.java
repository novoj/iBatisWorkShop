package cz.novoj.ibatis;

import cz.novoj.ibatis.model.product.Tag;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Description
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
public class ProductTagManagerTest extends AbstractBaseTest {
	@Autowired
	private ProductTagMapper productTagMapper;

	@Test
	public void testCountProductTags() throws Exception {
		assertEquals(12, productTagMapper.countTags());
	}

	@Test
	public void testGetProductById() throws Exception {
		Tag tag = productTagMapper.getTagById(1);
		assertNotNull(tag);
		assertEquals(1, (int)tag.getId());
		assertEquals("Samsung", tag.getName());		
	}

	@Test
	public void testCreateTag() throws Exception {
		Tag tag = new Tag("můjNovýTag");
		productTagMapper.createTag(tag);
		assertNotNull(tag.getId());

		Tag loadedTag = productTagMapper.getTagById(tag.getId());
		assertEquals(tag, loadedTag);
	}

	@Test
	public void testUpdateTag() throws Exception {
		Tag tag = productTagMapper.getTagById(1);
		tag.setName("úplně nové jméno");
		productTagMapper.updateTag(tag);

		Tag loadedTag = productTagMapper.getTagById(1);
		assertEquals(tag, loadedTag);
	}

	@Test
	public void testDeleteTag() throws Exception {
		productTagMapper.deleteTag(12);
		assertEquals(11, productTagMapper.countTags());
	}

	@Test
	public void testListTagsOrdered() throws Exception {
		List<Tag> tags = productTagMapper.getOrderedTags("id");
		assertEquals("Samsung", tags.get(0).getName());
		List<Tag> anotherTagSet = productTagMapper.getOrderedTags("name");
		assertEquals(".NET", anotherTagSet.get(0).getName());
	}
}