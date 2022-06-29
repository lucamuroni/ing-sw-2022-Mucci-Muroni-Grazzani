package it.polimi.ingsw.controller.networking;

//TODO: javadoc
class MessageTimer extends Thread{
    private final int timeToWait;
    private final static int minimumSleepTime = 500;
    private boolean timeEnded;
    private boolean killed;

    public MessageTimer(int timeToWait){
        this.timeToWait = timeToWait;
        this.timeEnded = false;
        this.killed = false;
        this.start();
    }

    @Override
    public void run() {
        int cycles;
        int fails = 0;
        if(this.timeToWait==0){
            cycles = 0;
        }else{
            cycles = this.timeToWait/this.minimumSleepTime;
        }
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
            if(this.getKillStatus()){
                i = cycles;
            }
        }
        if(!this.getKillStatus()){
            this.timerIsEnded();
        }
    }

    public synchronized boolean isTimeEnded(){
        return this.timeEnded;
    }

    private synchronized void timerIsEnded(){
        this.timeEnded = true;
    }

    public synchronized void kill(){
        this.killed = true;
    }

    private synchronized boolean getKillStatus(){
        return this.killed;
    }
}
