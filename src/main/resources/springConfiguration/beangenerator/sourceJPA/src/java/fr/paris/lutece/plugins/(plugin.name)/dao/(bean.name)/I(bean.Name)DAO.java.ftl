package ${package};

import fr.paris.lutece.plugins.genericjpa.dao.IGenericJPADAO;
<#if specific_import?has_content>
<#list specific_import as import>
import ${import};
</#list>
</#if>

/**
 * The ${bean.name?cap_first} interface for DAO
 * @author ${plugin.authorName}
 */
public interface I${bean.name?cap_first}DAO<K,E> extends IGenericJPADAO<K, E> {
	

}
