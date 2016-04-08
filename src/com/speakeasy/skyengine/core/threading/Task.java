
package com.speakeasy.skyengine.core.threading;

import com.speakeasy.skyengine.core.timer.Timing;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kevin Owen Burress <speakeasysky@gmail.com>
 */
public class Task {

    private PriorityLevel plevel = PriorityLevel.MEDIUM;
    protected float priority = 5f;
    private Timing timing;
    private boolean executed = false;
    private Runnable runnable;
    

    public Task() {
        
    }

    public Task(PriorityLevel plevel) {
        this.plevel = plevel;
        this.priority = (int)plevel.getPriority();
    }

    public void setPriorityLevel(PriorityLevel plevel) {
        this.plevel = plevel;
    }

    public void setPriority(float priority) {
        this.priority = priority;
    }

    public PriorityLevel getPriorityLevel() {
        return plevel;
    }

    public float getPriority() {
        return priority;
    }
    
    public void putRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public void executeTask() {
        executed = true;
    }

    public void setNotExecuted() {
        executed = false;
    }
    
    @Override
    public void finalize() {
        try {
            this.plevel = null;
            this.timing = null;
            super.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
