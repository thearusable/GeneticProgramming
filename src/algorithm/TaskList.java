/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

/**
 *
 * @author areks
 */
public class TaskList {
    
    private int count;
    private TaskData head;
    
    private void incrementCounter()
    {
        count++;
        //checking for length itp
    }
    
    public void append(TaskData obj)
    {
        if(head == null)
        {
            head = new TaskData(obj);
            incrementCounter();
            return;
        }
        
        TaskData tmpHead = head;
        
    }
    
    
}
