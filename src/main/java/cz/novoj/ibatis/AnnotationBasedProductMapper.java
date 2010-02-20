package cz.novoj.ibatis;

import cz.novoj.ibatis.model.product.Product;
import cz.novoj.ibatis.model.product.Tag;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Contains DAO methods for product manipulation and querying.
 *
 * @author Jan Novotný
 * @version $Id: $
 */
@SuppressWarnings({"InterfaceNeverImplemented"})
public interface AnnotationBasedProductMapper {

	int countProducts();

	Product getProductById(int id);

	Product getFullProductById(int id);

	@Select("select tag.id, tag.name" +
			 " from tag inner join productTag on productTag.idTag = tag.id" +
			 " where productTag.idProduct = #{value}"
	)
	List<Tag> getTagsForProduct(int idProduct);

	Product getProductByName(String name);

	void createProduct(Product product);

	void updateProduct(Product product);

	void deleteProduct(int id);

}