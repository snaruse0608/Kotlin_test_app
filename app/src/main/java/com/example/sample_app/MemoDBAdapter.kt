package com.example.sample_app

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import kotlin.collections.ArrayList

//DB操作用処理提供クラス
class MemoDBAdapter (var mContext: Context?){
    val memoDB :MemoDBHelper
    val db: SQLiteDatabase
    init {
        //クラスコンストラクタ呼び出し時にDBを書き込み可能でオープンする
        memoDB = MemoDBHelper(mContext)
        db = memoDB.writableDatabase
    }
    //DBから取得したメモのリストをArrayListで返す
    fun getMemoList() : ArrayList<Memo> {
        val memoList: ArrayList<Memo> = arrayListOf<Memo>() //memo格納用可変長配列
        var cursor : Cursor?=null
        try {
            //メモに欲しい要素をDBにクエリして取得
            cursor=db.rawQuery("select id, title, body, date from memo_DB order by id", null)
            //クエリに成功していれば以下実行
            if (cursor != null) {
                cursor.moveToFirst() //取得したデータの先頭行に移動
                var memo:Memo?=null
                var title:String?=null
                var body:String?=null
                var date: String?=null
                var id: Int?=null

                //取得したデータの最終行までループ
                while(!cursor.isAfterLast) {
                    id = cursor.getString(0).toInt()
                    title=cursor.getString(1)
                    body=cursor.getString(2)
                    date = cursor.getString(3)
                    memo=Memo(title,id,body,date,false)
                    memoList.add(memo) //ArrayListに取得データ書き込み
                    cursor.moveToNext() //次の行に移動
                }
                //終了後カーソルを開放
                cursor.close()
            }
        } finally {
            if(cursor != null) {
                //途中で例外発生した時もカーソルは開放する
                cursor.close()
            }
        }
        return memoList
    }
    //DBへの新規メモ挿入
    fun addMemo(title:String,body:String) {
        val values=ContentValues()
        values.put("title",title)
        values.put("body",body)

        db.insertOrThrow("memo_DB",null,values)
    }
    //Idをキーにメモ削除
    fun deleteMemo(id:Int) {
        val idStr= id.toString()
        db.execSQL("DELETE FROM memo_DB WHERE id = '$idStr'")
    }
    //DBのクローズ
    fun closeDB() {
        db.close()
    }
}