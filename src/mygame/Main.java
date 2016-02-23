package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.util.TangentBinormalGenerator;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    BitmapText EarthHealth;
    BitmapText CamPos;
    BitmapText CamRot;
    Spatial Earth; //These are only here to debug. Will be removed soon.
      public static void main(String[] args) {
          System.out.println("Starting Game!");
          Main app = new Main();
          
          /*AppSettings gameSettings = null;
          gameSettings = new AppSettings(false);
          gameSettings.setResolution(1280, 720);
          gameSettings.setFullscreen(false);
          gameSettings.setVSync(false);
          gameSettings.setTitle("Stellar Conquest");
          gameSettings.setUseInput(true);
          gameSettings.setFrameRate(500);
          gameSettings.setSamples(0);
          gameSettings.setRenderer("LWJGL-OpenGL2");
          app.settings = gameSettings;*/
          //app.setShowSettings(false);
          
          app.setDisplayFps(true);
          app.setDisplayStatView(false);
          
          app.start();
      }

      @Override
      public void simpleInitApp() {
          /** I just started learning this, so a lot of the code comes straight from the wiki. */
          System.out.println("Loading App!");
          Earth = assetManager.loadModel("Models/Earth/Earth.obj");
          Material EarthMat = new Material(assetManager, 
          "Common/MatDefs/Misc/Unshaded.j3md");
          Earth.setMaterial(EarthMat);
          EarthMat.setTexture("ColorMap", 
            assetManager.loadTexture("Models/Earth/Earth.jpg"));
          Earth.setLocalTranslation(0,0,8);
          Earth.setUserData("health", 100);
          rootNode.attachChild(Earth);
          
          /** Must add a light to make the lit object visible! */
          DirectionalLight sun = new DirectionalLight();
          sun.setDirection(new Vector3f(1,0,-2).normalizeLocal());
          sun.setColor(ColorRGBA.White);
          rootNode.addLight(sun);
          
          //cam.setLocation(Vector3f.ZERO); //Puts camera at (0,0,0). Default is (0,0,10).
          //cam.setRotation(Quaternion.IDENTITY); //Default is (0,1,0,0). Unknown to me how it works
          
          guiNode.detachAllChildren();
          guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
          EarthHealth = new BitmapText(guiFont, false);
          EarthHealth.setSize(guiFont.getCharSet().getRenderedSize());
          //EarthHealth.setText("Earth's Health: " + Earth.getUserData("health"));
          EarthHealth.setLocalTranslation(300, EarthHealth.getLineHeight(), 0);
          CamPos = new BitmapText(guiFont, false);
          CamPos.setSize(guiFont.getCharSet().getRenderedSize());
          //CamPos.setText("Camera Coordinates: " + cam.getLocation() + " Camera Starting Rotation: " + cam.getRotation());
          CamPos.setLocalTranslation(300, EarthHealth.getLineHeight()+CamPos.getLineHeight(), 0);
          CamRot = new BitmapText(guiFont, false);
          CamRot.setSize(guiFont.getCharSet().getRenderedSize());
          CamRot.setLocalTranslation(300, EarthHealth.getLineHeight()+CamPos.getLineHeight()+CamRot.getLineHeight(), 0);
          guiNode.attachChild(EarthHealth);
          guiNode.attachChild(CamPos);
          guiNode.attachChild(CamRot);
      }

      @Override
      public void simpleUpdate(float tpf) {//TODO: add update code
          //System.out.println("Performing Game Update (Not Software Update)!"); //Will spam log.
          //EarthHealth.setText("Earth's Health: " + Earth.getUserData("health"));
          EarthHealth.setText("Earth's Coordinates: " + Earth.getWorldTranslation());
          CamPos.setText("Camera Coordinates: " + cam.getLocation());
          CamRot.setText("Camera Rotation: " + cam.getRotation());
      }

      @Override
      public void simpleRender(RenderManager rm) {
          //TODO: add render code
          //System.out.println("Performing Game Render!"); //Will spam log
      }
}
