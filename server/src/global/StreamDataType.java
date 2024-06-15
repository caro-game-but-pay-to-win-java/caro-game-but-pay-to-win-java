package global;

public class StreamDataType {

	public static final Integer UNKNOWN = 0;
	public static final Integer LOGIN = 1;
	public static final Integer SIGNUP = 2;
	public static final Integer FIND_MATCH = 3;
	public static final Integer CANCEL_FIND_MATCH = 4;
	public static final Integer ACCEPT_MATCH = 5;
	public static final Integer REJECT_MATCH = 6;
	public static final Integer WATCH_PROFILE = 7;
	public static final Integer EDIT_PROFILE = 8;
	public static final Integer SEND_MESSAGE = 9;
	public static final Integer EXIT = 10;
	public static final Integer GAME_EVENT_MOVE = 11;
	public static final Integer GAME_EVENT_ABLE_TO_MOVE = 12;
	public static final Integer GAME_EVENT_UNABLE_TO_MOVE = 13;
	public static final Integer GAME_EVENT_SURRENDER = 14;
	public static final Integer GAME_EVENT_WIN = 15;
	public static final Integer GAME_EVENT_LOST = 16;
	public static final Integer START_MATCHING = 17;
	public static final Integer PREMATCH_META_DATA = 18;
	public static final Integer SEND_MESSAGE_IN_MATCH = 19;
	public static final Integer GAME_EVENT_TIMEOUT = 20;
	public static final Integer LOGIN_FAILED = 21;
	public static final Integer OUT_OF_CLIENT_UI = 22;
	public static final Integer DISCONNECTED = 23;
	public static final Integer LOGOUT = 24;
	public static final Integer CREATE_ROOM = 25;
	public static final Integer CANCEL_ROOM = 26;
	public static final Integer JOIN_ROOM = 27;
	public static final Integer ROOM_NOT_FOUND = 28;
	
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
