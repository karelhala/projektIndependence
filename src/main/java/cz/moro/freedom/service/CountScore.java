package cz.moro.freedom.service;

import cz.moro.freedom.model.Cell;
import cz.moro.freedom.model.Player;
import cz.moro.freedom.model.World;

public class CountScore {
	
	public static class Score {
		
		public Long firstTeamScore;
		public Long secondTeamScore;
		
		/**
		 * @param firstTeamScore
		 * @param secondTeamScore
		 */
		public Score(Long firstTeamScore, Long secondTeamScore) {
			super();
			this.firstTeamScore = firstTeamScore;
			this.secondTeamScore = secondTeamScore;
		}

		public Long getFirstTeamScore() {
			return firstTeamScore;
		}



		public Long getSecondTeamScore() {
			return secondTeamScore;
		}
		
	}

	public static Score  makeCount(World world){
		
		Score totalScore = new Score(0l,0l);
		int scoreold = 0;
		int score = 0;
		Cell cell = new Cell();
		Player playerAcct = null;
		Player playerOld = null;
		
		//prejdem hracie pole z hora dole
		for (int x=0; x < world.getWidth(); x++){
			scoreold = 0;
			score = 0; 	//na zaciatku stlpca sa pocitadlo vynuluje
			for (int y=0; y < world.getHeight(); y++){
				cell = world.getCell(x, y);
				
				if (playerOld != null){
					playerOld = playerAcct;
					playerAcct = cell.getPlayer();
					score = compareCell(playerOld, playerAcct, scoreold);
					if (score < scoreold){
						//pridaj niekomu skore
					}
					else{
						scoreold = score;
					}
				}
				else{
					playerAcct = cell.getPlayer();
				}
				
			} // koniec y
		} // koniec x
		
		return totalScore;		 
	}
	
	private static int compareCell(Player old, Player acct, int count){
		int result = count;
		
		
		return result;
	}
	

}
