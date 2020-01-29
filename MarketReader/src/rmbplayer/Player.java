package rmbplayer;

import java.awt.Color;

import madkit.kernel.AbstractAgent;
import madkit.kernel.Agent;
import madkit.kernel.AgentAddress;
import madkit.message.ObjectMessage;
import madkit.message.StringMessage;

public class Player extends Agent implements Comparable<AbstractAgent> {
	AgentAddress room = null;

	@Override
	protected void activate() {
		requestRole(GameRoom.COMMUNITY, GameRoom.SIMU_GROUP, GameRoom.Player_ROLE, null);
	}

	public int Id;
	public int Amount;

	public int MaxIdRange;

	@Override
	protected void live() {

		while (this.isAlive()) {

			if (room == null) {
				room = getAgentWithRole(GameRoom.COMMUNITY, GameRoom.SIMU_GROUP, GameRoom.Room_ROLE);
			}

			ObjectMessage<Cheque> ball = (ObjectMessage<Cheque>) waitNextMessage(1000);

			pause(1000);

			if (this.Amount > 0) {
				SendMoneyToSomeone();
			}

		}
	}

	public void SendMoneyToSomeone() {
		int receiverId = (int) Math.random() * this.MaxIdRange;
		Cheque c = new Cheque();
		c.Amount = 1;
		c.SenderId = this.Id;
		c.ReceiverId = receiverId;

		sendMessage(GameRoom.COMMUNITY, GameRoom.SIMU_GROUP, GameRoom.Room_ROLE, new ObjectMessage(c));

	}

	public Player(int i, int amount, int maxIdRange) {
		this.Id = i;
		this.Amount = amount;
		this.MaxIdRange = maxIdRange;
	}

	/* For Ascending order */
	// @Override
	public int compareTo(Player comparestu) {
		int compareage = ((Player) comparestu).Amount;
		return this.Amount - Amount;
	}
}
