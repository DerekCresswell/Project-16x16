package objects;

import jdk.tools.jlink.internal.TaskHelper.Option.Processing;
import projectiles.MagicProjectile;
import projectiles.Swing;
import sidescroller.SideScroller;
import sidescroller.Tileset;

/**
 * Extends {@link GameObject}.
 */
public class MagicSourceObject extends GameObject {

	public MagicSourceObject(SideScroller a) {
		super(a);

		type = type.OBJECT;
		id = "MAGIC_SOURCE";

		// Default image
		image = Tileset.getTile("MAGIC_SOURCE");

		// Setup Animation		
		animation.changeAnimation(Tileset.getAnimation("MAGIC::IDLE"), true, 6); // TODO: add magicsheet to tileset

		width = 48;
		height = 48;

		pos.y = -80;
	}

	@Override
	public void display() {
		applet.image(image, pos.x, pos.y);
	}
	
	//oldMillis is used to calculate the difference in time between shots.
	//shotDelay denotes the "fire rate" of the MagicSource in milliseconds.
	int oldMillis = 0;
	int shotDelay = 500;
	
	@Override
	public void update() {
		image = animation.animate();

		// Create new Magic Projectiles
		for (int i = 0; i < applet.player.swings.size(); i++) {
			Swing swing = applet.player.swings.get(i);

			if (collidesWithSwing(swing)) {
				if (!swing.activated) {
					
					if(applet.millis() > oldMillis + shotDelay) {
						oldMillis = applet.millis();
						
						applet.projectileObjects
							.add(new MagicProjectile(applet, (int) pos.x, (int) pos.y, swing.direction));

						swing.activated = true;
					}
				}
			}
		}
	}

	public boolean collidesWithSwing(Swing swing) {
		return (swing.pos.x + swing.width / 2 > pos.x - width / 2
				&& swing.pos.x - swing.width / 2 < pos.x + width / 2)
				&& (swing.pos.y + swing.height / 2 > pos.y - height / 2
						&& swing.pos.y - swing.height / 2 < pos.y + height / 2);
	}

	public boolean collidesWithPlayer() {
		return (applet.player.pos.x + applet.player.width / 2 > pos.x - width / 2
				&& applet.player.pos.x - applet.player.width / 2 < pos.x + width / 2)
				&& (applet.player.pos.y + applet.player.height / 2 > pos.y
						- height / 2
						&& applet.player.pos.y - applet.player.height / 2 < pos.y
								+ height / 2);
	}
}
