package cz.novoj.ibatis;

import cz.novoj.ibatis.model.product.ImmutableTag;
import cz.novoj.ibatis.model.product.Tag;

import java.util.List;
import java.util.Map;

/**
 * Contains DAO methods for product tag manipulation and querying.
 *
 * @author Jan Novotný
 * @version $Id: $
 */
@SuppressWarnings({"InterfaceNeverImplemented"})
public interface ProductTagMapper {

	void createTag(Tag tag);

	void updateTag(Tag tag);

	int deleteTag(int id);

	int countTags();

	Tag getTagById(int id);

	ImmutableTag getImmutableTagById(int id);

	List<Tag> getOrderedTags(String column);

	Map<String, Object> getAverageMinMaxLengthOfTag();

}
