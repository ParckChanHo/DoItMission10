package org.techtown.mission10;

public class Music {
    String title;
   // int img;
    String url;
    String _id; // 데이터베이스의 _id 이다.

    public Music(String title, String url, String _id) {
        this.title = title;
        this.url = url;
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
