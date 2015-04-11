package ${package};

import javax.inject.Inject;
import javax.inject.Named;

import fr.paris.lutece.plugins.${plugin.name}.dao.${bean.name}.I${bean.name?cap_first}DAO;
import fr.paris.lutece.plugins.generic.service.AbstractService;
<#if specific_import?has_content>
<#list specific_import as import>
import ${import};
</#list>
</#if>

/**
 * The ${bean.name?cap_first} class service
 * @author ${plugin.authorName}
 */
public class ${bean.name?cap_first}Service extends AbstractService implements I${bean.name?cap_first}Service {

	@Inject
	@Named("${plugin.name}.${bean.name}DAO")
	private I${bean.name?cap_first}DAO _dao${bean.name?cap_first};
	
	@Override
	protected AbstractDAO getDAO() {
		return (AbstractDAO) _dao${bean.name?cap_first};
	}


}
/*
 * Copyright (c) 2002-2009, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.ods.modules.cp.service;

import fr.paris.lutece.portal.service.plugin.PluginDefaultImplementation;

import java.io.Serializable;


/**
 * ${plugin.name?cap_first} Plugin
 */
public class ${plugin.name?cap_first}Plugin extends PluginDefaultImplementation implements Serializable
{
    private static final long serialVersionUID = 5633080377569288953L;
    public static String PLUGIN_NAME = "${plugin.name}";

    public void init(  )
    {
        super.init();
    }
}
