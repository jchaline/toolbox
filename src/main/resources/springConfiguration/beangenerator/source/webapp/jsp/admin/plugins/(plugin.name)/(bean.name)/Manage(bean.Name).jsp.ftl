<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../../AdminHeader.jsp" />

<%@page import="fr.paris.lutece.plugins.${plugin.name}.web.${bean.name?cap_first}JspBean"%>
<jsp:useBean id="${bean.name}" scope="session" class="fr.paris.lutece.plugins.${plugin.name}.web.${bean.name}.${bean.name?cap_first}JspBean" />
<% 
${bean.name}.init( request, ${bean.name?cap_first}JspBean.RIGHT_MANAGE_${bean.name?upper_case});
%>
<%= ${bean.name}.getManage${bean.name?cap_first}( request ) %>

<%@ include file="../../../AdminFooter.jsp" %>
