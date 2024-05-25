package global;

public class GVAR {
	public static int SERVER_PORT = 5056;
	public static int MATRIX_SIZE = 19;
	
	public enum StreamDataType {
		LOGIN,
		SIGNUP,
		FIND_MATCH,
		CANCEL_FIND_MATCH,
		ACCEPT_MATCH,
		REJECT_MATCH,
		WATCH_PROFILE,
		EDIT_PROFILE,
		SEND_MESSAGE,
		
		GAME_EVENT,
		/* -> */	START,
		/* -> */	MOVE,
		/* -> */	SURRENDER,
		
		// Only Server StreamDataType
		/* -> */	WIN,
		/* -> */	TURN_TICK,
		/* -> */	MATCH_TICK,
		/* -> */	MATCH_TIMEOUT,
		/* -> */	TURN_TIMEOUT,
		
		EXIT,
		UNKNOWN
	}
	
	public static StreamDataType getTypeFromData(String receivedData) {		
		String type = receivedData.split("/")[0];
		return getType(type);
	}
	
	public static StreamDataType getType(String receivedData) {
		StreamDataType dataType = StreamDataType.UNKNOWN;
		try {
			dataType = Enum.valueOf(GVAR.StreamDataType.class, receivedData);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dataType;
	}
}