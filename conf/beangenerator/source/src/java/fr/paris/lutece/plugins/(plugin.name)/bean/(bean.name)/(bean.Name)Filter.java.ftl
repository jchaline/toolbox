package ${package};

import fr.paris.lutece.plugins.generic.bean.AbstractFilter;
<#if specific_import?has_content>
<#list specific_import as import>
import ${import};
</#list>
</#if>

/**
 * The ${bean.name?cap_first} class for filter
 * @author ${plugin.authorName}
 */
public class ${bean.name?cap_first}Filter extends AbstractFilter {

	<#list bean.attributs?keys as key>
	<#if bean.attributs[key].filter>
		private	${bean.attributs[key].type?cap_first} _${key};
	</#if>
	</#list>
	
	<#list bean.attributs?keys as key>
	<#if bean.attributs[key].filter >
	public void set${key?cap_first}(${bean.attributs[key].type?cap_first} ${key}){
		this._${key} = ${key};
	}

	public ${bean.attributs[key].type?cap_first} get${key?cap_first}(){
		return this._${key};
	}
	</#if>
	</#list>

}