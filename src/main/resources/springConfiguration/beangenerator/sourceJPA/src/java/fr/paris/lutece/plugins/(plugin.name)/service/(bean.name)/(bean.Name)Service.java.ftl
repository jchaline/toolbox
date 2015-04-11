package ${package};

import javax.inject.Inject;
import javax.inject.Named;

import fr.paris.lutece.plugins.${plugin.name}.dao.${bean.name}.I${bean.name?cap_first}DAO;
import fr.paris.lutece.plugins.genericjpa.service.GenericJPAService;
<#if specific_import?has_content>
<#list specific_import as import>
import ${import};
</#list>
</#if>

/**
 * The ${bean.name?cap_first} class service
 * @author ${plugin.authorName}
 */
public class ${bean.name?cap_first}Service extends GenericJPAService implements I${bean.name?cap_first}Service {

	@Inject
	@Named("${plugin.name}.${bean.name}DAO")
	private I${bean.name?cap_first}DAO _dao${bean.name?cap_first};
	
	@Override
	protected GenericJPADAO getDAO() {
		return (GenericJPADAO) _dao${bean.name?cap_first};
	}


}