package mygame;

import com.jme3.app.SimpleApplication;
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
      public static void main(String[] args) {
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
            Sphere sphereMesh = new Sphere(32,32, 2f);
            Geometry sphereGeo = new Geometry("Planet Earth", sphereMesh);
            sphereMesh.setTextureMode(Sphere.TextureMode.Projected); // better quality on spheres
            TangentBinormalGenerator.generate(sphereMesh);               // for lighting effect
            Material sphereMat = new Material(assetManager, 
            "Common/MatDefs/Light/Lighting.j3md");
            sphereMat.setTexture("DiffuseMap", 
            assetManager.loadTexture("Models/Earth/Earth.jpg"));
            sphereMat.setTexture("NormalMap", 
            assetManager.loadTexture("Models/Earth/Earth-Normal.jpg"));
            sphereMat.setBoolean("UseMaterialColors",true);            
            sphereMat.setColor("Diffuse",ColorRGBA.White);
            sphereMat.setColor("Specular",ColorRGBA.White);
            sphereMat.setFloat("Shininess", 64f);  // [0,128]
            sphereGeo.setMaterial(sphereMat);
            sphereGeo.setLocalTranslation(0,2,-2); // Move it a bit
            sphereGeo.rotate(1.6f, 0, 0);              // Rotate it a bit
            rootNode.attachChild(sphereGeo);
 
            /** Must add a light to make the lit object visible! */
            DirectionalLight sun = new DirectionalLight();
            sun.setDirection(new Vector3f(1,0,-2).normalizeLocal());
            sun.setColor(ColorRGBA.White);
            rootNode.addLight(sun);
      }

      @Override
      public void simpleUpdate(float tpf) {
            //TODO: add update code
      }

      @Override
      public void simpleRender(RenderManager rm) {
            //TODO: add render code
      }
}
