package ex;
/**
 * 含有下级分列列表异常，用户在删除含有下级元素的数据时报出的异常
 * @author hejt
 *
 */
public class haveNextListException extends Exception{

	private static final long serialVersionUID = 645413356129655594L;

	public haveNextListException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public haveNextListException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public haveNextListException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public haveNextListException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public haveNextListException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
