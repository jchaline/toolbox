<#-- FILE WITH ALL THE SPECIFIC MACRO OF THE PLUGIN -->
<#-- internal macro for show button -->
<#macro getButtonCode link title aspect icon additionalParameters="" >
	<a ${(additionalParameters)!} href="${link}"
		title="${title}"
		class="${aspect}">
		<i class="${icon}" ></i>
	</a>
</#macro>

<#-- macro use to display a button with generic code and bootstrap css-->
<#macro showButton type link="" title="" additionalParameters="" >
	<#if type?? && link?? && title??>
		<#-- standard aspect -->
		<#assign aspect="btn btn-small btn-primary" />
		
		<#-- map of the icon & title -->
		<#assign map = {
				"delete":["ods.transverse.title.delete","btn btn-small btn-danger","icon-trash icon-white"],
				"edit":["ods.transverse.title.edit","btn btn-small btn-primary","icon-edit icon-white"],
				"send":["ods.transverse.title.send","btn btn-small btn-primary","icon-envelope icon-white"],
				"history":["ods.transverse.title.history","btn btn-small btn-primary","icon-book icon-white"],
				"export":["ods.transverse.title.export","btn btn-small btn-primary","icon-download icon-white"],
				"up":["ods.transverse.title.up","btn btn-small btn-primary","icon-chevron-up icon-white"],
				"down":["ods.transverse.title.down","btn btn-small btn-primary","icon-chevron-down icon-white"],
				"see":["ods.transverse.title.see","btn btn-small btn-primary","icon-eye-open icon-white"]
			}>

		<#if !(title?has_content) >
			<#assign nouveauTitre="${(map[type][0])!}" />
		</#if>
		<@getButtonCode link=link title="#i18n{${(nouveauTitre)!}}" aspect="${(map[type][1])!}" icon="${(map[type][2])!}" additionalParameters=additionalParameters />
	</#if>
</#macro>

<#-- Special macro to display the search button in BO in filter form -->
<#macro showSearchButton title="Rechercher">
	<div class="form-actions">
		<button class="btn btn-primary btn-small" type="submit" >
			<i class="icon-search icon-white"></i>&nbsp;
			<span class="hidden-phone">${title}</span>
		</button>
	</div>
</#macro>