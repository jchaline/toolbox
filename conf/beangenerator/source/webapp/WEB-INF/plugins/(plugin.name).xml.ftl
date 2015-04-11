<?xml version="1.0" encoding="ISO-8859-1" standalone="no"?><plug-in>
    <!-- Plugin Informations -->
    <name>${(plugin.name)!'pluginName'}</name>    
    <class>fr.paris.lutece.plugin.${(plugin.name)!'pluginName'}.service.${(plugin.name?cap_first)!'pluginName'}Plugin</class>
    <version>1.0.0-SNAPSHOT</version>
    <documentation/>
    <installation/>
    <changes/>
    <user-guide/>
    <description>${(plugin.name)!'pluginName'}.plugin.description</description>
    <provider>${(plugin.name)!'pluginName'}.plugin.provider</provider>
    <provider-url>http://lutece.paris.fr</provider-url>
    <icon-url>images/admin/skin/plugins/${(plugin.name)!'pluginName'}/${(plugin.name)!'pluginName'}.png</icon-url>
    <copyright>Copyright 2002-2013 Mairie de Paris</copyright>
    <db-pool-required>1</db-pool-required>
    
    <freemarker-macro-files>
		<freemarker-macro-file>/commons_${(plugin.name)!'pluginName'}.html
		</freemarker-macro-file>
	</freemarker-macro-files>
    
    <!--  Specific plugin CSS stylesheet   --> 
    <css-stylesheets>
          <css-stylesheet>${(plugin.name)!'pluginName'}/${(plugin.name)!'pluginName'}.css</css-stylesheet> 
    </css-stylesheets>
    
    <!--  Specific plugin JAVASCRIPT   --> 
    <javascript-files>
          <javascript-file>${(plugin.name)!'pluginName'}/${(plugin.name)!'pluginName'}.js</javascript-file> 
    </javascript-files>
    
    <!-- Administration interface parameters -->
    <admin-features>
    	<#list plugin.beanList as beanItem>
    		<admin-feature>
				<feature-id>${plugin.name?upper_case}_${beanItem.name?upper_case}_MANAGEMENT</feature-id>
				<feature-title>plugin.${(plugin.name)!}.adminFeature.${(plugin.name)!}.${beanItem.name}Management.name</feature-title>
				<feature-description>plugin.${(plugin.name)!}.adminFeature.${(plugin.name)!}.${beanItem.name}Management.description</feature-description>
				<feature-level>4</feature-level>
				<feature-url>jsp/admin/plugins/${(plugin.name)!}/${beanItem.name}/Manage${beanItem.name?cap_first}.jsp</feature-url>
				<feature-group>${(plugin.name?upper_case)!}</feature-group>
				<feature-icon-url>images/admin/skin/plugins/${(plugin.name)!}/adminPage/adminPage.png</feature-icon-url>
			</admin-feature>
    	</#list>
    </admin-features>    
    
	<!-- Applications -->
	<applications>
		<!-- The XPage
		<application>
			<application-id>${(plugin.name)!'pluginName'}_accueil</application-id>
			<application-class>fr.paris.lutece.plugins.${(plugin.name)!'pluginName'}.web.xpage.${(plugin.name)!'pluginName'}App</application-class>
		</application>
		-->
    </applications>
    
    <!-- Daemons -->
    <daemons>
	</daemons>
    
    <!-- RBAC Resources -->
    <rbac-resource-types>   	
   </rbac-resource-types>
</plug-in>
