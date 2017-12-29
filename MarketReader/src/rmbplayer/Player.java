package rmbplayer;
 
import java.awt.Color;

import madkit.kernel.AbstractAgent;
import madkit.kernel.Agent;
import madkit.kernel.AgentAddress;
import madkit.message.ObjectMessage;
import madkit.message.StringMessage;

public class Player extends Agent{
	AgentAddress room = null;
	
	@Override
	protected void activate() {  
		requestRole(Room.COMMUNITY, Room.SIMU_GROUP, Room.Player_ROLE, null);
	}

	public int Id;
	public int Amount;

	public int MaxIdRange;

	@Override
	protected void live() {
	 
		while (this.isAlive()) {
			 
			if (room == null)
			{
				room = getAgentWithRole(Room.COMMUNITY, Room.SIMU_GROUP, Room.Room_ROLE);
			}
			
			ObjectMessage<Cheque>  ball = (ObjectMessage<Cheque>) waitNextMessage(1000);
			  
			pause(1000);
			
			if (this.Amount>0)
			{
				SendMoneyToSomeone();
			}
		 	
		}
	}
	
	public void SendMoneyToSomeone()
	{
		int receiverId = (int)Math.random()* this.MaxIdRange;
		Cheque c = new Cheque();
		c.Amount = 1;
		c.SenderId = this.Id;
		c.ReceiverId = receiverId;
		
		sendMessage(Room.COMMUNITY, Room.SIMU_GROUP, Room.Room_ROLE, new ObjectMessage(c));
	 
	}
	
	public Player(int i, int amount, int maxIdRange)
	{
			this.Id = i;
			this.Amount = amount;
			this.MaxIdRange = maxIdRange;
	}
	
	
}
