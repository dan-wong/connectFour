package grid;

public enum Player {
	CROSS("X"), CIRCLE("O");
	
	private final String _symbol;
	
	Player(String symbol) {
		_symbol = symbol;
	}
	
	public String getSymbol() {
		return _symbol;
	}
}
