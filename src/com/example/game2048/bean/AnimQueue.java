package com.example.game2048.bean;

import java.util.LinkedList;
import java.util.Queue;

public class AnimQueue {
    
    // anim type
    public static final int ANIM_NONE = 0x00;
    public static final int ANIM_MOVE = 0x01;
    public static final int ANIM_CREATE = 0x02;
    public static final int ANIM_MERGE = 0x03;
    
    public Queue<AnimGrid> queue = new LinkedList<AnimGrid>();

    public void addAnim(AnimGrid anim) {
        queue.add(anim);
    }
    
    public void clearQueue() {
        queue.clear();
    }
    
    public AnimGrid getCurrentAnim() {
        return queue.peek();
    }
    
    public float getAnimDone(long currentTime) {
        AnimGrid curAnim = getCurrentAnim();
        if (curAnim == null) {
            return -1;
        } else {
            float done = (float) (currentTime - curAnim.startTime) / curAnim.delayTime;
            return done;
        }
    }
    
}
