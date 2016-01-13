package practicalities;

import java.io.IOException;
import java.io.InputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringTranslate;
import practicalities.lib.util.AdvancedLangLoader;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import practicalities.registers.BlockRegister;
import practicalities.registers.GuideRegister;
import practicalities.registers.ItemRegister;

public class ProxyClient extends ProxyCommon implements IResourceManagerReloadListener {

	@Override
	public void preInit() {
		super.preInit();
		registerRenders();
	}
	
	@Override
	public void init() {
		super.init();
		GuideRegister.init();
		( (IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager() ).registerReloadListener(this);
	}
	
	@Override
	public void registerRenders() {
		ItemRegister.registerRenders();
		BlockRegister.registerRenders();
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		String lang = Minecraft.getMinecraft().gameSettings.language;
		InputStream stream = stream(PracticalitiesMod.TEXTURE_BASE + "guides/"+lang+".lang");
		if(stream == null) stream = stream(PracticalitiesMod.TEXTURE_BASE + "guides/en_US.lang");
		StringTranslate.inject(AdvancedLangLoader.parse(stream));
	}
	
	public InputStream stream(String resource) {
		try {
			return Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(resource)).getInputStream();
		} catch (IOException e) {
			return null;
		}
	}
}
