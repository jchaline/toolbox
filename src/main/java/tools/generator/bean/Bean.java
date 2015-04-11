package tools.generator.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Bean {
	
	private String name;
	private Map<String,Attribut> attributs;
	private boolean withDTO;

	public Bean(String name) {
		this.name = name.toLowerCase();
		attributs = new HashMap<String,Attribut>();
		withDTO = true;
	}
	
	public Bean() {
		name = "";
		attributs = new HashMap<String,Attribut>();
		withDTO = true;
	}
	
	public boolean add(String s, Attribut a){
		boolean ret = false;
		if(!attributs.containsKey(s)){
			attributs.put(s,a);
			ret = true;
		}
		return ret;
	}

	public Map<String,Attribut> getAttributs() {
		return attributs;
	}

	public void setAttributs(Map<String,Attribut> attributs) {
		this.attributs = attributs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toLowerCase();
	}

	public void loadAttributs(List<String> attributs) {
		String[] split ;
		for(String s : attributs){
			split = s.split(",");
			this.attributs.put(split[1], new Attribut(split[0],split[1],split[2].equals("true")));
		}
	}

	public void setWithDTO(boolean withDTO) {
		this.withDTO = withDTO;
	}

	public boolean isWithDTO() {
		return withDTO;
	}

}
