package cz.novoj.ibatis;

import cz.novoj.ibatis.infrastructure.AbstractBaseTest;
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
 * This test shows the basic usage of iBatis.
 *
 * @author Jan Novotný
 * @version $Id: $
 */
public class A_ProductTagMapperTest extends AbstractBaseTest {
	@Autowired
	private ProductTagMapper productTagMapper;

	/**
	 * Implement basic selection statement in ProductTagMapper class and ProductTagMapper.xml config.
	 * @throws Exception
	 */
	@Test
	public void testCountTags() throws Exception {
		assertEquals(12, productTagMapper.countTags());
	}

	/**
	 * Implement basic selection statement in ProductTagMapper class and ProductTagMapper.xml config.
	 * @throws Exception
	 */
	@Test
	public void testGetTagById() throws Exception {
		Tag tag = productTagMapper.getTagById(1);
		assertNotNull(tag);
		assertEquals(1, (int)tag.getId());
		assertEquals("Samsung", tag.getName());		
	}

	/**
	 * Modify selection statement to create instance of ImmutableTag in ProductTagMapper class and
	 * ProductTagMapper.xml config.
	 *
	 * In order to pass, this tests needs setting:
	 *
	 * <setting name="lazyLoadingEnabled" value="false"/>
	 *
	 * This is certainly a bug in iBatis - reported as #IBATIS-750
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetImmutableTagById() throws Exception {
		ImmutableTag tag = productTagMapper.getImmutableTagById(1);
		assertNotNull(tag);
		assertEquals(1, (int)tag.getId());
		assertEquals("Samsung", tag.getName());
	}

	/**
	 * Implement basic insert statement in ProductTagMapper class and ProductTagMapper.xml config.
	 * @throws Exception
	 */
	@Test
	public void testCreateTag() throws Exception {
		Tag tag = new Tag("můjNovýTag");
		productTagMapper.createTag(tag);
		assertNotNull(tag.getId());

		Tag loadedTag = productTagMapper.getTagById(tag.getId());
		assertEquals(tag.getName(), loadedTag.getName());
	}

	/**
	 * Implement basic update by id statement in ProductTagMapper class and ProductTagMapper.xml config.
	 * @throws Exception
	 */
	@Test
	public void testUpdateTag() throws Exception {
		Tag tag = productTagMapper.getTagById(1);
		tag.setName("úplně nové jméno");
		productTagMapper.updateTag(tag);

		Tag loadedTag = productTagMapper.getTagById(1);
		assertEquals(tag.getName(), loadedTag.getName());
	}

	/**
	 * Implement basic delete by id statement in ProductTagMapper class and ProductTagMapper.xml config.
	 * @throws Exception
	 */
	@Test
	public void testDeleteTag() throws Exception {
		int removedItems = productTagMapper.deleteTag(12);
		assertEquals(1, removedItems);
		assertEquals(11, productTagMapper.countTags());
	}

	/**
	 * Implement basic selection statement that orders rows ascending by specified column in ProductTagMapper class
	 * and ProductTagMapper.xml config.
	 *
	 * In production keep an eye from what source the name of the column comes. This could lead to SQL injection issue.
	 *
	 * @throws Exception
	 */
	@Test
	public void testListTagsOrdered() throws Exception {
		List<Tag> tags = productTagMapper.getOrderedTags("id");
		assertEquals("Samsung", tags.get(0).getName());
		List<Tag> anotherTagSet = productTagMapper.getOrderedTags("name");
		assertEquals(".NET", anotherTagSet.get(0).getName());
	}

	/**
	 * Implement following selection statement (this would be pretty difficult to achieve in Hibernate):
	 *
	 * select
     *       avg(length(name)) as averageLength,
     *       min(length(name)) as minLength,
     *       max(length(name)) as maxLength
     *   from tag
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetCalculationsInHashMap() throws Exception {
		Map<String,Object> result = productTagMapper.getAverageMinMaxLengthOfTag();
		assertFalse(result.isEmpty());
		assertEquals(6, ((Number)result.get("AVERAGELENGTH")).intValue());
		assertEquals(2, ((Number)result.get("MINLENGTH")).intValue());
		assertEquals(13, ((Number)result.get("MAXLENGTH")).intValue());
	}
}