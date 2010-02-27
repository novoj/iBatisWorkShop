package cz.novoj.ibatis;

import cz.novoj.ibatis.model.product.Product;
import cz.novoj.ibatis.model.product.Tag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Contains DAO methods for product manipulation and querying.
 *
 * @author Jan Novotný
 * @version $Id: $
 */
@SuppressWarnings({"InterfaceNeverImplemented"})
public interface AnnotationBasedProductMapper {

	@Select("select count(0) from product")
	int countProducts();

	@Select(
			"select product.* , productGroup.name as groupName, productGroup.groupType" +
			" from product inner join productGroup on idGroup = productGroup.id" +
			" where id = #{id}"
	)
	@Results({
		@Result(property = "id", id = true),
		@Result(property = "name"),
		@Result(property = "group.id", column = "idGroup"),
		@Result(property = "group.name", column = "groupName"),
		@Result(property = "group.groupType", column = "groupType")
	})
	Product getProductById(int id);

	@Select(
			"select product.*, product.id as idProduct, productGroup.name as groupName, productGroup.groupType" +
			" from product inner join productGroup on idGroup = productGroup.id" +
			" where id = #{id}"
	)
	@Results({
		@Result(property = "id", id = true),
		@Result(property = "name"),
		@Result(property = "group.id", column = "idGroup", id = true),
		@Result(property = "group.name", column = "groupName"),
		@Result(property = "group.groupType", column = "groupType"),
		@Result(property = "tags", column = "idProduct",
				many = @Many(select = "cz.novoj.ibatis.AnnotationBasedProductMapper.getTagsForProduct")
		)
	})
	Product getFullProductById(int id);

	@Select("select tag.id, tag.name" +
			 " from tag inner join productTag on productTag.idTag = tag.id" +
			 " where productTag.idProduct = #{value}"
	)
	List<Tag> getTagsForProduct(int idProduct);

	@Select(
			"select product.* , productGroup.name as groupName, productGroup.groupType" +
			" from product inner join productGroup on idGroup = productGroup.id" +
			" where name = #{value}"
	)
	@Results({
		@Result(property = "id", id = true),
		@Result(property = "name"),
		@Result(property = "group.id", column = "idGroup"),
		@Result(property = "group.name", column = "groupName"),
		@Result(property = "group.groupType", column = "groupType")
	})
	Product getProductByName(String name);


	@Insert("insert into product (name, idGroup) values (#{name}, #{group.id})")
	void createProduct(Product product);

	@Update("update product" +
			" set name = #{name}, idGroup = #{group.id}" +
			" where id = #{id}"
	)
	void updateProduct(Product product);

	@Delete("delete from product where id = #{id}")
	void deleteProduct(int id);

}