package com.watabou.modloader;

public interface Mod {

	public void onEnable();
	public ModInfo getInfo();
	public void onDisable();
	
}
