package ${package};

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import fr.paris.lutece.plugins.genericjpa.bean.GenericJPABean;
<#if specific_import?has_content>
<#list specific_import as import>
import ${import};
</#list>
</#if>

/**
 * The ${bean.name?cap_first} Bean
 * @author ${plugin.authorName}
 */
@Entity
@Table(name = "${plugin.name}_${bean.name}")
public class ${bean.name?cap_first} extends GenericJPABean<Integer> {
	<#list bean.attributs?keys as key>
	@Column(name = "${key}")
	private	${bean.attributs[key].type?cap_first} _${key};
	
	</#list>
	
	<#list bean.attributs?keys as key>
	/**
	 * the ${key} setter
	 * @param ${key} the ${key} to set
	 */
	public void set${key?cap_first}(${bean.attributs[key].type?cap_first} ${key}){
		this._${key} = ${key};
	}

	/**
	 * the ${key} getter
	 * @return ${key}
	 */
	public ${bean.attributs[key].type?cap_first} get${key?cap_first}(){
		return this._${key};
	}
	
	</#list>
}