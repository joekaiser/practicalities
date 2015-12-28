package practicalities.lib.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.util.StringTranslate;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import practicalities.Logger;

public class AdvancedLangLoader {

	static Pattern braceLine   = Pattern.compile("^([\\w.]+)\\{");
	static Pattern bracketLine = Pattern.compile("^([\\w.]+)\\[");
	
	public static InputStream parse(InputStream stream) {		
		try {
			StringBuilder real = new StringBuilder();
			
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
				
				
				if(line.charAt(0) == '#')
					continue;
				if(line.charAt(0) == '}') {
					if(region.isEmpty()) {
						Logger.error("[Advanced Lang Loader] Ignoring unexpected close bracket: line %d", lineNum);
					} else {
						region.pop();
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
