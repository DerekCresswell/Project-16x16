package windows;

import scene.SceneMapEditor;
import sidescroller.PClass;
import sidescroller.SideScroller;
import ui.Anchor;
import ui.Button;
import ui.List;
import ui.ScrollBarVertical;

import java.io.File;

import processing.core.PApplet;

public class LoadLevelWindow extends PClass {

	String path = "Assets/Storage/Game/Maps/";
	// Map editor Scene
	public SceneMapEditor scene;
	public List list;
	File f;

	public LoadLevelWindow(SideScroller a) {
		super(a);

		scene = (SceneMapEditor) a.mapEditor;

		f = new File(path);
		list = new List(a, f.list(), 30);
		list.setSizeH(200);
		list.setPosition(applet.width / 2, 325);
		list.setConfirmButton("Confirm", applet.width / 2, 500);
		list.setCancelButton("Cancel", applet.width / 2, 550);
	}

	public void display() {
		// Display Privacy Area
		applet.fill(0, 100);
		applet.noStroke();
		applet.rect(applet.width / 2, applet.height / 2, applet.width, applet.height);

		// Display Window
		applet.fill(29, 33, 45);
		applet.stroke(47, 54, 73);
		applet.strokeWeight(8);
		applet.rect(applet.width / 2, applet.height / 2, 400, 500);

		// Display Window Title
		applet.pushMatrix();
		applet.fill(255);
		applet.textSize(30);
		applet.textAlign(CENTER, CENTER);
		applet.text("Load Level", applet.width / 2, applet.height / 2 - 200);
		applet.popMatrix();
		// Display Load Press
		list.display();
	}

	public void update() {
		list.update();
		confirmButton();
		cancelButton();
	}

	public void confirmButton() {
		if (list.getConfirmPress() && !list.getElement().isEmpty()) {
			util.loadLevel(path + list.getElement());
			list.resetElement();
			scene.tool = SceneMapEditor.Tools.MOVE;
		} else if (list.getConfirmPress() && list.getElement().isEmpty())
			scene.tool = SceneMapEditor.Tools.MOVE;
	}

	public void cancelButton() {
		if (list.getCancelPress()) {
			scene.tool = SceneMapEditor.Tools.MOVE;
			list.resetElement();
		}
	}

}
