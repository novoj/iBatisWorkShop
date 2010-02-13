package cz.novoj.ibatis;

import cz.novoj.ibatis.model.product.Product;

/**
 * Contains DAO methods for product manipulation and querying.
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
@SuppressWarnings({"InterfaceNeverImplemented"})
public interface ProductMapper {	
	
	int countProducts();

	Product getProductById(int id);

}
