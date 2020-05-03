/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.input.CameraInput;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;

/**
 *
 * @author leoau
 */
public class Controls {
    
    private final InputManager inputManager;
    private final FlyByCamera flyCam;
    private boolean setInput = true;
    
    public Controls(InputManager inputManager, FlyByCamera flyCam) {
        this.inputManager = inputManager;
        this.flyCam = flyCam;
    }
    
    public void init(){
        
        /* Passage de qwerty à azerty lors de la première execution de la boucle */
        if(setInput){
            inputManager.deleteMapping(CameraInput.FLYCAM_STRAFELEFT);
            inputManager.deleteMapping(CameraInput.FLYCAM_FORWARD);
            inputManager.deleteMapping(CameraInput.FLYCAM_RISE);
            inputManager.deleteMapping(CameraInput.FLYCAM_LOWER);
            inputManager.addMapping("FLYCAM_StrafeLeft", new KeyTrigger(KeyInput.KEY_Q));
            inputManager.addMapping("FLYCAM_Forward", new KeyTrigger(KeyInput.KEY_Z));
            inputManager.addMapping("FLYCAM_Lower", new KeyTrigger(KeyInput.KEY_W));
            inputManager.addMapping("FLYCAM_Rise", new KeyTrigger(KeyInput.KEY_A));
            inputManager.addListener(flyCam, new String[]{
                "FLYCAM_StrafeLeft", "FLYCAM_Forward", "FLYCAM_Lower", "FLYCAM_Rise"
            });
            setInput = false;
        }
    }
    
}
