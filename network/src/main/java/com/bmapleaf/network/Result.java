package com.bmapleaf.network;

/**
 * Created by zxm on 2016/11/18.
 */

public class Result {
    /**
     * success
     * or
     * failure
     */
    public String status;
    public String reason;

    public boolean success() {
        return (status != null && status.compareTo(N.error.E00000) == 0);
    }

    public String getErrorMsg() {
        return reason;
    }

    public String getErrorCode() {
        return status;
    }
}
