package com.iamnick.timer;

public class Fighter {
	public String name;
	public int getBet() {
		return bet;
	}
	public void setBet(int bet) {
		this.bet = bet;
	}
	public String getName() {
		return name;
	}
	public int bet;
	public Fighter(String user, int bet){
		this.name = user;
		this.bet = bet;
	}
	
}
