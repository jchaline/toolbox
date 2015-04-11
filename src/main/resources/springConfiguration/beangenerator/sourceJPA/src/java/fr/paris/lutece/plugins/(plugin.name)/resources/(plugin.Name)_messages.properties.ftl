#information about the plugin
plugin.description=Some description about the plugin
plugin.provider=Mairie de Paris


#admin feature
<#list plugin.beanList as beanItem>
adminFeature.${plugin.name}.${beanItem.name}Management.name=${plugin.name?cap_first} ${beanItem.name}s Management
adminFeature.${plugin.name}.${beanItem.name}Management.description=The ${plugin.name?cap_first} ${beanItem.name}s management page
</#list>

#transverse message for the application
transverse.title.delete=Supprimer
transverse.title.edit=Modifier
transverse.title.send=Envoyer
transverse.title.history=Historique
transverse.title.export=Exporter
transverse.title.up=Monter
transverse.title.down=Descendre
transverse.title.see=Apercevoir