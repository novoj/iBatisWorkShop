package cz.novoj.ibatis;

import cz.novoj.ibatis.model.product.Group;

/**
 * Contains DAO methods for product group manipulation and querying.
 *
 * @author Jan Novotný
 * @version $Id: $
 */
@SuppressWarnings({"InterfaceNeverImplemented"})
public interface ProductGroupMapper {

	void createGroup(Group group);

	void updateGroup(Group group);

	int deleteGroup(int id);

	int countGroups();

	Group getGroupById(int id);	

}
