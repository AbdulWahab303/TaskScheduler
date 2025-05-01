package com.example.taskscheduler;

public class TaskModel {
    private int _id;
    private String _title;
    private String _description;
    private String _datetime;
    private int _status;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_datetime() {
        return _datetime;
    }

    public void set_datetime(String _datetime) {
        this._datetime = _datetime;
    }

    public int get_status() {
        return _status;
    }

    public void set_status(int _status) {
        this._status = _status;
    }

    public TaskModel(int _id, String _title, String _description, String _datetime, int _status) {
        this._id = _id;
        this._title = _title;
        this._description = _description;
        this._datetime = _datetime;
        this._status = _status;
    }
}
