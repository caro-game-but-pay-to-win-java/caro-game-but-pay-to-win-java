package CustomComponents;

public class BtnChanChat extends RadiusButton {

	/**
	 * 
	 */
	
	private Boolean canReceivedMessage = true;
	
	private static final long serialVersionUID = 1L;
	public BtnChanChat() {
		canReceivedMessage = true;
		this.setText("CHẶN CHAT");
	}
	
	public void Click() {
		
	}
	
	public Boolean getStatus() {
		return null;
	}
	
//	public void Click() {
//		
//	}
//	
//	public Boolean getStatus() {
//		return false;
//	}
	
//	public Boolean Click() {
//		if (canReceivedMessage == false) {
//			canReceivedMessage = true;
//			this.setText("CHẶN CHAT");
//		} else {
//			canReceivedMessage = false;
//			this.setText("BỎ CHẶN CHAT");
//		}
//		return canReceivedMessage;
//	}
//	
//	public Boolean getStatus() {
//		return canReceivedMessage;
//	}
}
