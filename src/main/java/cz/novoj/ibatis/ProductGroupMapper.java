package cz.novoj.ibatis;

import cz.novoj.ibatis.model.product.Group;

/**
 * Contains DAO methods for product group manipulation and querying.
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
@SuppressWarnings({"InterfaceNeverImplemented"})
public interface ProductGroupMapper {

	void createProductGroup(Group group);

	void updateProductGroup(Group group);

	int deleteProductGroup(int id);

}
