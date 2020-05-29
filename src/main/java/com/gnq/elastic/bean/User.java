package com.gnq.elastic.bean;/**
 * @author guonanqing
 * @date 2020/5/26 16:37
 * @version 1.0
 */

import java.io.Serializable;

/**
 * @author: guonanqing
 * @desc:
 * @date: 2020/5/26
 */
public class User implements Serializable {

    private static final long serialVersionUID = 3766995167418138662L;
    private Long id;
    private String name;
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
