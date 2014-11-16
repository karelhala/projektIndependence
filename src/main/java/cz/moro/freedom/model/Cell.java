package cz.moro.freedom.model;


public class Cell {

    private Player player = null;
    
    public Cell() {
        
    }
    
    public Player getPlayer() {    
        return player;
    }

    
    public void setPlayer(Player player) {    
        this.player = player;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((player == null) ? 0 : player.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        
        if (this == obj)
            return true;
        
        if (obj == null)
            return false;
        
        if (getClass() != obj.getClass())
            return false;
        
        Cell other = (Cell)obj;
        
        if (player == null) {
            if (other.player != null)
                return false;
        } else if (!player.equals(other.player))
            return false;
        return true;
    }

}
