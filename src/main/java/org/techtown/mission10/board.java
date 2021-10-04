package org.techtown.mission10;

public class board {

    public int boardId; // 데이터베이스의 boardId 이다.
    public String boardTitle;
    public String boardNickname;
    public String boardDate;
    public String boardContent;
    public int boardAvailable; // 1은 유요한 게시물 0은 삭제된 게시물이다.

    public board(int boardId, String boardTitle, String boardNickname, String boardDate, String boardContent, int boardAvailable) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardNickname = boardNickname;
        this.boardDate = boardDate;
        this.boardContent = boardContent;
        this.boardAvailable = boardAvailable;
    }

    public board(){};


    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getBoardNickname() {
        return boardNickname;
    }

    public void setBoardNickname(String boardNickname) {
        this.boardNickname = boardNickname;
    }

    public String getBoardDate() {
        return boardDate;
    }

    public void setBoardDate(String boardDate) {
        this.boardDate = boardDate;
    }

    public String getBoardContent() {
        return boardContent;
    }

    public void setBoardContent(String boardContent) {
        this.boardContent = boardContent;
    }

    public int getBoardAvailable() {
        return boardAvailable;
    }

    public void setBoardAvailable(int boardAvailable) {
        this.boardAvailable = boardAvailable;
    }
}
