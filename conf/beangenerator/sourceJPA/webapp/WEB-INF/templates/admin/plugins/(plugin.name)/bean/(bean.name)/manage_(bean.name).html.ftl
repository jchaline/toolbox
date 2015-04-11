< #macro columnActionsBean item >
    < @showButton type="edit" href="jsp/admin/plugins/${plugin.name}/${bean.name}/Save${bean.name?cap_first}.jsp?bean_id=$ {item.id}" />
    < @showButton type="delete" href="jsp/admin/plugins/${plugin.name}/${bean.name}/Delete${bean.name?cap_first}.jsp?bean_id=$ {item.id}" />
</ #macro>

<div class="row-fluid">
	<div class="span12">
		<fieldset>
			<legend>
				#i18n{${plugin.name}.${bean.name}.manage.title}
				<span class="pull-right spaced">
				  	<a class="btn btn-primary btn-small" href="jsp/admin/plugins/${plugin.name}/${bean.name}/Save${bean.name?cap_first}.jsp">	    	
			     		<i class="icon-plus icon-white" ></i>&nbsp;
			     		<span class="hidden-phone">
			     			#i18n{${plugin.name}.${bean.name}.manage.button.create}
			     		</span>	
				  	</a>
				</span>
			</legend>
			<div class="row-fluid">
				<div class="span3 well">
					<form action="jsp/admin/plugins/${plugin.name}/chat/Manage${bean.name?cap_first}.jsp" method="post">
						<input type="hidden" name="filter" value="filter" />
						<div class="">
							<label for="filter_name">#i18n{${plugin.name}.${bean.name}.field.name} : </label>
							<input class="span"  type="text" value="${(filter.name)!}" name="name" id="name" />
						</div>
						< @showSearchButton />
					</form>
				</div>
				<div class="span9">
					< @dataTable dataTableManager=dataTableBean />
				</div>
			</div>
		</fieldset>
	</div>
</div>

