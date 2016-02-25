package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
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
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    BitmapText EarthHealth;
    BitmapText CamPos;
    BitmapText CamRot;
    BitmapText FPS;
    BitmapText Memory, MaxFreeMemory;
    BitmapText CamDir;
    
    Spatial Earth;
    
    //protected Geometry Earth;
    boolean debugScreen = false;
    //boolean paused = false;
    
    Runtime runtime;
    
      public static void main(String[] args) {
          System.out.println("Starting Game!");
          
          Main app = new Main();
          
          //TODO: Eventually add loading settings from a file
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
          
          app.setDisplayFps(false);
          app.setDisplayStatView(false);
          
          app.start();
      }

      @Override
      public void simpleInitApp() {
          /** I just started learning this, so a lot of the code comes straight from the wiki. */
          guiNode.detachAllChildren(); //I added this so the built-in debug info will not be shown on press of F5 key.
          System.out.println("Loading App!");
          
          runtime = Runtime.getRuntime();
          
          //Earth's Textures
          Material EarthMat = new Material(assetManager, 
            "Common/MatDefs/Light/Lighting.j3md");
          EarthMat.setTexture("DiffuseMap", 
            assetManager.loadTexture("Models/Earth/Earth.jpg"));
          EarthMat.setTexture("NormalMap", 
            assetManager.loadTexture("Models/Earth/Earth-Normal.jpg"));
          
          EarthMat.setBoolean("UseMaterialColors",true);    
          EarthMat.setColor("Diffuse",ColorRGBA.White);
          EarthMat.setColor("Specular",ColorRGBA.White);
          EarthMat.setFloat("Shininess", 64f);
          
          //Earth
          Earth = assetManager.loadModel("Models/Earth/Earth.j3o");
          Earth.setMaterial(EarthMat);
          Earth.setLocalTranslation(0,0,5);
          Earth.setUserData("health", 100);
          rootNode.attachChild(Earth);
          
          TangentBinormalGenerator.generate(Earth);
          
          DirectionalLight sun = new DirectionalLight();
          //sun.setDirection(new Vector3f(0,0,10).normalizeLocal());
          sun.setDirection(cam.getDirection());
          sun.setColor(ColorRGBA.White);
          rootNode.addLight(sun);
          
          initKeys();
      }
      
      public void initKeys() {
          inputManager.addMapping("Pause",  new KeyTrigger(KeyInput.KEY_P));
          inputManager.addMapping("Debug",   new KeyTrigger(KeyInput.KEY_F3),
                                      new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
          inputManager.addMapping("ResetCamera",   new KeyTrigger(KeyInput.KEY_F1));
          
          inputManager.addListener(actionListener,"Pause","Debug","ResetCamera");
          //inputManager.addListener(analogListener,"Left","Right");
      }
      
      private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
          if(name.equals("Pause") && !keyPressed) {
            paused = !paused;
          }
          if(name.equals("Debug") && !keyPressed) {
            debugScreen = !debugScreen;
            debugScreen();
          }
          if(name.equals("ResetCamera") && !keyPressed) {
            cam.setRotation(new Quaternion(0,1,0,0));
            cam.setLocation(new Vector3f(0,0,10));
            cam.lookAtDirection(new Vector3f(0,0,-1), new Vector3f(0,1,0)); //Direction, then Up Vector
          }
        }
      };

      //Sample Code from Tutorial
      private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {
          if(paused) {
            if (name.equals("Rotate")) {
              Earth.rotate(0, value*speed, 0);
            }
            if(name.equals("Right")) {
              Vector3f v = Earth.getLocalTranslation();
              Earth.setLocalTranslation(v.x + value*speed, v.y, v.z);
            }
            if (name.equals("Left")) {
              Vector3f v = Earth.getLocalTranslation();
              Earth.setLocalTranslation(v.x - value*speed, v.y, v.z);
            }
          } else {
            System.out.println("Press P to unpause.");
          }
        }
      };
      
      @Override
      public void simpleUpdate(float tpf) {
          if(debugScreen) { //Checks to make sure the Debug Screen is Visible
            CamPos.setText("Camera Coordinates: " + cam.getLocation());
            CamRot.setText("Camera Rotation: " + cam.getRotation());
            CamDir.setText("Camera Direction: " + cam.getDirection() + " - Camera Up Vector: " + cam.getUp());
            FPS.setText("FPS: " + 1/tpf);
            int mb = 1024*1024;
            Memory.setText("Memory: " + (runtime.totalMemory() - runtime.freeMemory())/mb + " mb/" + runtime.totalMemory()/mb + " mb");
            MaxFreeMemory.setText("Free Memory: " + runtime.freeMemory()/mb + " mb  " + "Max Memory: " + runtime.maxMemory()/mb + " mb");
          }
          Earth.rotate(0, 2*tpf, 0);
      }

      @Override
      public void simpleRender(RenderManager rm) {
          //TODO: add render code
          //System.out.println("Performing Game Render!"); //Will spam log
      }
      
      public void changeAppSettings() {
        //app = new Main();
        AppSettings settings = new AppSettings(false);
        /* Set AppSettings(true) to keep default settings which were not specified.
           Set AppSettings(false) to load user settings from previous launches if not specified */
        
        AppSettings gameSettings = null;
        gameSettings = new AppSettings(false);
        gameSettings.setResolution(1280, 720);
        gameSettings.setFullscreen(false);
        gameSettings.setVSync(false);
        gameSettings.setTitle("Stellar Conquest");
        gameSettings.setUseInput(true);
        gameSettings.setFrameRate(500);
        gameSettings.setSamples(0);
        gameSettings.setRenderer("LWJGL-OpenGL2");
        //app.settings = gameSettings;        
        //app.setShowSettings(false);
        
        //app.setSettings(gameSettings);
        //app.restart();
      }
      
      public void debugScreen() {
          if(debugScreen) {
            guiNode.detachAllChildren();
            guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
            
            CamPos = new BitmapText(guiFont, false);
            CamRot = new BitmapText(guiFont, false);
            CamDir = new BitmapText(guiFont, false);
            FPS = new BitmapText(guiFont, false);
            Memory = new BitmapText(guiFont, false);
            MaxFreeMemory = new BitmapText(guiFont, false);
            
            CamPos.setSize(guiFont.getCharSet().getRenderedSize());
            CamRot.setSize(guiFont.getCharSet().getRenderedSize());
            CamDir.setSize(guiFont.getCharSet().getRenderedSize());
            FPS.setSize(guiFont.getCharSet().getRenderedSize());
            Memory.setSize(guiFont.getCharSet().getRenderedSize());
            MaxFreeMemory.setSize(guiFont.getCharSet().getRenderedSize());
            
            //TODO: Change Location of The Text on The Debug Screen
            CamPos.setLocalTranslation(0, CamPos.getLineHeight(), 0);
            CamRot.setLocalTranslation(0, CamPos.getLineHeight()+CamRot.getLineHeight(), 0);
            CamDir.setLocalTranslation(0, CamDir.getLineHeight()+CamPos.getLineHeight()+CamRot.getLineHeight(), 0);
            FPS.setLocalTranslation(0, FPS.getLineHeight()+CamDir.getLineHeight()+CamPos.getLineHeight()+CamRot.getLineHeight(), 0);
            Memory.setLocalTranslation(0, Memory.getLineHeight()+FPS.getLineHeight()+CamDir.getLineHeight()+CamPos.getLineHeight()+CamRot.getLineHeight(), 0);
            MaxFreeMemory.setLocalTranslation(0, MaxFreeMemory.getLineHeight()+Memory.getLineHeight()+FPS.getLineHeight()+CamDir.getLineHeight()+CamPos.getLineHeight()+CamRot.getLineHeight(), 0);
            
            guiNode.attachChild(CamPos);
            guiNode.attachChild(CamRot);
            guiNode.attachChild(CamDir);
            guiNode.attachChild(FPS);
            guiNode.attachChild(Memory);
            guiNode.attachChild(MaxFreeMemory);
            
            //changeAppSettings();
          } else {
            guiNode.detachAllChildren();
          }
      }
}

/*
 * runtime.totalMemory() = Amount of Memory Available to the JVM at the Time
 * runtime.maxMemory() = Is the Maximum Amount of Memory that Can be Allocated to the JVM
 * runtime.freeMemory() = Is the Amount of Memory Still Not Used that is Available to the JVM.
 * Total Memory - Free Memory = The Amount of Memory Currently Used
 */