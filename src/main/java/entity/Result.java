package entity;
import java.io.Serializable;
/**
 *  Purpose:  send info to the pages
 *  to ensure the data structure unification between front and back end, also called data protocol
 */
public class Result<T> implements Serializable{
	private boolean success; //whether the operation is succeeded
	private String message; //info that need to be sent
	private T data;         //data that need to be sent

	public Result(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	public Result(boolean success, String message, T data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
