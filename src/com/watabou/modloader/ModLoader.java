package com.watabou.modloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.util.Log;

import com.watabou.noosa.Game;

import dalvik.system.DexClassLoader;

public abstract class ModLoader {

	private static Game game = Game.instance;
	private static final File dexDir = game.getDir( "dex", Game.MODE_PRIVATE );

	private static List<Mod> mods = new ArrayList<>();

	public static void loadPlugins() {
		Intent intent = new Intent( "com.watabou.pixeldungeon.LOAD_MOD" );
		List<ResolveInfo> list = game.getPackageManager()
				.queryBroadcastReceivers( intent, 0 );
		for (ResolveInfo info : list ) {
			String source = info.activityInfo.applicationInfo.sourceDir;
			String classname = info.activityInfo.name;
			try {
				ClassLoader cl = new DexClassLoader( source,
						dexDir.getAbsolutePath(), null, game.getClassLoader() );
				Class<?> clazz = cl.loadClass( classname );
				Mod mod = (Mod) clazz.newInstance();
				register( mod );
			} catch ( ClassNotFoundException e ) {
				Log.e( "ModLoader", "Couldn't find class " + classname );
			} catch ( ClassCastException e ) {
				Log.e( "ModLoader", "Couldn't cast " + classname + " to "
						+ Mod.class );
			} catch ( Exception e ) {
				Log.e( "ModLoader", "Couldn't load class " + classname );
			}
		}
	}

	public static void register( Mod mod ) {
		Log.i( "ModLoader", "Enabling mod: " + mod.getInfo().getModName() );
		mods.add( mod );
		mod.onEnable();
	}

	public static List<Mod> allMods() {
		return mods;
	}

	public static void destroyMods() {
		for (Mod mod : mods)
			mod.onDisable();
		mods.clear();
	}
}
