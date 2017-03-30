package com.b2c.rpc.exception;

import java.util.ArrayList;
import java.util.Collection;

public class BaseException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** The message key */
    private int code;

    /** The message parameters */
    private Object[] params;

    /** The detail information, expected to appear in the response payload */
    private Collection<?> details = new ArrayList<>();

    public BaseException(String message, Throwable cause, int code) {
        super(message, cause);
        setCode(code);
    }

    public BaseException(String message, int code) {
        super(message);
        setCode(code);
    }

    public BaseException(String message, int code, Object[] params) {
        super(message);
        setCode(code);
        setParams(params);
    }

    public BaseException(String message, Throwable cause, int code, Object[] params) {
        super(message, cause);
        setCode(code);
        setParams(params);
    }

    public BaseException(String message, Throwable cause, int code, Collection<?> details) {
        super(message, cause);
        setCode(code);
        setDetails(details);
    }

    public BaseException(String message, int code, Collection<?> details) {
        super(message);
        setCode(code);
        setDetails(details);
    }

    public BaseException(String message, int code, Object[] params, Collection<?> details) {
        super(message);
        setCode(code);
        setParams(params);
        setDetails(details);
    }

    public BaseException(String message, Throwable cause, int code, Object[] params, Collection<?> details) {
        super(message, cause);
        setCode(code);
        setParams(params);
        setDetails(details);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message) {
        super(message);
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object[] getParams() {
        return this.params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Collection<?> getDetails() {
        return details;
    }

    public void setDetails(Collection<?> details) {
        // cast AbstractList (from Arrays.asList) to ArrayList, so that the list can add items;
        if (details instanceof ArrayList == false) {
            this.details = new ArrayList<Object>(details);
        } else {
            this.details = details;
        }
    }

}

