package jimpossiblemission.audio;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import jimpossiblemission.model.entity.BigBomb;
import jimpossiblemission.model.entity.Direction;
import jimpossiblemission.model.entity.Elevator;
import jimpossiblemission.model.entity.LiftFloor;
import jimpossiblemission.model.entity.Player;
import jimpossiblemission.model.entity.Robot;
import jimpossiblemission.model.entity.Robot.RobotState;
import jimpossiblemission.model.game.Game;
import jimpossiblemission.model.game.Game.GameState;

@SuppressWarnings("deprecation")
public class AudioManager implements Observer
{

    private static AudioManager instance;
    private AudioClipRegistry audioClipRegistry;

    public static AudioManager getInstance()
    {
        if (instance == null)
            instance = new AudioManager();
        return instance;
    }

    private AudioManager()
    {
        audioClipRegistry = new AudioClipRegistry();
    }

    /**
     * Method to play audio clip
     * 
     * @param filename path of
     */
    private void play(Clip clip)
    {
        if (clip == null)
            return;
        if (!clip.isRunning())
        {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    /**
     * Method to stop audio clip
     * 
     * @param filename path of
     */
    private void stop(Clip clip)
    {
        if (clip == null)
            return;
        if (clip.isRunning())
            clip.stop();

    }

    /**
     * Method to stop audio running clips
     * 
     */
    public void stopRunningClips()
    {
        audioClipRegistry.mapAudioClips.values().stream().filter(clip -> clip.isRunning()).forEach(c -> c.stop());
    }

    public void dispose()
    {
        audioClipRegistry.dispose();
    }

    /**
     * method called when an observed object notifies a change of state
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if (o == null && arg == null)
            return;
        if (o instanceof Player)
        {
            Player p = (Player) o;
            switch (p.getPlayerState()) {
                case Player.PlayerState.FallenDown:
                    play(audioClipRegistry.getAudioClip("playerFalledDown"));
                    break;
                case Player.PlayerState.Electrified:
                    play(audioClipRegistry.getAudioClip("playerElectrified"));
                    break;

                case Player.PlayerState.Running:
                    play(audioClipRegistry.getAudioClip("playerRun"));
                    break;

                default:
                    break;
            }

        } else if (o instanceof Game)
        {
            Game gt = (Game) o;
            switch (gt.getState()) {
                case GameState.START:
                    play(audioClipRegistry.getAudioClip("start"));
                    break;
                case GameState.PLAY:
//                    play(audioClipRegistry.getAudioClip("firstRoom"));
                    break;
                case GameState.GAMEOVER:
                    play(audioClipRegistry.getAudioClip("gameOver"));
                    break;
                case GameState.VICTORY:
                    play(audioClipRegistry.getAudioClip("gameWon"));
                    break;

                default:
                    break;
            }
        } else if (o instanceof Robot)
        {
            if (arg == RobotState.ATTACK)
                play(audioClipRegistry.getAudioClip("robotAttack"));
            else if (arg == RobotState.IDLE || arg == RobotState.RUN)
                play(audioClipRegistry.getAudioClip("robotMove"));

        } else if (o instanceof BigBomb)
        {
            play(audioClipRegistry.getAudioClip("bigbomb"));

        } else if (o instanceof LiftFloor)
        {
            if (arg == Direction.NONE)
                stop(audioClipRegistry.getAudioClip("lift"));
            else
                play(audioClipRegistry.getAudioClip("lift"));

        } else if (o instanceof Elevator)
        {
            if (arg == Direction.NONE)
            {
                stop(audioClipRegistry.getAudioClip("elevetorStart"));
                play(audioClipRegistry.getAudioClip("elevetorStop"));
            } else
                play(audioClipRegistry.getAudioClip("elevetorStart"));
        }

    }

    /**
     * Inner class used to map string audiofile with real file stored in resource
     * folders
     * 
     * @author Filippo Taiuti
     *
     */
    class AudioClipRegistry
    {
        private Map<String, Clip> mapAudioClips;

        private AudioClipRegistry()
        {
            mapAudioClips = new HashMap<>();
            loadAudioClip();
        }

        private void loadAudioClip()
        {
            mapAudioClips.put("start", loadClip("/Audio/Dialogues/anotherVisitor.wav"));
            mapAudioClips.put("firstRoom", loadClip("/Audio/Dialogues/destroyHim.wav"));
            mapAudioClips.put("gameOver", loadClip("/Audio/Dialogues/hahaha.wav"));
            mapAudioClips.put("gameWon", loadClip("/Audio/Dialogues/missionAccomplished.wav"));
            mapAudioClips.put("playerRun", loadClip("/Audio/Player/Running/step.wav"));
            mapAudioClips.put("playerJump", loadClip("/Audio/Player/Jumping/jump.wav"));
            mapAudioClips.put("playerElectrified", loadClip("/Audio/Player/Death/dieByZap.wav"));
            mapAudioClips.put("playerFalledDown", loadClip("/Audio/Player/Death/falling.wav"));
            mapAudioClips.put("lift", loadClip("/Audio/Elevator/lift.wav"));
            mapAudioClips.put("elevetorStart", loadClip("/Audio/Elevator/elevatorStart.wav"));
            mapAudioClips.put("elevetorStop", loadClip("/Audio/Elevator/elevatorStop.wav"));
            mapAudioClips.put("robotStand", loadClip("/Audio/Robot/Standing/droid.wav"));
            mapAudioClips.put("robotMove", loadClip("/Audio/Robot/Standing/droid.wav"));
            mapAudioClips.put("bigbomb", loadClip("/Audio/BigBomb/Standing/bigbomb.wav"));
            mapAudioClips.put("robotAttack", loadClip("/Audio/Robot/Bolt/zap.wav"));
        }

        private Clip getAudioClip(String audioName)
        {
            return mapAudioClips.get(audioName);
        }

        private Clip loadClip(String resourcePath)
        {
            try
            {
                InputStream in = AudioManager.class.getResourceAsStream(resourcePath);

                if (in == null)
                {
                    System.err.println("Error: File not found in Resource folders: " + resourcePath);
                    return null;
                }
                InputStream bufferedIn = new BufferedInputStream(in);
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                audioIn.close();
                return clip;
            } catch (FileNotFoundException e1)
            {
                e1.printStackTrace();
            } catch (IOException e1)
            {
                e1.printStackTrace();
            } catch (UnsupportedAudioFileException e1)
            {
                e1.printStackTrace();
            } catch (LineUnavailableException e1)
            {
                e1.printStackTrace();
            }
            return null;

        }

        public void dispose()
        {
            mapAudioClips.values().forEach(c ->
            {
                c.stop();
                c.close();
            });
            mapAudioClips.clear();
        }

    }
}
