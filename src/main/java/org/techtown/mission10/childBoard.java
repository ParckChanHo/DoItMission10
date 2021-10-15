package org.techtown.mission10;

public class childBoard {
    public int childId; // 댓글 데이터베이스의 childId 이다.
    public String childNickname;
    public String childDate;
    public String childContent;
    public int childAvailable; // 1은 유요한 게시물 0은 삭제된 게시물이다.
    public int parentId; // 게시판 데이터베이스의 boardId 이다.

    public childBoard(int childId, String childNickname, String childDate, String childContent, int childAvailable, int parentId) {
        this.childId = childId;
        this.childNickname = childNickname;
        this.childDate = childDate;
        this.childContent = childContent;
        this.childAvailable = childAvailable;
        this.parentId = parentId;
    }

    public childBoard(){};

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public String getChildNickname() {
        return childNickname;
    }

    public void setChildNickname(String childNickname) {
        this.childNickname = childNickname;
    }

    public String getChildDate() {
        return childDate;
    }

    public void setChildDate(String childDate) {
        this.childDate = childDate;
    }

    public String getChildContent() {
        return childContent;
    }

    public void setChildContent(String childContent) {
        this.childContent = childContent;
    }

    public int getChildAvailable() {
        return childAvailable;
    }

    public void setChildAvailable(int childAvailable) {
        this.childAvailable = childAvailable;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
