package it.polimi.ingsw.controller.networking;

class MessageTimer extends Thread{
    private final int timeToWait;
    private final static int minimumSleepTime = 500;
    private boolean timeEnded;
    private boolean killed;

    public MessageTimer(int timeToWait){
        this.timeToWait = timeToWait;
        this.timeEnded = false;
        this.killed = false;
    }

    @Override
    public void run() {
        int cycles;
        int fails = 0;
        cycles = this.timeToWait/this.minimumSleepTime;
        for(int i = 0; i < cycles ; i++ ){
            try {
                sleep(this.minimumSleepTime);
            } catch (InterruptedException e) {
                fails++;
                if(fails>=3){
                    System.out.println("Could not set up timer");
                    e.printStackTrace();
                }
            }
            if(killed){
                i = cycles;
            }
        }
        if(!killed){
            this.timerIsEnded();
        }
    }

    public boolean isTimeEnded(){
        return this.timeEnded;
    }

    private void timerIsEnded(){
        this.timeEnded = true;
    }

    public void kill(){
        this.killed = true;
    }
}
