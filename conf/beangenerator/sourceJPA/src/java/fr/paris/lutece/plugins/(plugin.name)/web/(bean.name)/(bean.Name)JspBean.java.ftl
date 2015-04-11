package ${package};

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import fr.paris.lutece.plugins.genericjpa.web.GenericJPAJspBean;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;
<#if specific_import?has_content>
<#list specific_import as import>
import ${import};
</#list>
</#if>

/**
 * The ${bean.name?cap_first} Jsp Bean
 * @author ${plugin.authorName}
 */
public class ${bean.name?cap_first}JspBean extends GenericJPAJspBean {

    public static final String RIGHT_MANAGE_${bean.name?upper_case} = "${plugin.name?upper_case}_${bean.name?upper_case}_MANAGEMENT";

    private static final String TEMPLATE_MANAGE_${bean.name?upper_case} = "admin/plugins/${plugin.name}/manage_${bean.name}.html";
	
    /**
     * Get the manage ${bean.name}s page
     *
     * @param request http the request
     * @return the page with ${bean.name}s list
     */
    public String getManage${bean.name?cap_first}s( HttpServletRequest request )
    {
    	Map<String,Object> model = super.getManageBeansModel( request );
    
    	HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_${bean.name?upper_case}, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

}