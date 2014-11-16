package cz.moro.freedom.service;


public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] setting = { //Zadani Sudoku - MF DNES 23.8. 2010, priloha leto
				{0, 0, 4, 0, 3, 6, 9, 2, 7},
				{1, 0, 0, 0, 0, 5, 0, 6, 0},
				{0, 0, 0, 2, 0, 0, 5, 0, 4},
				{0, 0, 5, 0, 0, 4, 0, 6, 0},
				{6, 4, 0, 0, 3, 0, 0, 8, 5},
				{0, 7, 0, 2, 0, 0, 2, 0, 0},
				{5, 0, 2, 0, 0, 1, 0, 0, 0},
				{0, 1, 0, 7, 0, 0, 0, 0, 2}
				
				};
//			for (int i = 0; i < setting.length; i++) { //pruchod pres pole poli
//			for (int j = 0; j < setting[i].length; j++) { //pruchod samotnym polem (radkem)
//			System.out.print(setting[i][j] + " "); //bez odradkovani
//			}
//			System.out.println(""); //odradkovani
//			}
//			System.out.println(setting[0][8] );
		
			
			for (int y = 8; y > 0+3; y--) {
				
				for (int x = 0; x < 9; x++) {
					 int newy = y-x;
					System.out.println("x-"+x +" new - "+ newy );
					if (newy < 0 || x >= y) {
						continue;				
					}
					System.out.print(setting[x][newy] + " "); //bez odradkovani
					
//					cell = world.getCell(x, y+x);
//					scoreold = getScoreFor(player,scoreold,cell);	
//					player = cell.getPlayer();
				}
				System.out.println(""); //odradkovani
			}
			

   }
}
