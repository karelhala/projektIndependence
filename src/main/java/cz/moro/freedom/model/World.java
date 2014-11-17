package cz.moro.freedom.model;


public class World {


    public static final int DEFAULT_WORLD_SIZE = 20;
    
    private final Cell[][] world;
    private int width = DEFAULT_WORLD_SIZE;
    private int height = DEFAULT_WORLD_SIZE;
    
    public World() {
        this(DEFAULT_WORLD_SIZE, DEFAULT_WORLD_SIZE);
    }
        
    public World(int width, int height) {
        world = new Cell[width][height];
        this.height = height;
        this.width = width;
        
        for(int x=0; x < width; x++) {
            for(int y=0; y < height; y++) {
                world[x][y] = new Cell();
            }
        }
    }
    
    public Cell getCell(int x, int y) {
        return world[x][y];
    }

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
    
}
