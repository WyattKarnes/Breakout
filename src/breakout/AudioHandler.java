package breakout;

import javax.sound.sampled.*;
import java.io.File;

public class AudioHandler {

    private File brickBreakFile = new File("res/bump.wav");
    private Clip brickClip;



    public AudioHandler(){
        //setup all necessary files
        try {
            if(brickBreakFile.exists()){
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(brickBreakFile);
                brickClip = AudioSystem.getClip();
                brickClip.open(audioInputStream);
            } else{
                System.out.println("Cannot find file");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void playBrickBreak() {
        try {
            brickClip.setFramePosition(0);
            brickClip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
