/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.Random;

/**
 *
 * @author leoau
 */
public class Lilypad {
    
    private final Node rootNode;
    private final AssetManager assetManager;
    private final BulletAppState bulletAppState;
    private BetterCharacterControl lilyControl;
    private Spatial lily;
    private Node lilyNode;
    private float warpX, warpZ;
    private boolean destroyed;
    
    public Lilypad(Node rootNode, BulletAppState bulletAppState,AssetManager assetManager) {
        this.rootNode = rootNode;
        this.bulletAppState = bulletAppState;
        this.assetManager = assetManager;
    } 
    
    public void init() {
        
        destroyed = false;
        
        /* Chargement du modèle et ses textures */
        lily = assetManager.loadModel("Models/18366_Hinduism-Lotus_Flower_V1.j3o");
        lily.setMaterial(assetManager.loadMaterial( "Materials/Generated/saule.j3m"));
        
        /* Création d'une node pour le nénuphar */
        lilyNode = new Node();
        
        /* Rattachement du spatial nénuphar à sa node */
        lilyNode.attachChild(lily);
        
        /* Réglage de la taille du canard */
        lily.setLocalScale(0.5f);
        
        /* Rotation du nénuphar pour le mettre dans le bon sens */
        Quaternion roll180 = new Quaternion();
        roll180.fromAngleAxis( FastMath.PI * 280 / 180 , new Vector3f(1,0,0) );
        lily.setLocalRotation( roll180 ); 
        
        /* Creation d'un character controller */
        lilyControl = new BetterCharacterControl(0.1f,0.1f,0.1f);
        lilyNode.addControl(lilyControl);
        
        /* Spawn du nénuphar */
        warpX = new Random().nextInt(35 + 35) - 35;
        warpZ = new Random().nextInt(35 + 30) - 30; 
        lilyControl.warp(new Vector3f(warpX,0.5f,warpZ));
        lily.move(0f,0.00000001f,0f);
        lily.move(0f,0.00000001f,0f);        
                
        /* Physique */
        bulletAppState.getPhysicsSpace().add(lilyControl);
        bulletAppState.getPhysicsSpace().add(lilyNode);
        
        /* Gravité */
        lilyControl.setGravity(new Vector3f(0f,-0.000001f,0f));  
        
        /* Lumière */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        lilyNode.addLight(ambient);
        
        /* Rattachement du nénuphar à la node principale */
        rootNode.attachChild(lilyNode);
    }
    
    public void destroy() {
        lilyControl.setEnabled(false);
        rootNode.detachChild(lilyNode);
        destroyed = true;
    }
    
    public Spatial getLily() {
        return lily;
    }
    
    public boolean getDestroyed() {
        return destroyed;
    }
}
