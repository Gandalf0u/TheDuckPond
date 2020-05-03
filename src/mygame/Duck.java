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
public class Duck {
    
    private final Node rootNode;
    private final AssetManager assetManager;
    private final BulletAppState bulletAppState;
    private BetterCharacterControl duckControl;
    private Spatial duck;
    private Node duckNode;
    private int direction, randNbr, food;
    private float warpX, warpZ, saveX, saveZ;
    private boolean boss;
    
    public Duck(Node rootNode, BulletAppState bulletAppState, AssetManager assetManager) {
        this.rootNode = rootNode;
        this.bulletAppState = bulletAppState;
        this.assetManager = assetManager;
    }  
    
    public void init() {
       
        /* Pas un boss */
        boss = false;
        
        /* Chargement du modèle et ses textures */
        duck = assetManager.loadModel("Models/12249_Bird_v1_L2.j3o");
        duck.setMaterial(assetManager.loadMaterial( "Materials/Generated/duck.j3m"));
        
        /* Création d'une node pour le canard */
        duckNode = new Node();
        
        /* Rattachement du spatial canard à sa node */
        duckNode.attachChild(duck);
        
        /* Réglage de la taille du canard */
        duck.setLocalScale(0.05f);
        
        /* Rotation du canard pour le mettre dans le bon sens */  
        Quaternion roll180 = new Quaternion();
        roll180.fromAngleAxis( FastMath.PI * 280 / 180 , new Vector3f(1,0,0) );
        duck.setLocalRotation( roll180 );
        
        /* Creation d'un character controller */
        duckControl = new BetterCharacterControl(1f,5f,1f);
        duckNode.addControl(duckControl);
        
        /* Spawn du canard */
        warpX = new Random().nextInt(35 + 35) - 35;
        warpZ = new Random().nextInt(35 + 30) - 30; 
        duckControl.warp(new Vector3f(warpX,-0.3f,warpZ));
        duck.move(0f,0.00000001f,0f);
        
        /* Direction de base aléatoire */
        direction = (int) (1 + (Math.random() * (4 - 1)));
                
        /* Physique */
        bulletAppState.getPhysicsSpace().add(duckControl);
        bulletAppState.getPhysicsSpace().add(duckNode);
        
        /* Gravité */
        duckControl.setGravity(new Vector3f(0f,-0.000001f,0f));   
        
        /* Lumière */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        duckNode.addLight(ambient);
               
        /* Rattachement du canard à la node principale */
        rootNode.attachChild(duckNode);
    }
    
    public void move() {
        
        /* Nombre aléatoire qui permet aux canards de changer de direction aléatoirement */
        randNbr = (int) (1 + (Math.random() * (1500 - 1)));
        
        /* Déplacement dans les 4 directions */               
        switch (direction) {
            case 1:
                duckControl.setWalkDirection(new Vector3f(0f,0f,-5f)); //haut
                duckControl.setViewDirection(new Vector3f(0f,0f,-1f)); //haut
                
                if(duck.getWorldTranslation().z < -29) {
                    direction = 2;
                }
                
                if(randNbr == 1) {
                    direction = 3;
                } else if(randNbr == 2) {
                    direction = 4;
                } else if(randNbr == 2) {
                    direction = 2;
                }
                
                break;
            case 2:
                duckControl.setWalkDirection(new Vector3f(0f,0f,5f)); //bas
                duckControl.setViewDirection(new Vector3f(0f,0f,1f)); //bas   
                
                if(duck.getWorldTranslation().z > 36) {
                    direction = 1;
                }
                                
                if(randNbr == 1) {
                    direction = 3;
                } else if(randNbr == 2) {
                    direction = 4;
                } else if(randNbr == 2) {
                    direction = 1;
                }
                
                break;
            case 3:
                duckControl.setWalkDirection(new Vector3f(5f,0f,0f)); //droite
                duckControl.setViewDirection(new Vector3f(1f,0f,0.1f)); //droite
                
                if(duck.getWorldTranslation().x > 34) {
                    direction = 4;
                }
                                
                if(randNbr == 1) {
                    direction = 1;
                } else if(randNbr == 2) {
                    direction = 2;
                } else if(randNbr == 2) {
                    direction = 4;
                }
                
                break;
            case 4:
                duckControl.setWalkDirection(new Vector3f(-5f,0f,0f)); //gauche
                duckControl.setViewDirection(new Vector3f(-1f,0f,0.1f)); //gauche
                
                if(duck.getWorldTranslation().x < -38) {
                    direction = 3;
                }
                                
                if(randNbr == 1) {
                    direction = 1;
                } else if(randNbr == 2) {
                    direction = 2;
                } else if(randNbr == 2) {
                    direction = 3;
                }
                
                break;
            default:
                break;
        }
        
        /* Sauvegarde des coordonnées du canard */
        saveX = duck.getWorldTranslation().x;
        saveZ = duck.getWorldTranslation().z;
        
        /* Affichage nombre de nénuphars mangés */
        //System.out.println(food);
        
        /* Affichage position canards */
        //System.out.println(duck.getWorldTranslation().x + " " + duck.getWorldTranslation().z);
    }
    
