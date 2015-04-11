<#list plugin.beanList as bean>
DROP TABLE IF EXISTS ${plugin.name}_${bean.name};
CREATE TABLE IF NOT EXISTS ${bean.name}_${bean.name} (	
	<#list bean.attributs?keys as key>
		${key} ${bean.attributs[key].type},
	</#list>
	PRIMARY KEY  (id)
)COMMENT='Contient les ${bean.name}s';
</#list>
