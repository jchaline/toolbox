package ${package};

import org.dozer.Mapper;

import fr.paris.lutece.plugins.${plugin.name}.bean.${bean.name}.${bean.name?cap_first};
import fr.paris.lutece.plugins.${plugin.name}.dto.${bean.name}.${bean.name?cap_first}DTO;
import fr.paris.lutece.plugins.generic.dao.commons.ResultList;
import fr.paris.lutece.plugins.generic.utils.dozer.AbstractDozerConverter;
import fr.paris.lutece.portal.service.spring.SpringContextService;

/**
 * The ${bean.name?cap_first} converter between bean and DTO
 * @author ${plugin.authorName}
 */
public class ${bean.name?cap_first}Converter extends AbstractDozerConverter<${bean.name?cap_first}DTO,${bean.name?cap_first}>{

	private static final String BEAN_MAPPER = "mapper";

	private ${bean.name?cap_first}Converter(){}
	
	public ${bean.name?cap_first}DTO convertEntity(${bean.name?cap_first} source) {
		Mapper mapper = (Mapper) SpringContextService.getBean(BEAN_MAPPER);
		return mapper.map(source, ${bean.name?cap_first}DTO.class);
	}

	public ResultList<${bean.name?cap_first}DTO> convertEntities(ResultList<${bean.name?cap_first}> findAll) {
		ResultList<${bean.name?cap_first}DTO> ret;
		if (findAll != null) {
			ret = new ResultList<${bean.name?cap_first}DTO>();
			for (${bean.name?cap_first} ${bean.name}: findAll) {
				ret.add(convertEntity(${bean.name}));
			}		
			ret.setTotalResult(findAll.getTotalResult());
		} else {
			ret = null;
		}

		return ret;
	}

}