    public void transform() {
        
        /* Destruction du canard de base */
        this.destroy();
        
        if(this.boss){
            
            /* Chargement du modèle et ses textures */
            duck = assetManager.loadModel("Models/12249_Bird_v1_L2.j3o");
            duck.setMaterial(assetManager.loadMaterial( "Materials/Generated/duck.j3m"));  
            
            /* Création d'une node pour le canard boss */
            duckNode = new Node();

            /* Rattachement du spatial canard boss à sa node */
            duckNode.attachChild(duck);
            
            /* Réglage de la taille du canard */
            duck.setLocalScale(0.05f);
            
            /* Rotation du canard pour le mettre dans le bon sens */   
            Quaternion roll180 = new Quaternion();
            roll180.fromAngleAxis( FastMath.PI * 280 / 180 , new Vector3f(1,0,0) );
            duck.setLocalRotation( roll180 );
            
            /* Creation d'un character controller */
            duckControl = new BetterCharacterControl(1f,5f,1f);
            duckNode.addControl(duckControl);
            
            /* Spawn du canard */
            duckControl.warp(new Vector3f(saveX,-0.7f,saveZ));
            duck.move(0f,0.00000001f,0f);
            
            boss = false;
            
        } else if(!this.boss) {
            
            /* Chargement du modèle du canard boss et ses textures */
            duck = assetManager.loadModel("Models/12248_Bird_v1_L2.j3o");
            duck.setMaterial(assetManager.loadMaterial( "Materials/Generated/duckBoss.j3m"));
            
            /* Création d'une node pour le canard boss */
            duckNode = new Node();

            /* Rattachement du spatial canard boss à sa node */
            duckNode.attachChild(duck);
            
            /* Réglage de la taille du canard */
            duck.setLocalScale(0.075f);
            
            /* Rotation du canard pour le mettre dans le bon sens */   
            Quaternion roll180 = new Quaternion();
            roll180.fromAngleAxis( FastMath.PI * 280 / 180 , new Vector3f(1,0,0) );
            duck.setLocalRotation( roll180 );
            
            /* Creation d'un character controller */
            duckControl = new BetterCharacterControl(1f,5f,1f);
            duckNode.addControl(duckControl);
            
            /* Spawn du canard */
            duckControl.warp(new Vector3f(saveX,-0.3f,saveZ));
            duck.move(0f,0.00000001f,0f);
            
            boss = true;
        }       
   
        /* Physique */
        bulletAppState.getPhysicsSpace().add(duckControl);
        bulletAppState.getPhysicsSpace().add(duckNode);
        
        /* Gravité */
        duckControl.setGravity(new Vector3f(0f,-0.000001f,0f));  
        
        /* Lumière */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        duckNode.addLight(ambient);
        
        /* Rattachement du canard boss à la node principale */
        rootNode.attachChild(duckNode);
    }
    
    public void eat(Lilypad lilypad) {
                   
        /* Check de si un nénuphar est à porté */
        if((duck.getWorldTranslation().z - lilypad.getLily().getWorldTranslation().z < 3) && (duck.getWorldTranslation().z - lilypad.getLily().getWorldTranslation().z > -3) && (duck.getWorldTranslation().x - lilypad.getLily().getWorldTranslation().x < 3) && (duck.getWorldTranslation().x - lilypad.getLily().getWorldTranslation().x > -3)) {
            lilypad.destroy();
            food += 1;
            if(food == 5) {         //transformation après 5 nénuphars mangés
                this.transform();
            }    
        }                       
    }
    
    /* Destruction de la node pour que le canard disparaisse */
    public void destroy() {
        duckControl.setEnabled(false);
        rootNode.detachChild(duckNode);
    }
    
    /* Getters et Setters */
    
    public Vector3f getDuckPos() {
        return duck.getWorldTranslation();
    }
    
    public boolean getBoss() {
        return boss;
    }
    
    public int getFood() {
        return food;
    }
    
    public void setFood(int food) {
        this.food = food;
    }
    
}
