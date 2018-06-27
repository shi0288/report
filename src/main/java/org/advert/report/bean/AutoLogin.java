package org.advert.report.bean;

import org.springframework.data.annotation.Id;

/**
 * Created by shiqm on 2018-06-14.
 */
public class AutoLogin {

    @Id
    private String id;

    private String encrypt;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
