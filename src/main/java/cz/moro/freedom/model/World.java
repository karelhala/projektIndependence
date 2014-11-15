package cz.moro.freedom.model;


public class World {


    public static final int DEFAULT_WORLD_SIZE = 10;
    
    private final Cell[][] world;
    
    public World() {
        this(DEFAULT_WORLD_SIZE, DEFAULT_WORLD_SIZE);
    }
        
    public World(int width, int height) {
        world = new Cell[width][height];
        
        for(int x=0; x < width; x++) {
            for(int y=0; y < height; y++) {
                world[x][y] = new Cell();
            }
        }
    }
    
    public Cell getCell(int x, int y) {
        return world[x][y];
    }
    
}
