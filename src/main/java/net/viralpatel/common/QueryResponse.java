package net.viralpatel.common;

public class QueryResponse {	
	
	private ResponseCode code = null;
	
	private String message = null;
	
	private String fullMessage = null;

	public ResponseCode getCode() {
		return code;
	}

	public void setCode(ResponseCode code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFullMessage() {
		return fullMessage;
	}

	public void setFullMessage(String fullMessage) {
		this.fullMessage = fullMessage;
	}	
	
	//
	// constructors
	//
	
	public QueryResponse(){
		this(null);
	}
	
	public QueryResponse(ResponseCode code){
		this(code, null);
	}
	
	public QueryResponse(ResponseCode code,String message){
		this(code, message, null);
	}
	
	public QueryResponse(ResponseCode code,String message, String fullMessage){
		this.code = code;
		this.message = message;
		this.fullMessage = fullMessage;
	}
	
	//
	// methods
	//
	
	/**
	 * @deprecated
	 */
	public QueryResponse fillQueryResponse(ResponseCode code){
		return fillQueryResponse(code, null);
	}
	
	/**
	 * @deprecated
	 */	
	public QueryResponse fillQueryResponse(ResponseCode code,String message){
		return fillQueryResponse(code, message, null);
	}
	
	/**
	 * @deprecated
	 */	
	public QueryResponse fillQueryResponse(ResponseCode code,String message, String fullMessage){
		this.code = code;
		this.message = message;
		this.fullMessage = fullMessage;
		return this;
	}
}
