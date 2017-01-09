package youtube.demo.youtubedemo.entity;

import java.io.Serializable;

/**
 * Created by carloseduardoolivera on 26/12/16.
 */

public class LatLongEntity implements Serializable {

    private Double latitude;

    private Double longitude;

    public LatLongEntity(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
