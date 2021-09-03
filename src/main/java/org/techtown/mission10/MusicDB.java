package org.techtown.mission10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MusicDB extends SQLiteOpenHelper {


    // DB의 버전으로 1부터 시작하고 스키마가 변경될 때 숫자를 올린다
    private static final int DB_VERSION = 3;
    // DB 파일명
    private static final String DB_NAME = "Music.db";

    public MusicDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 테이블 삭제 SQL문
    private static final String SQL_DELETE = "DROP TABLE IF EXISTS music";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table music(" +
                "_id integer primary key autoincrement," + // 데이터베이스의 _id 이다.
                "title text," + // 노래 제목
                "url text)";  // 유튜브 url
        db.execSQL(sql);

        db.execSQL("insert into music(title,url) values('인연','https://www.youtube.com/watch?v=kUJv343NHsU&ab_channel=KENKAMIKITAKENKAMIKITA')");
        db.execSQL("insert into music(title,url) values('스물다섯 스물하나','https://www.youtube.com/watch?v=qvJ1FHRR1n8&ab_channel=KENKAMIKITAKENKAMIKITA')");
        db.execSQL("insert into music(title,url) values('안드로이드','https://www.youtube.com/watch?v=y7mfblGo_gw&list=RDMMNOr0l9n5dSE&index=8&ab_channel=XYNSIA%EC%8B%A0%EC%8B%9C%EC%95%84XYNSIA%EC%8B%A0%EC%8B%9C%EC%95%84')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // DB 스키마가 변경될 때 여기서 데이터를 백업하고
        // 테이블을 삭제 후 재생성 및 데이터 복원 등을 한다
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }
}
