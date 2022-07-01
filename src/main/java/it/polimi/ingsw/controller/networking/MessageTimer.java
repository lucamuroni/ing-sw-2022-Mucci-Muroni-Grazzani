package it.polimi.ingsw.controller.networking;


/**
 * Class used as a Timer to reveal un-expected host shutdowns
 * @author Davide Grazzani
 */
class MessageTimer extends Thread{
    private final int timeToWait;
    private final static int minimumSleepTime = 500;
    private boolean timeEnded;
    private boolean killed;

    /**
     * Class Builder
     * @param timeToWait is the time (in milliseconds) that the timer has to wait prior to be flagged as expired
     */
    public MessageTimer(int timeToWait){
        this.timeToWait = timeToWait;
        this.timeEnded = false;
        this.killed = false;
        this.start();
    }

    /**
     * Override of the Thread run method
     */
    @Override
    public void run() {
        int cycles;
        int fails = 0;
        if(this.timeToWait==0){
            cycles = 0;
        }else{
            cycles = this.timeToWait/minimumSleepTime;
        }
        for(int i = 0; i < cycles ; i++ ){
            try {
                sleep(minimumSleepTime);
            } catch (InterruptedException e) {
                fails++;
                if(fails>=3){
                    System.out.println("Could not set up timer");
                    e.printStackTrace();
                }
            }
            if(this.getKillStatus()){
                i = cycles;
            }
        }
        if(!this.getKillStatus()){
            this.timerIsEnded();
        }
    }

    /**
     * Getter method
     * @return a boolean that represent if the timer is ended
     */
    public synchronized boolean isTimeEnded(){
        return this.timeEnded;
    }

    /**
     * Setter method that is used by the timer when time is ended
     */
    private synchronized void timerIsEnded(){
        this.timeEnded = true;
    }

    /**
     * Setter method used to kill MessageTimer thread
     */
    public synchronized void kill(){
        this.killed = true;
    }

    /**
     * Getter method used by the timer to know if the timer has been killed
     * @return a boolean that represent the status of the timer
     */
    private synchronized boolean getKillStatus(){
        return this.killed;
    }
}