package com.bmapleaf.network;

/**
 * Created by zxm on 2016/11/16.
 */

final class N {
    static final class url {
        static final String base = "http://www.google.com/";
        static final String network_test = "network_test.php";
    }

    static final class error {
        /*处理完成*/
        static final String E00000 = "E00000";
        /*产生错误*/
        static final String E00001 = "E00001";
        /* exception*/
        static final String SocketTimeoutException = "E100";
        static final String UnknownHostException = "E101";
    }
}
