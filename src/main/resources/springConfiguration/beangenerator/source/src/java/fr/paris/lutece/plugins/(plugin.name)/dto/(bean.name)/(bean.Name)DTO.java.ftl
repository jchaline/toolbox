package ${package};

import fr.paris.lutece.plugins.generic.dto.AbstractDTO;
<#if specific_import?has_content>
<#list specific_import as import>
import ${import};
</#list>
</#if>

/**
 * The ${bean.name?cap_first} class DTO
 * @author ${plugin.authorName}
 */
public class ${bean.name?cap_first}DTO extends AbstractDTO{
	<#list bean.attributs?keys as key>
	private	${bean.attributs[key].type?cap_first} _${key};
	</#list>
	
	<#list bean.attributs?keys as key>
	public void set${key?cap_first}(${bean.attributs[key].type?cap_first} ${key}){
		this._${key} = ${key};
	}

	public ${bean.attributs[key].type?cap_first} get${key?cap_first}(){
		return this._${key};
	}
	
	</#list>
}