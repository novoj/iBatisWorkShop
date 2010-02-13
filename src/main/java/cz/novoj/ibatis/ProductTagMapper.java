package cz.novoj.ibatis;

import cz.novoj.ibatis.model.product.Tag;

/**
 * Contains DAO methods for product tag manipulation and querying.
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
@SuppressWarnings({"InterfaceNeverImplemented"})
public interface ProductTagMapper {

	void createProductTag(Tag tag);

	void updateProductTag(Tag tag);

	int deleteTag(int id);

	int countTags();

	Tag getTagById(int id);
	
}
