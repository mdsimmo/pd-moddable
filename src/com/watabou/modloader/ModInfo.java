package com.watabou.modloader;

import com.watabou.noosa.Image;

public interface ModInfo {
	
	/**
	 * the mod's name to show the user
	 */
	public String getModName();
	
	/**
	 * the mod's logo (should be ~16x16)
	 */
	public Image getLogo();
	
	/**
	 * A brief description of the mod 
	 */
	public String getDescription();
	
	/**
	 * The version of the mod
	 */
	public String getVersion();
	
	/**
	 * The version of pixel dungeon the mod was build for
	 */
	public String getTarget();	
}
