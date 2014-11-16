package cz.moro.freedom.model;

public enum Character {
	SHAMAN("shaman"),NORMAL("normal");
	
private String code;
	
	Character(final String code){
		this.code= code;
	}	

	public String getCode(){
		return String.valueOf(code);
	}
}
