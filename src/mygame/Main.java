package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import java.util.ArrayList;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    public Duck duck;
    public Lilypad lilypad;
    public Controls controls;
    public Sound sound;
    public ArrayList<Duck> duckList;
    public ArrayList<Lilypad> lilypadList;
    private Spatial geom;
    private int turn, saveTurn, lilypadListSize;
    private BulletAppState bulletAppState;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {     
        
        /* Initialisation objet Controls */
        controls = new Controls(inputManager, flyCam);
        
        /* Initialisation objet Sound */
        sound = new Sound(rootNode, assetManager);
        
        /* Compteur du nombre d'execution de la boucle principale */
        turn = 1;
        saveTurn = 1;
        
        /* On charge la scène */
        geom = assetManager.loadModel("Scenes/pond.j3o");
        
        /* Rattachement du spatial de la scène à la node principale */
        rootNode.attachChild(geom);
        
        /* Vitesse de la caméra libre à 30 */
        flyCam.setMoveSpeed(30f);
        
        /* Création d'un filtre pour l'eau */
        FilterPostProcessor water;
        water = assetManager.loadFilter("Models/water.j3f");
        viewPort.addProcessor(water);
        
        /* Variable de gestion de la physique */
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        
        /* Initialisation sons */
        sound.init();
        
        /* Création des listes pour nos objets objets */
        duckList = new ArrayList<>();
        lilypadList = new ArrayList<>();        
        
        /* Création des canards */
        for(int i = 0; i < 12; i++){
            duckList.add(new Duck(rootNode, bulletAppState, assetManager));
            duckList.get(i).init();
        }
        
        /* Création des nénuphars */
        for(int i = 0; i < 30; i++){
            lilypadList.add(new Lilypad(rootNode, bulletAppState, assetManager));
            lilypadList.get(i).init();
        }
    }

    @Override
    public void simpleUpdate(float tpf) {

        /* Passage en azerty */
        controls.init();
        
        /* Déplacement des canards */
        if(turn != 1) {
            for(int i = 0; i < duckList.size(); i++){
                duckList.get(i).move();
                for(int j = 0; j < lilypadList.size(); j++){
                    duckList.get(i).eat(lilypadList.get(j));
                    if(lilypadList.get(j).getDestroyed()) {
                        lilypadList.remove(j);    //on enleve le nénuphar mangé de la liste
                        if(duckList.get(i).getBoss()) {
                            sound.duckSound(duckList.get(i).getDuckPos());  //son pour les canards boss
                        }
                    }
                }
            }   
            
            /* Action en fonction du temps */
            if(saveTurn + 2000 < turn) {    
                
                /* Création de nouveaux nénuphars */
                lilypadListSize = lilypadList.size();
                for(int r = lilypadListSize; r < (lilypadListSize + (int) (5 + (Math.random() * (26 - 5)))) ; r++){
                    lilypadList.add(new Lilypad(rootNode, bulletAppState, assetManager));
                    lilypadList.get(r).init();
                }
                /* Perte de nourriture et retransformation des canards boss en normaux */
                for(int i = 0; i < duckList.size(); i++) {
                    if(duckList.get(i).getFood() > 0) {
                        duckList.get(i).setFood(duckList.get(i).getFood()-1);
                        if(duckList.get(i).getFood() < 5 && duckList.get(i).getBoss()) {
                            duckList.get(i).transform();
                        }
                    }   
                }
                saveTurn = turn;
            }
            
        }
        
        /* Incrémentation du compteur */
        turn += 1;
        //System.out.println(turn);
    }
        

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
