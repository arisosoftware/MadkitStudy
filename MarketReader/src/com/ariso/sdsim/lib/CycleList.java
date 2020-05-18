package com.ariso.sdsim.lib;

import com.ariso.simlib.app.Sample3;

/**
 *  this circular queue. 循环列表
 *
 * @author <a href="mailto:arisosoftware@gmail.com">Ariso software ltd</a>
 */
public class CycleList {

	private double[] arr;
	public int front, rear;
	private int enQueueCount =0;
    /** Initialize your data structure here. Set the size of the queue to be k. */
    public CycleList(int k) {
    	this.arr = new double[k];
    	this.front = -1;
    	this.rear = 0;
    }
    
    /** Insert an element into the circular queue. Return true if the operation is successful. */
    public boolean enQueue(double value) {
        if (isFull()) {
        	deQueue();
        }
    	arr[rear] = value;
        if (front == -1) {
        	front = rear;
        }
        enQueueCount++;
        rear = (rear + 1)%arr.length;
        return true;
    }
    
    /** Delete an element from the circular queue. Return true if the operation is successful. */
    public boolean deQueue() {
        if (isEmpty()) {
        	return false;
        }
       // double val = arr[front];
        front = (front+1)%arr.length;
        if (front == rear) {
        	front = -1;
        }
        return true;
    }
    
    /** Get the front item from the queue. */
    public double getFront() {
        if (isEmpty()) {
        	return -1;
        }
        return arr[front];
    }
    
    /** Get the last item from the queue. */
    public double getRear() {
        if (isEmpty()) { 
        	return -1;
        }
        return arr[(rear-1+arr.length)%arr.length];
    }
    
    public double getDeep(int i)
    {
    	if ( (i+1) >= enQueueCount || i==0)
    	{
    		return getFront();
    	}
    	
    	int v = rear - i -2 ;
    	if (v<0)
    	{
    		v = (v+arr.length ) %arr.length;
    	}
    
    	return arr[v];
    	
    }
    
    
    /** Checks whether the circular queue is empty or not. */
    public boolean isEmpty() {
        return (front == -1);
    }
    
    /** Checks whether the circular queue is full or not. */
    public boolean isFull() {
        return (front == rear);
    }


    /*
#1	1.0 1.0  1.0,1.0front0rear1
#2	1.0 1.0  1.0,2.0front0rear2
#3	1.0 1.0  1.0,3.0front0rear3
#4	2.0 1.0  1.0,4.0front0rear4
#5	3.0 2.0  1.0,5.0front0rear5
#6	4.0 3.0  1.0,6.0front0rear6
#7	5.0 4.0  1.0,7.0front0rear7
#8	6.0 5.0  1.0,8.0front0rear8
#9	7.0 6.0  1.0,9.0front0rear9
#10	8.0 7.0  1.0,10.0front0rear0
#11	9.0 8.0  2.0,11.0front1rear1
#12	10.0 9.0  3.0,12.0front2rear2
#13	11.0 10.0  4.0,13.0front3rear3
#14	12.0 11.0  5.0,14.0front4rear4
test result
    private static void Testing_main(String[] args) throws Exception {
    	CycleList cl = new CycleList(10);
    	
		for (int i=1;i<15;i++)
		{
			cl.enQueue(i);
			System.out.println("#"+i+"\t"+cl.getDeep(1) +" "+cl.getDeep(2) +"  "+cl.getFront()+","+cl.getRear() +"front"+cl.front+"rear"+cl.rear);
		}

	}
     * 
     * 
     */
  

    
}
