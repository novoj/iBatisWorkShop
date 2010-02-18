package cz.novoj.ibatis;

import cz.novoj.ibatis.model.product.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Contains DAO methods for product manipulation and querying.
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
@SuppressWarnings({"InterfaceNeverImplemented"})
public interface ProductMapper {	
	
	int countProducts();

	List<Product> getProducts();
	
	List<Product> getProducts(RowBounds bounds);	

	Product getProductById(int id);

	Product getLazyProductById(int id);

	Product getFullProductById(int id);

	Product getFullLazyProductById(int id);

	Product getProductByNameAndGroup(@Param("name") String name, @Param("group") String group);

	void createProduct(Product product);

	void updateProduct(Product product);

	void deleteProduct(int id);

}
