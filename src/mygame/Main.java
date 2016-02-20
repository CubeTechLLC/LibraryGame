package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;

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
        Spatial teapot = assetManager.loadModel("Models/sphere/sphere.j3o");
        Material mat_default = new Material( 
            assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_default.setTexture("ColorMap", 
            assetManager.loadTexture("Textures/test.jpg"));
        teapot.setMaterial(mat_default);
        rootNode.attachChild(teapot);
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
