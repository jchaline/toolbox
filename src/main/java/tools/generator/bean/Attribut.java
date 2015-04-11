package tools.generator.bean;

/**
 * Class to generate the attribute in beans
 * @author jchaline
 *
 */
public class Attribut {

	private String type;
	private String name;
	private boolean filter;
	
	public Attribut(String type, String name, boolean filter){
		this.setType(type.toLowerCase());
		this.setName(name.toLowerCase());
		this.setFilter(filter);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type.toLowerCase();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toLowerCase();
	}

	public boolean isFilter() {
		return filter;
	}

	public void setFilter(boolean filter) {
		this.filter = filter;
	}
	
	

}
