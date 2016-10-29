package todfresser.smash.map;

public enum GameState {
	Lobby("Lobby"),
	Starting("Starting..."),
	Running("Running..."),
	Ending("Ending...");
	
	String name;
	
	private GameState(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
