package cz.novoj.ibatis;

import org.apache.ibatis.annotations.Select;

/**
 * Description
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
@SuppressWarnings({"InterfaceNeverImplemented"})
public interface ProductManager {

	@Select("select count(*) from product")
	int countProducts();

}
