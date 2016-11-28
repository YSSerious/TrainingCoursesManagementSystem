/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.ukma.nc.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author michael
 */
public class AjaxResponse implements Serializable {

    private String code;
    private Map messages;

    public AjaxResponse() {
        this.messages = new HashMap();
    }

    public AjaxResponse(String code) {
        this.code = code;
        this.messages = new HashMap();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map getMessages() {
        return messages;
    }

    public void setMessages(Map messages) {
        this.messages = messages;
    }

    public void addMessage(String field, String message) {
        this.messages.put(field, message);
    }
    
}
