package tools.generator.bean;

import java.util.List;

public class Plugin {
	
	private List<Bean> beanList;
	private String name;
	private String authorName;
	private List<String> packages;
	private List<Xpage> xpages;
	
	
	public Plugin(String name){
		
		
	}

	public List<Bean> getBeanList() {
		return beanList;
	}

	public void setBeanList(List<Bean> beanList) {
		this.beanList = beanList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public List<String> getPackages() {
		return packages;
	}

	public void setPackages(List<String> packages) {
		this.packages = packages;
	}

    /**
     * @param xpages the xpages to set
     */
    public void setXpages( List<Xpage> xpages )
    {
        this.xpages = xpages;
    }

    /**
     * @return the xpages
     */
    public List<Xpage> getXpages( )
    {
        return xpages;
    }

}
