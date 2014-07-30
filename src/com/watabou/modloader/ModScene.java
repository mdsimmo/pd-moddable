package com.watabou.modloader;

import com.watabou.noosa.BitmapText;
import com.watabou.noosa.BitmapTextMultiline;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.ui.Button;
import com.watabou.noosa.ui.Component;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Chrome;
import com.watabou.pixeldungeon.scenes.PixelScene;
import com.watabou.pixeldungeon.scenes.TitleScene;
import com.watabou.pixeldungeon.ui.Archs;
import com.watabou.pixeldungeon.ui.RedButton;
import com.watabou.pixeldungeon.ui.ScrollPane;
import com.watabou.pixeldungeon.ui.Window;

public class ModScene extends PixelScene {

	public int MARGIN = 4;
	
	@Override
	public void create() {

		super.create();
		
		Music.INSTANCE.play( Assets.THEME, true );
		Music.INSTANCE.volume( 1f );
		
		uiCamera.visible = false;
		
		int w = Camera.main.width;
		int h = Camera.main.height;
		
		Archs archs = new Archs();
		archs.setSize( w, h );
		add( archs );

		int pos = MARGIN;
		
		BitmapText heading = PixelScene.createText( "Mods", 9 );
		heading.hardlight( Window.TITLE_COLOR );
		heading.measure();
		heading.x = (w - heading.width) / 2;
		heading.y = pos;
		add( heading );
		
		pos += heading.height + MARGIN;
		
		int pw = Math.min( 160, w - MARGIN*2 );
		int buttonHeight = 16;
		int ph = h - pos - buttonHeight - MARGIN*2;
		
		NinePatch panel = Chrome.get( Chrome.Type.WINDOW );
		panel.size( pw, ph );
		panel.x = (w - pw) / 2;
		panel.y = pos;
		add( panel );
		
		ScrollPane pane = new ModList();
		add( pane );
		pane.setRect( panel.x +panel.marginLeft(),
				panel.y + panel.marginTop(),
				panel.innerWidth(),
				panel.innerHeight() );
		pane.content().add( new ModButton( new TestMod() ) );
		
		pos = (int)panel.height() + MARGIN;
		
		Button more = new RedButton("More Mods") {
			@Override
			protected void onClick() {
				// TODO Auto-generated method stub
				super.onClick();
			}
		};
		more.setSize( Math.min( 64, (w-MARGIN*3)/2 ), buttonHeight );
		more.setPos( (w - more.width())/2, pos );
		add( more );

		fadeIn();

	}

	@Override
	protected void onBackPressed() {
		Game.switchScene( TitleScene.class );
	}

	private class ModButton extends Button {

		private ModInfo mod;
		private Image logo;
		private BitmapText title;

		public ModButton(ModInfo mod) {
			super();
			this.mod = mod;
			logo.copy( mod.getLogo() );
			title.text( mod.getModName() );

			setSize( 256, 32 );
		}

		@Override
		protected void createChildren() {
			super.createChildren();

			logo = new Image();
			add( logo );
			title = createText( 9 );
			add( title );
		}

		@Override
		protected void layout() {
			super.layout();
			logo.x = x;
			logo.y = PixelScene.align( y + (height - logo.height) / 2 );

			title.x = logo.x + logo.width + 4;
			title.y = PixelScene.align( y + (height - title.baseLine()) / 2 );
		}

		@Override
		protected void onClick() {
			super.onClick();
			Game.scene().add( new WndModBrief( mod ) );
		}
	}

	private class ModList extends ScrollPane {

		public ModList() {
			super( new Component() );
		}

	}

	private class WndModBrief extends Window {

		private int WIDTH = Math.min( Camera.main.width-MARGIN*2, 120 );

		public WndModBrief(ModInfo mod) {
			super();
			int pos = MARGIN;
			
			
			Image icon = mod.getLogo();
			icon.x = MARGIN;
			icon.y = pos;
			add( icon );
			
			BitmapText title = createText( mod.getModName(), 9 );
			title.hardlight( Window.TITLE_COLOR );
			title.x = icon.x + icon.width + MARGIN;
			title.y = icon.y + PixelScene.align( (icon.height - title.baseLine()) / 2 );
			add( title );
			
			pos += icon.height + MARGIN;
			
			BitmapTextMultiline about = createMultiline( " Version: " + mod.getVersion() +"\n"
						+ " Target: " + mod.getTarget() + "\n\n"
						+ mod.getDescription() , 6);
			about.maxWidth = WIDTH - MARGIN*2;;
			about.x = MARGIN;
			about.y = pos;
			about.measure();
			add( about );
			
			pos += about.height() + MARGIN;
			
			RedButton uninstal = new RedButton( "Uninstal" ) {
				@Override
				protected void onClick() {
					// TODO Auto-generated method stub
					super.onClick();
				}
			};
			uninstal.setSize( WIDTH/2-MARGIN*1.5f, 16 );
			uninstal.setPos( MARGIN, pos );
			add( uninstal );
			
			RedButton disable = new RedButton( "Disable" ) {
				@Override
				protected void onClick() {
					// TODO Auto-generated method stub
					super.onClick();
				}
			};
			disable.setSize( uninstal.width(), uninstal.height() );
			disable.setPos( uninstal.right() + MARGIN, pos );
			add( disable );
			
			pos += uninstal.height() + MARGIN;

			resize( WIDTH, pos );
		}
	}

}
