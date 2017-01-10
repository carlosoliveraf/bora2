package youtube.demo.youtubedemo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by oliverace on 10/01/2017.
 */

public class MensagemWrapper implements Serializable {

    private int _id;

    private String userSend;
    private String userRecei;
    private String msg;
    private Date created_at;

    public MensagemWrapper(int _id, String userSend, String userRecei, String msg, Date created_at) {
        this._id = _id;
        this.userSend = userSend;
        this.userRecei = userRecei;
        this.msg = msg;
        this.created_at = created_at;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUserSend() {
        return userSend;
    }

    public void setUserSend(String userSend) {
        this.userSend = userSend;
    }

    public String getUserRecei() {
        return userRecei;
    }

    public void setUserRecei(String userRecei) {
        this.userRecei = userRecei;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
