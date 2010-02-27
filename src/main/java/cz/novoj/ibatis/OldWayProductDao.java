package cz.novoj.ibatis;

import cz.novoj.ibatis.model.product.Product;
import org.springframework.orm.ibatis3.support.SqlSessionDaoSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * Example class showing the old way of manipulating iBatis from Spring.
 *
 * @author Jan Novotný
 * @version $Id: $
 */
public class OldWayProductDao extends SqlSessionDaoSupport {


	public int countProducts() {
		return (Integer)getSqlSessionTemplate().selectOne(appendPrefix("countProducts"));
	}

	public Product getProductById(int id) {
		return (Product)getSqlSessionTemplate().selectOne(appendPrefix("getProductById"), id);
	}


	public Product getProductByNameAndGroup(String name, String group) {
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("name", name);
		params.put("group", group);
		return (Product)getSqlSessionTemplate().selectOne(appendPrefix("getProductByNameAndGroup"), params);
	}


	public void createProduct(Product product) {
		getSqlSessionTemplate().insert(appendPrefix("createProduct"), product);
	}


	public void updateProduct(Product product) {
		getSqlSessionTemplate().update(appendPrefix("updateProduct"), product);
	}


	public void deleteProduct(int id) {
		getSqlSessionTemplate().delete(appendPrefix("deleteProduct"), id);
	}

	private String appendPrefix(String statementName) {
		return "cz.novoj.ibatis.ProductMapper." + statementName;
	}

}
