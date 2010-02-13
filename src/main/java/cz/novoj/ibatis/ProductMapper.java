package cz.novoj.ibatis;

import cz.novoj.ibatis.model.product.Product;
import org.apache.ibatis.annotations.Param;

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

	Product getProductByNameAndGroup(@Param("name") String name, @Param("group") String group);

	void createProduct(Product product);

	void updateProduct(Product product);

	void deleteProduct(int id);

}
