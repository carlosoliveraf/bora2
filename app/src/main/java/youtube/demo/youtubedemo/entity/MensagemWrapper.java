package youtube.demo.youtubedemo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by oliverace on 10/01/2017.
 */

public class MensagemWrapper implements Serializable {

    private String _id;
    private boolean isMe;
    private String userSend;
    private String userRecei;
    private String msg;
    private String created_at;

    public MensagemWrapper(String _id, boolean isMe, String userSend, String userRecei, String msg, String created_at) {
        this._id = _id;
        this.isMe = isMe;
        this.userSend = userSend;
        this.userRecei = userRecei;
        this.msg = msg;
        this.created_at = created_at;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
