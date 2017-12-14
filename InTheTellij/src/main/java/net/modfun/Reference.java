package net.modfun;

import net.minecraft.client.renderer.GlStateManager;

public class Reference {
	public static final String CLIENT_PROXY_CLASS = "net.modfun.proxy.ClientProxy";
	public static final String COMMON_PROXY_CLASS = "net.modfun.proxy.CommonProxy";
	public static final String MOD_ID = "modfun";
	public static void color(float colorRed, float colorGreen, float colorBlue)
	   {
		   GlStateManager.color(colorRed, colorGreen, colorBlue);
	   }
}
