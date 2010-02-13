package cz.novoj.ibatis;

import cz.novoj.ibatis.model.product.ImmutableTag;
import cz.novoj.ibatis.model.product.Tag;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Description
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
public class ProductTagMapperTest extends AbstractBaseTest {
	@Autowired
	private ProductTagMapper productTagMapper;

	@Test
	public void testCountTags() throws Exception {
		assertEquals(12, productTagMapper.countTags());
	}

	@Test
	public void testGetTagById() throws Exception {
		Tag tag = productTagMapper.getTagById(1);
		assertNotNull(tag);
		assertEquals(1, (int)tag.getId());
		assertEquals("Samsung", tag.getName());		
	}

	@Test
	public void testGetImmutableTagById() throws Exception {
		ImmutableTag tag = productTagMapper.getImmutableTagById(1);
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

	@Test
	public void testGetCalculationsInHashMap() throws Exception {
		Map<String,Object> result = productTagMapper.getAverageMinMaxLengthOfTag();
		assertFalse(result.isEmpty());
		assertEquals(6, ((Number)result.get("AVERAGELENGTH")).intValue());
		assertEquals(2, ((Number)result.get("MINLENGTH")).intValue());
		assertEquals(13, ((Number)result.get("MAXLENGTH")).intValue());
	}
}