package com.cwj.we.base;

import java.io.Serializable;

/**
 * Description : BaseBean 实体类的基类
 *
 * @author XuCanyou666
 * @date 2020/2/7
 */


public class BaseBean<T> implements Serializable {


    /**
     * data :
     * errorCode : 0
     * errorMsg :
     */

    public int code;
    public String msg;
    public T data;

    public BaseBean(int code, String data) {
        this.code = code;
        this.data = (T) data;
    }
}
