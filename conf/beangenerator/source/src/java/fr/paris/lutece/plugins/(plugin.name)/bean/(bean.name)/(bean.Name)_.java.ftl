package ${package};

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
<#if specific_import?has_content>
<#list specific_import as import>
import ${import};
</#list>
</#if>

/**
 * The ${bean.name?cap_first} class for JPA and DAO
 * @author ${plugin.authorName}
 */
@StaticMetamodel( ${bean.name?cap_first}.class )
public class ${bean.name?cap_first}_ {

	<#list bean.attributs?keys as key>
	<#if bean.attributs[key].filter>
	public static volatile SingularAttribute<${bean.name?cap_first}, ${bean.attributs[key].type?cap_first}> _${key};
	</#if>
	</#list>

}