package org.techtown.mission10;

public class BarScore {
    int _id; // 데이터베이스 _id
    int value; // 점수
    String date; // 날짜

    public BarScore(int value, String date) {
        this.value = value;
        this.date = date;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
