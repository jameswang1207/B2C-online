package com.b2c.exception;

/**
 * sql insert exception code 1000
 * @author james
 *
 */
public class SqlInsertException extends BaseException{

    private static final long serialVersionUID = 1L;

    public SqlInsertException(String message, int code) {
        super(message, code);
    }
}
