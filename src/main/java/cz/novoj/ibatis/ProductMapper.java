package cz.novoj.ibatis;

/**
 * Description
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
@SuppressWarnings({"InterfaceNeverImplemented"})
public interface ProductMapper {

	/**@Select("select count(*) from product")**/
	int countProducts();

	

}
