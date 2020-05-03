/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author leoau
 */
public class Sound {
    
    private final Node rootNode;
    private final AssetManager assetManager;
    private AudioNode natureAudio, duckAudio1, duckAudio2;
    private int random;
    
    public Sound(Node rootNode, AssetManager assetManager) {
        this.rootNode = rootNode;
        this.assetManager = assetManager;
    } 
       
    public void init() {
        
        /* Chargement du fichier audio */
        natureAudio = new AudioNode(assetManager, "Sounds/nature.wav", DataType.Stream);
        /* Lecture en boucle */
        natureAudio.setLooping(true);
        /* Son qu'on entend de partout */
        natureAudio.setPositional(false);
        /* Réglage volume */
        natureAudio.setVolume(1);
        /* Rattachement de la node audio à la node principale */
        rootNode.attachChild(natureAudio);
        /* Lecture */
        natureAudio.play();
        
        
        duckAudio1 = new AudioNode(assetManager, "Sounds/duck1.wav", DataType.Buffer);
        /* Son directionnel */
        duckAudio1.setPositional(true);
        /* Lecture une fois */
        duckAudio1.setLooping(false);
        /* Réglage volume */
        duckAudio1.setVolume(2);
        /* Rattachement de la node audio à la node principale */
        rootNode.attachChild(duckAudio1);
        
        
        duckAudio2 = new AudioNode(assetManager, "Sounds/duck2.wav", DataType.Buffer);
        /* Son directionnel */
        duckAudio2.setPositional(true);
        /* Lecture une fois */
        duckAudio2.setLooping(false);
        /* Réglage volume */
        duckAudio2.setVolume(2);
        /* Rattachement de la node audio à la node principale */
        rootNode.attachChild(duckAudio2);
        
    }
    
    public void duckSound(Vector3f duckPos) {
        
        /* Choix aléatoire entre 2 sons */
        random = (int) (1 + (Math.random() * (3 - 1)));
        System.out.println(random);
        switch(random) {
            case 1:
                duckAudio1.setLocalTranslation(duckPos);
                duckAudio1.playInstance();
                break;
            case 2:
                duckAudio2.setLocalTranslation(duckPos);
                duckAudio2.playInstance();
                break;
        }   
    }
    
}
