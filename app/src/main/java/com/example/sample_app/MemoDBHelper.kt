package com.example.sample_app

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
//今回扱うDB
class MemoDBHelper (var mContext: Context?) : SQLiteOpenHelper(mContext,DBName,null, DBVersion){
    //DBがなかったら以下の要件で作成
    override fun onCreate(db: SQLiteDatabase?) {
        //IDと時間は自動管理
        db?.execSQL(
            "CREATE TABLE " + DBName + " ( " +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "title TEXT not null, "+
                    "body TEXT not null, " +
                    "date TIMESTAMP DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime'))" +
                    ");")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + DBName + ";")
        onCreate(db);
    }

    companion object {

        // データベース名
        private val DBName: String = "memo_DB"
        // データベースのバージョン(2,3と挙げていくとonUpgradeメソッドが実行される)
        private val DBVersion = 1
    }

}