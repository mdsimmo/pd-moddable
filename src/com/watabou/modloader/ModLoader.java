package com.watabou.modloader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;

import com.watabou.noosa.Game;

import dalvik.system.DexClassLoader;

public abstract class ModLoader {

	// TODO remove these
	private static Game game = Game.instance;
	private static final int BUFFER_SIZE = 1028 * 8;
	private static final String MOD_LOCATION = "plugins/ModTest.apk";
	private static final File dexDir = game.getDir( "dex", Game.MODE_PRIVATE );
	private static final File dexFile = new File(dexDir, "ModTest.apk");
	
	private static List<Mod> mods = new ArrayList<>();
	
	@SuppressLint("NewApi")
	public static void loadPlugins() {
		try {
			InputStream is = game.getAssets().open( MOD_LOCATION );
		BufferedInputStream bis = new BufferedInputStream( is );

		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream( dexFile ) );
		byte[] buf = new byte[BUFFER_SIZE];
		int len;
		while ( (len = bis.read( buf, 0, BUFFER_SIZE )) > 0 ) {
			out.write( buf, 0, len );
		}
		out.close();
		bis.close();

		DexClassLoader dcl = new DexClassLoader( 
				dexFile.getAbsolutePath(),
				dexDir.getAbsolutePath(),
				null,
				game.getClassLoader() );
		Class<?> clazz = dcl.loadClass( "com.mdsimmo.modtest.TestMod" );
		Mod mod = (Mod)clazz.newInstance();
		register(mod);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	public static void register(Mod mod) {
		mods.add( mod );
		mod.onEnable();
	}
	
	public static List<Mod> allMods() {
		return mods;
	}

	public static void destroyMods() {
		mods.clear();
	}
}
