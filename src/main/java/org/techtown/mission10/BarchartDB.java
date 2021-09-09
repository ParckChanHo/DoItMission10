package org.techtown.mission10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BarchartDB extends SQLiteOpenHelper {
    // DB의 버전으로 1부터 시작하고 스키마가 변경될 때 숫자를 올린다
    private static final int DB_VERSION = 1;
    // DB 파일명
    private static final String DB_NAME = "Chart.db";

    public BarchartDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 테이블 삭제 SQL문
    private static final String SQL_DELETE = "DROP TABLE IF EXISTS chart";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table chart(" +
                " _id integer primary key autoincrement," +
                "value integer," + // 점수
                "date date)"; // 날짜

        // date date(10) ==> 원래는 2010-08-25 13:02:30 으로 나오게 되는데 date date(10)으로 설정했기 때문에
        // 2010-08-25만 저장이 된다.
        // select datetime('now','localtime'); ==>  2010-08-25 13:02:30
        db.execSQL(sql);
        // 2021-07-13

       /* db.execSQL("insert into chart(value,date) values(10,'2021-08-28')");  // yyyy-MM-dd
        db.execSQL("insert into chart(value,date) values(20,'2021-08-29')");
        db.execSQL("insert into chart(value,date) values(30,'2021-08-30')");*/
        db.execSQL("insert into chart(value,date) values(10,'2021-09-01')");
        db.execSQL("insert into chart(value,date) values(10,'2021-09-02')");
        db.execSQL("insert into chart(value,date) values(20,'2021-09-03')");
        db.execSQL("insert into chart(value,date) values(30,'2021-09-04')");
        db.execSQL("insert into chart(value,date) values(40,'2021-09-06')");
        db.execSQL("insert into chart(value,date) values(50,'2021-09-08')");
        /*db.execSQL("insert into chart(value,date) values(10,'2021-07-13')");
        db.execSQL("insert into chart(value,date) values(20,'2021-07-14')");
        db.execSQL("insert into chart(value,date) values(30,'2021-07-15')");
        db.execSQL("insert into chart(value,date) values(40,'2021-07-16')");
        db.execSQL("insert into chart(value,date) values(50,'2021-07-17')");
        db.execSQL("insert into chart(value,date) values(60,'2021-07-18')");
        //db.execSQL("insert into chart(value,date) values(70,'2021-07-19')");
        db.execSQL("insert into chart(value,date) values(80,'2021-07-20')");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // DB 스키마가 변경될 때 여기서 데이터를 백업하고
        // 테이블을 삭제 후 재생성 및 데이터 복원 등을 한다
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }
}
