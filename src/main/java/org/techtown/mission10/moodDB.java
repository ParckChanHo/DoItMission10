package org.techtown.mission10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class moodDB extends SQLiteOpenHelper {
    // DB의 버전으로 1부터 시작하고 스키마가 변경될 때 숫자를 올린다
    private static final int DB_VERSION = 1;
    // DB 파일명
    private static final String DB_NAME = "Mood.db";

    // 테이블 삭제 SQL문
    private static final String SQL_DELETE = "DROP TABLE IF EXISTS mood";

    public moodDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table mood(" +
                " _id integer primary key autoincrement," +
                "morning integer," + // 아침 ==> 0~4까지만 들어갈 수 있음
                "lunch integer," + // 점심
                "dinner integer," + // 저녁
                "date text)"; // 날짜
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // DB 스키마가 변경될 때 여기서 데이터를 백업하고
        // 테이블을 삭제 후 재생성 및 데이터 복원 등을 한다
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }
}
