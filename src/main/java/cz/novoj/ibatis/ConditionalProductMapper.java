package cz.novoj.ibatis;

import cz.novoj.ibatis.model.product.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Contains DAO methods for product manipulation and querying.
 *
 * @author Jan Novotný
 * @version $Id: $
 */
@SuppressWarnings({"InterfaceNeverImplemented"})
public interface ConditionalProductMapper {

	List<Product> getProducts(@Param("titlePart") String titlePart, @Param("groupNamePart") String groupNamePart);

	List<Product> getProductsAlternativeWithTrim(@Param("titlePart") String titlePart, @Param("groupNamePart") String groupNamePart);

	List<Product> getProductsByChoose(@Param("titlePart") String titlePart, @Param("groupNamePart") String groupNamePart);

	List<Product> getProductsById(int... id);

	void selectiveUpdate(Product product);

	void selectiveUpdateAlternativeWithTrim(Product product);

}