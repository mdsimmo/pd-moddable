package com.watabou.modloader;

import com.watabou.noosa.Image;
import com.watabou.pixeldungeon.Assets;

public class TestMod implements ModInfo {

	@Override
	public Image getLogo() {
		return new Image(Assets.RATKING, 0, 0, 16, 16);
	}

	@Override
	public String getVersion() {
		return "0.0.1";
	}
	
	@Override
	public String getTarget() {
		return "1.7.1c";
	}

	@Override
	public String getDescription() {
		return "This mod does absolutly nothing. It is puerely (spelt wrong) for testing purposes. Now I'm just waffling (also spelt wrong?) because I want a longer sentence. Blah blah blah. The end :D";
	}
	
	@Override
	public String getModName() {
		return "Mod Test";
	}

	
	
}
