package tools;

/**
 * Common interface for all tools
 * @author jchaline
 */
public interface Tool
{
    /**
     * Get the tool configuration as string
     * @return the tool configuration
     */
    String getConf();
    
    /**
     * Run the tool
     * @return status result, 0 if everything is alright, other for errors
     */
    int run();
    
    /**
     * Get the tool id
     * @return the string id
     */
    String getId();
    
    /**
     * Get the tool name with short description
     * @return the string name
     */
    String getName();
}
