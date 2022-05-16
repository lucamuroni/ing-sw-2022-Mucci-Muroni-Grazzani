package it.polimi.ingsw.view.cli;

public class LoadingBar {
    private final int length;
    private final static int loadingSize = 3;
    private int oldSpacePosition[];

    public LoadingBar(){
        this(10);
    }

    public LoadingBar(int length){
        this.length = length;
        this.oldSpacePosition = new int[loadingSize];
        this.reset();
    }

    public void print(){
        System.out.print("[");
        for(int i = 0;i<length;i++){
            boolean flag = true;
            for(Integer num : this.oldSpacePosition){
                if(num == i){
                    flag = false;
                    System.out.print("-");
                }
            }
            if(flag){
                System.out.print("*");
            }
        }
        System.out.print("]");
        this.update();
    }

    private synchronized void update(){
        for(int i = 0;i<loadingSize;i++){
            oldSpacePosition[i]++;
        }
        if(oldSpacePosition[loadingSize]>length+loadingSize){
            reset();
        }else {
            try {
                this.wait(100);
            }catch (InterruptedException e){}
        }
    }

    private synchronized void reset() {
        for(int i = 0;i<loadingSize;i++){
            oldSpacePosition[i] = -loadingSize+i;
        }
        try {
            this.wait(300);
        } catch (InterruptedException e) {}
    }
}
