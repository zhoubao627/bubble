/**
 * @path net.bubble.common.exception.CommonException.java
 * @date 2015年1月28日
 * @author shiwen_xiao
 */
package net.bubble.common.exception;

import java.io.Serializable;


/**
 * Bubble Common操作异常
 * 2015年1月28日
 */
public class CommonException extends Exception implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3938805407657465096L;

	/**
	 * Common操作异常构造函数
	 * @param message 异常信息
	 * @param cause 异常原因
	 */
	public CommonException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Common操作异常构造函数(重载)
	 * @param message 异常信息
	 */
	public CommonException(String message) {
		super(message);
	}

	/**
	 * Common操作异常构造函数(重载)
	 * @param cause 异常原因
	 */
	public CommonException(Throwable cause) {
		super(cause);
	}
}
