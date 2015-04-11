package ${package};

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fr.paris.lutece.plugins.${plugin.name}.bean.${bean.name}.${bean.name?cap_first};
import fr.paris.lutece.plugins.generic.bean.AbstractFilter;
import fr.paris.lutece.plugins.generic.dao.AbstractDAO;
<#if specific_import?has_content>
<#list specific_import as import>
import ${import};
</#list>
</#if>

/**
 * The ${bean.name?cap_first} DAO class
 * @author ${plugin.authorName}
 */
public class ${bean.name?cap_first}DAO extends AbstractDAO<Integer, ${bean.name?cap_first}> implements I${bean.name?cap_first}DAO<Integer, ${bean.name?cap_first}>{

	@Override
	protected void buildCriteriaQuery( AbstractFilter filter, Root<${bean.name?cap_first}> root, CriteriaQuery<${bean.name?cap_first}> query,
    	CriteriaBuilder builder )
	{
    	
	}
}