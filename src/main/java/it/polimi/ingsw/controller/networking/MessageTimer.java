package it.polimi.ingsw.controller.networking;

class MessageTimer extends Thread{
    private final int timeToWait;
    private boolean timeEnded;
    private boolean killed;

    public MessageTimer(int timeToWait){
        this.timeToWait = timeToWait*1000;
        this.timeEnded = false;
        this.killed = false;
    }

    @Override
    public void run() {
        try {
            sleep(this.timeToWait);
        } catch (InterruptedException e) {
            System.out.println("Could not set up timer");
            e.printStackTrace();
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
