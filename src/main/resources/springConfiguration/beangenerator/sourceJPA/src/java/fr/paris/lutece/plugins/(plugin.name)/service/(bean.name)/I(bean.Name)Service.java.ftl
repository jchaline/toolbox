package ${package};

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
<#if specific_import?has_content>
<#list specific_import as import>
import ${import};
</#list>
</#if>


/**
 * The ${bean.name?cap_first} interface for service
 * @author ${plugin.authorName}
 */
@Transactional
public interface I${bean.name?cap_first}Service {


}
