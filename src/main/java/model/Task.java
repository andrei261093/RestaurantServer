package model;

import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by andreiiorga on 26/06/2017.
 */

@Entity
@Table
public class Task implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String waiterName;
    @Column(length = 1000)
    private String taskJson;
    private Boolean isDone = false;
    private int zoneID;
    private String taskType;
    private int waiterId;

    private Date onCreate;
    private Date onUpdate;

    public Task(JSONObject taskJson) {
        this.taskJson = taskJson.toString();
        this.waiterName = taskJson.getJSONObject("waiter").getString("name");
        this.waiterId = taskJson.getJSONObject("waiter").getInt("id");
        this.zoneID = taskJson.getInt("tableZone");
        this.taskType = taskJson.getString("taskType");
    }

    public Task() {
    }

    public String getTaskJson() {
        return taskJson;
    }

    public void setTaskJson(String taskJson) {
        this.taskJson = taskJson;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    public int getZoneID() {
        return zoneID;
    }

    public void setZoneID(int zoneID) {
        this.zoneID = zoneID;
    }

    public Date getOnCreate() {
        return onCreate;
    }

    public void setOnCreate(Date onCreate) {
        this.onCreate = onCreate;
    }

    public Date getOnUpdate() {
        return onUpdate;
    }

    public void setOnUpdate(Date onUpdate) {
        this.onUpdate = onUpdate;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
