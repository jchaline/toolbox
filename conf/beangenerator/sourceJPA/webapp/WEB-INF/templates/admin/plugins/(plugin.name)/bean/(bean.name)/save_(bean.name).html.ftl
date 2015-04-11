<div class="row-fluid">
	<div class="span12">
	    <form method="post" name="save_${bean.name}" id="save_${bean.name}" action="jsp/admin/plugins/${plugin.name}/${bean.name}/DoSave${bean.name?cap_first}.jsp">
			<fieldset>
				<legend>#i18n{${plugin.name}.${bean.name}.create.title}</legend>
			    ${error!}
		    	<input type="hidden" id="jsp_back" name="jsp_back" value="${jsp_back!}"/>
		        
				<div>
					<label for="${bean.name}_name" >#i18n{${plugin.name}.${bean.name}.field.name} * :</label>
					<input type="text" id="name" name="name" class="input-large" maxlength="100" value="${(bean.name)!}"/>
				</div>
				<div>
					<label for="${bean.name}_date" >#i18n{${plugin.name}.${bean.name}.field.date} * :</label>
					<input type="text" id="date" name="date" class="input-large" maxlength="10" value="${(bean.date)!}"/>
				</div>
			</fieldset>
			<fieldset>
				<div class="form-actions pagination-centered">   
					<input type="hidden" id="bean_id" name="id" value="${(bean.id)!}"/>
					
					<button type="submit" name="save" class="btn btn-primary btn-small" tabindex="3" >
						<i class="icon-ok icon-white">&nbsp;</i>
						#i18n{${plugin.name}.transverse.save}
					</button>
					<button class="btn btn-small" type="submit" name="cancel">
						<i class="icon-remove-circle">&nbsp;</i>
						#i18n{${plugin.name}.transverse.cancel}
					</button>
				</div>
			</fieldset>
	   </form>
	</div>
</div>
