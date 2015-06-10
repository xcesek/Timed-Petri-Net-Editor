package org.pneditor.editor.time;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import org.pneditor.editor.PNEditor;
import org.pneditor.petrinet.Marking;
import org.pneditor.petrinet.Transition;

public class SimpleTimer {

    private long seconds;
    private long elapsedSeconds;
    // Timer timer;
    Timer timer2;
    boolean isActive;
    private Marking marking;
    private Transition transition;

    private class PnTimerTask extends TimerTask {

        @Override
        public void run() {
            isActive = false;
            marking.fire(transition);

            PNEditor.getRoot().repaintCanvas();
        }
    }

    private class SecondsCounter extends TimerTask {

        @Override
        public void run() {
            elapsedSeconds--;
            transition.setLabel(String.valueOf(elapsedSeconds));
            if (elapsedSeconds < 1) {
                cancel();
                marking.fire(transition);
            }
            PNEditor.getRoot().repaintCanvas();
        }
    }

    public SimpleTimer(int earliestFiringTime, int latestFiringTime) {
        seconds = determineDelaySeconds(earliestFiringTime, latestFiringTime);
        elapsedSeconds = seconds;
    }

    public void startTimer(Transition transition, Marking marking) {
        this.transition = transition;
        this.marking = marking;

        // timer = new Timer();   
        timer2 = new Timer();
        isActive = true;

        // timer.schedule(new PnTimerTask(), seconds * 1000);
        timer2.scheduleAtFixedRate(new SecondsCounter(), 0, 1000);
    }

    public void cancel() {
        isActive = false;
        timer2.cancel();
    }

    public void resetTimer() {
        if (isActive) {
            cancel();
        }
        elapsedSeconds = seconds;
    }

    public boolean isActive() {
        return isActive;
    }

    private int determineDelaySeconds(int earliestFiringTime, int latestFiringTime) {
        if (earliestFiringTime == latestFiringTime) { // deterministic
            return earliestFiringTime;
        }

        Random randomGenerator = new Random();
        return randomGenerator.nextInt(latestFiringTime - earliestFiringTime) + earliestFiringTime;
    }
}
