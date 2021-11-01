package org.techtown.mission10;

public class Table {
    String title;
    String content;
    String author;
    String boardnum;

    public Table(String title, String content, String author, String boardnum){
        this.title = title;
        this.content = content;
        this.author = author;
        this.boardnum = boardnum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBoardnum() {
        return boardnum;
    }

    public void setBoardnum(String boardnum) {
        this.boardnum = boardnum;
    }
}
