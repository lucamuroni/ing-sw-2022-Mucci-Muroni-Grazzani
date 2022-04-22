package it.polimi.ingsw.controller.server.virtualView;

public abstract class Executor {
    private final VirtualViewHandler virtualViewHandler;

    public Executor(VirtualViewHandler virtualViewHandler){
        this.virtualViewHandler = virtualViewHandler;
    }

    public abstract void execute();



}
