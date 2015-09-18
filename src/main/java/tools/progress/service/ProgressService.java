package tools.progress.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class ProgressService {
	
	private static final Pattern INCLUDE_PATTERN = Pattern.compile("\\{([\\w.]*\\.[ipw])\\}|RUN ([\\w.]*\\.[ipw])");
	
	public boolean keepLine(String line) {
		return INCLUDE_PATTERN.matcher(line).find();
	}
	
	public List<String> findDependencies(String line) {
		Matcher matcher = INCLUDE_PATTERN.matcher(line);
		List<String> res = new ArrayList<String>();
		while (matcher.find()) {
			String group = matcher.group(1);
			if (group == null) {
				group = matcher.group(2);
			}
			res.add(group);
		}
		return res;
	}

}
