package com.epam.game;



public interface Game {
    void stop();
    void reset();    
    void exit();
    boolean isRunning();
    
    int getScore();
    void incrementScore();

    void setGameSpeed(GameSpeed speed);
    
    Entity getEntity();
    EntityController getEntityController();
}
