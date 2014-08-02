package com.watabou.modloader;


public abstract class Mod {

	public void onEnable() {}
	public void onDisable() {}
	public abstract ModInfo getInfo();
	
}
