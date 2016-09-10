package com.example.game2048.bean;

public class AnimEvent {
    
    public boolean doAnim;
    public int[] extras;
    
    public AnimEvent() {
        this.doAnim = false;
        this.extras = null;
    }

    public AnimEvent(boolean doAnim, int[] extras) {
        this.doAnim = doAnim;
        this.extras = extras;
    }
    
    public void setAnim(boolean doAnim, int[] extras) {
        this.doAnim = doAnim;
        this.extras = extras;
    }
    
    public void clearEvent() {
        this.doAnim = false;
        this.extras = null;
    }
    
}
