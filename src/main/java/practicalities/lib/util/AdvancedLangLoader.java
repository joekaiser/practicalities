package practicalities.lib.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import practicalities.Logger;

public class AdvancedLangLoader {

	static Pattern braceLine   = Pattern.compile("^([\\w.]+)\\{");
	static Pattern bracketLine = Pattern.compile("^([\\w.]+)\\[");
	
	public static InputStream parse(InputStream stream) {		
		try {
			StringBuilder real = new StringBuilder();
			
			final Map<String, String> variables = new HashMap<String, String>();
			boolean isAddingVars = false;
			
			Rewriter vars = new Rewriter("(?<!\\\\)\\$\\{(\\w+)\\}") {
				
				@Override
				public String replacement() {
					String name = group(1);
					if(variables.containsKey(name)) {
						return variables.get(name);
					}
					Logger.error("[Advanced Lang Loader] Variable not found: %s", name);
					return "!!VAR_NOT_FOUND(" + name + ")!!";
				}
			};
			
			int lineNum = 0;
			Stack<String> region = new Stack<String>();
			
			boolean isInBlock = false;
			String currentBlockName = "";
			StringBuilder currentBlock = new StringBuilder();
			
			for (String line : IOUtils.readLines(stream, Charsets.UTF_8)) {
				lineNum++;
				
				line = line.replaceFirst("^\\s+", "");
				if(line.length() == 0) {
					if(isInBlock)
						currentBlock.append("\n");
					continue;					
				}
				
				line = vars.rewrite(line);
				line = line.replaceAll("\\\\(\\$\\{\\w+\\})", "$1");
				
				if(line.charAt(0) == ']') {
					real.append(e( currentBlockName, currentBlock.toString() )+"\n");
					isInBlock = false;
					currentBlockName = "";
					currentBlock = new StringBuilder();
					continue;
				}
				
				line = line.replaceAll("^¡", "");
				
				if(isInBlock) {
					if(line.endsWith("\\"))
						currentBlock.append(line.substring(0, line.length()-1));
					else
						currentBlock.append(line + "\n");
					continue;
				}
				
				if(line.startsWith("${") && region.isEmpty()) {
					isAddingVars = true;
					continue;
				}
				if(line.charAt(0) == '#')
					continue;
				if(line.charAt(0) == '}') {
					if(isAddingVars) {
						isAddingVars = false;
					}
						
					if(region.isEmpty()) {
						Logger.error("[Advanced Lang Loader] Ignoring unexpected close bracket: line %d", lineNum);
					} else {
						region.pop();
					}
					continue;
				}
				
				if(isAddingVars) {
					String[] parts = line.split("=", 2);
					if(parts.length < 2) {
						Logger.error("[Advanced Lang Loader] Invalid variable declaration, '=' not found: line %d", lineNum);
					} else {
						variables.put(parts[0], parts[1]);
					}
					continue;
				}
				
				
				Matcher m = braceLine.matcher(line);
				
				if(m.matches()) {
					region.push( (region.isEmpty() ? "" : region.peek() ) + m.group(1) );
					continue;
				}
				
				m = bracketLine.matcher(line);
				
				if(m.matches() && !isInBlock) {
					currentBlockName = (region.isEmpty() ? "" : region.peek() ) + m.group(1);
					isInBlock = true;
					continue;
				}
				
				real.append((region.isEmpty() ? "" : region.peek()) + line + "\n");
				
			}
			
			IOUtils.closeQuietly(stream);
			
			InputStream lang = new ByteArrayInputStream(real.toString().getBytes(StandardCharsets.UTF_8));
			return lang;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String e(String name, String text) {
		return name + "=" + text.replaceAll("\n", "\\\\n");
	}
	
}
