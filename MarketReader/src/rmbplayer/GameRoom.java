package rmbplayer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import madkit.bees.Bee;
import madkit.bees.BeeScheduler;
import madkit.bees.BeeViewer;
import madkit.bees.QueenBee;
 
import madkit.kernel.AbstractAgent;
import madkit.kernel.Message;
import madkit.message.EnumMessage;
import madkit.message.ObjectMessage;

public class GameRoom  extends madkit.kernel.Agent {
	
	// C / G / R 
	public static final String COMMUNITY = "RMBGameRoom";	
	public static final String SIMU_GROUP = "RMBGroup";
	public static final String Player_ROLE = "player";
	public static final String Room_ROLE = "room";
	public static final String VIEWER_ROLE = "viewer";

    // Player Number
	private static final int PLAYER_NB = 100;
 
	private ArrayList<AbstractAgent> playerList = new ArrayList<>(PLAYER_NB * 2);
	 

	@Override
	protected void activate() {
		getLogger().info(() -> "Launching bees simulation...");
		createGroup(COMMUNITY, SIMU_GROUP, false, null);
		requestRole(COMMUNITY, SIMU_GROUP, Room_ROLE, null);

		long startTime = System.nanoTime();
		launchPlayer(PLAYER_NB);
		getLogger().info(() -> "launch time : " + (System.nanoTime() - startTime));
		 
 
	}

	long ticker=0L;
	  
	@Override
	protected void live() {
		while (isAlive()) {
			
			long tickerNow = System.currentTimeMillis();
			if (tickerNow>ticker)
			{
				ticker = tickerNow+1000;
				SendViewReport();
			}
			
			
			ObjectMessage<Cheque>  ball = (ObjectMessage<Cheque>) waitNextMessage(1000);
			if (ball!=null)
			{
			   Cheque c = ball.getContent();
			   
			   Player sender = getPlayer(c.SenderId);
			   Player receiver = getPlayer(c.ReceiverId);
			   
			   if (sender.Amount - c.Amount >0)
			   {
				   sender.Amount -= c.Amount;
				   receiver.Amount +=c.Amount;
			   }			 
			} 
		}
	}
	
	void SendViewReport()
	{
		
		ArrayList<Player> reportlist = (ArrayList<Player>) this.playerList.clone();
		Collections.sort(reportlist);
		
	}
	
	
	
	Player getPlayer(int no)
	{
	   if (no<playerList.size())
		   {
		   return (Player)playerList.get(no);
		   }
	   return null;
	}

	@Override
	protected void end() {
	 
		playerList = null;
		getLogger().info("I am done. Bye !");
	}

	private void launchPlayer(int numberOfPlayer) {
		getLogger().info(() -> "Launching " + numberOfPlayer + " player");
		Player p = null;
		for (int i=0;i<numberOfPlayer;i++)
		{
			p = new Player(i,100, numberOfPlayer);
			launchAgent(p);		 
			playerList.add(p);
		}
		
		p.SendMoneyToSomeone();
		
	}
 

	public static void main(String[] args) {
		executeThisAgent(1, false, args);
	}
}
