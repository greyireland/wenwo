package com.finalx.async;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tengyu on 2016/8/3.
 */
public class EventModel {
    private EventType type;
    //触发事件的用户id，如用户登录
    private int actorId;
    private int entityType;
    private int entityId;
    //如一个用户评论另外一个用户的问题，下面表示被评论问题的用户id
    private int entityOwnerId;

    private Map<String, String> exts = new HashMap<>();

    public EventModel() {

    }

    public EventModel setExt(String key, String value) {
        exts.put(key, value);
        return this;
    }

    public EventModel(EventType type) {
        this.type = type;
    }

    public String getExt(String key) {
        return exts.get(key);
    }


    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }

    @Override
    public String toString() {
        return "EventModel{" +
                "type=" + type +
                ", actorId=" + actorId +
                ", entityType=" + entityType +
                ", entityId=" + entityId +
                ", entityOwnerId=" + entityOwnerId +
                ", exts=" + exts +
                '}';
    }
}
