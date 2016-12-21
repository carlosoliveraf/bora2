package youtube.demo.youtubedemo.entity;

import java.io.Serializable;

/**
 * Created by carloseduardoolivera on 20/12/16.
 */

public class UserEntity implements Serializable {

    private String _id;

    private String name;

    private String email;

    private String username;

    private String password;

    public UserEntity(String _id, String name, String email, String username, String password) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
