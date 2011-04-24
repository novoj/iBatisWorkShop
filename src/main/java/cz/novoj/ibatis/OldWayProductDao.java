package cz.novoj.ibatis;

import cz.novoj.ibatis.model.product.Product;
import org.mybatis.spring.support.SqlSessionDaoSupport;

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
		return (Integer)getSqlSession().selectOne(appendPrefix("countProducts"));
	}

	public Product getProductById(int id) {
		return (Product)getSqlSession().selectOne(appendPrefix("getProductById"), id);
	}


	public Product getProductByNameAndGroup(String name, String group) {
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("name", name);
		params.put("group", group);
		return (Product)getSqlSession().selectOne(appendPrefix("getProductByNameAndGroup"), params);
	}


	public void createProduct(Product product) {
		getSqlSession().insert(appendPrefix("createProduct"), product);
	}


	public void updateProduct(Product product) {
		getSqlSession().update(appendPrefix("updateProduct"), product);
	}


	public void deleteProduct(int id) {
		getSqlSession().delete(appendPrefix("deleteProduct"), id);
	}

	private String appendPrefix(String statementName) {
		return "cz.novoj.ibatis.ProductMapper." + statementName;
	}

}
