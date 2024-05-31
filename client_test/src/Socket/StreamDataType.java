package Socket;

public class StreamDataType {
	
	public static final Integer LOGIN = 1;
	public static final Integer SIGNUP = 2;
	public static final Integer FIND_MATCH = 3;
	public static final Integer CANCEL_FIND_MATCH = 4;
	public static final Integer ACCEPT_MATCH = 5;
	public static final Integer REJECT_MATCH = 6;
	public static final Integer WATCH_PROFILE = 7;
	public static final Integer EDIT_PROFILE = 8;
	public static final Integer SEND_MESSAGE = 9;

	public static final Integer GAME_EVENT = 10;

	public static final Integer EXIT = 11;
	public static final Integer UNKNOWN = 12;
	
	public static Integer getTypeFromData(String receivedData) {
		String type = receivedData.split("/")[0];
		return getType(type);
	}

	public static Integer getType(String receivedData) {
		Integer dataType = StreamDataType.UNKNOWN;
		try {
			dataType = Integer.valueOf(receivedData);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dataType;
	}
}

