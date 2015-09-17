package tools.progress;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import services.FileService;
import tools.Tool;

@Service
public class ProgressTool implements Tool {
	
	private static final Logger logger = Logger.getLogger( ProgressTool.class );
	private static final String CHARSET_PROGRESS = "windows-1252";
    private static final String TOOL_ID = "progtool";
    private static final String TOOL_NAME = "Progress analyser";
    
    private static final String SRC_DIR = "D:\\cnaf\\SIM_juin2015";
    private static final String JSON_FILE = "progress.json";
    private static final String FILE_PATTERN = ".*.i$|.*.w$|.*.p$";
    private static final Pattern INCLUDE_PATTERN = Pattern.compile("\\{([\\w.]*\\.[ipw])\\}|RUN ([\\w.]*\\.[ipw])");

	@Override
	public String getConf() {
		return "";
	}

	@Override
	public int run() {
		
		List<String> findFiles = FileService.findFiles(-1, SRC_DIR, FILE_PATTERN);
		Map<String, List<String>> collect = findFiles.stream().collect(Collectors.toMap(p -> Paths.get(p).getFileName().toString() , p -> dependencies(p)));
		logger.debug("Find " + collect.size() + " files");
		
		generateJson(collect);
		
		return 0;
	}

	private void generateJson(Map<String, List<String>> collect) {
		ObjectMapper mapper = new ObjectMapper(); 
		try {
			mapper.writeValue(new File(JSON_FILE), collect);
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
	private List<String> dependencies(String path) {
		List<String> result = new ArrayList<String>();
		try {
			List<String> lines = FileService.read(path, Charset.forName(CHARSET_PROGRESS));
			lines.stream().filter(l -> INCLUDE_PATTERN.matcher(l).find()).forEach(l ->  {
				Matcher matcher = INCLUDE_PATTERN.matcher(l);
				while (matcher.find()) {
					String group = matcher.group(1);
					if(group == null){
						matcher.group(1);
					}
					result.add(group);
				}
			});
		} catch (IOException e) {
			logger.error("Impossible to load file " + path + " : " + e);
		}
		
		return result;
	}

	@Override
	public String getId() {
		return TOOL_ID;
	}

	@Override
	public String getName() {
		return TOOL_NAME;
	}

}
