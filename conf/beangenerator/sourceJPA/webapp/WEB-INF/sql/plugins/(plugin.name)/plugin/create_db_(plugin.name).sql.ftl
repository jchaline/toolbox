<#list plugin.beanList as bean>
DROP TABLE IF EXISTS ${plugin.name}_${bean.name};
CREATE TABLE IF NOT EXISTS ${plugin.name}_${bean.name} (	
	<#list bean.attributs?keys as key>
		${key} ${bean.attributs[key].type},
	</#list>
	PRIMARY KEY  (id)
)COMMENT='Contient les ${bean.name}s';
</#list>

DROP TABLE IF EXISTS ${plugin.name}_sequences;
CREATE TABLE IF NOT EXISTS ${plugin.name}_sequences (	
		sequence_name varchar(255),
		next_val int,
	PRIMARY KEY  (sequence_name, next_val)
)COMMENT='contient les sequences des beans du plugin ${plugin.name} ';
