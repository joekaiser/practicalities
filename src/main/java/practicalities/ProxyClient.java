package practicalities;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringTranslate;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import practicalities.registers.GuideRegister;
import practicalities.registers.ItemRegister;

public class ProxyClient extends ProxyCommon implements IResourceManagerReloadListener {

	@Override
	public void init() {
		super.init();
		registerRenders();
		GuideRegister.init();
		( (IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager() ).registerReloadListener(this);
	}
	
	@Override
	public void registerRenders() {
		ItemRegister.registerRenders();
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
//		mergeAdvancedLangFile("en_US");
		String lang = Minecraft.getMinecraft().gameSettings.language;
		InputStream stream = stream(PracticalitiesMod.TEXTURE_BASE + "guides/"+lang+".lang");
		if(stream == null) stream = stream(PracticalitiesMod.TEXTURE_BASE + "guides/en_US.lang");
		mergeAdvancedLangFile(stream);
	}
	
	public void mergeAdvancedLangFile(InputStream stream) {
		
		Pattern bracketLine = Pattern.compile("^([\\w.]+)\\{");
		
		try {
			StringBuilder real = new StringBuilder();
			
			int lineNum = 0;
			boolean inRegion = false;
			String region = "";
			
			for (String line : IOUtils.readLines(stream, Charsets.UTF_8)) {
				lineNum++;
				
				line = line.replaceFirst("^\\s+", "");
				if(line.length() == 0)
					continue;
				
				if(line.charAt(0) == '#')
					continue;
				if(line.charAt(0) == '}') {
					region = "";
					inRegion = false;
					continue;
				}
				
				
				Matcher m = bracketLine.matcher(line);
				
				if(m.matches()) {
					if(inRegion) {
						Logger.error("[Advanced Lang Loader] CANNOT HAVE NESTED REGIONS! Closing region. (line #%i)", lineNum);
					}
					region = m.group(1);
					inRegion = true;
					continue;
				}
				
				real.append(region+line+"\n");
			}
			
			IOUtils.closeQuietly(stream);
			
			InputStream lang = new ByteArrayInputStream(real.toString().getBytes(StandardCharsets.UTF_8));
			StringTranslate.inject(lang);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public InputStream stream(String resource) {
		try {
			return Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(resource)).getInputStream();
		} catch (IOException e) {
			return null;
		}
	}
}
