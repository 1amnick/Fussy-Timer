package com.iamnick.timer;



public class FightAndBet {

	public boolean isWarmingUp;
	public boolean isCoolingDown;
	static int fighterCount = 0;
	static Fighter[] fighterList = new Fighter[30];

	public static void addFighter(String name, int bet){
		fighterList[fighterCount] = new Fighter(name, bet);
		fighterCount++;
	}
	public static Fighter closeBets(){
		Fighter winner = fighterList[(int) Math.random() * fighterCount];
		for(int i = 0; i<= fighterCount; i++){
			fighterList[i] = null;
		}
		return winner;
		
	}

}
