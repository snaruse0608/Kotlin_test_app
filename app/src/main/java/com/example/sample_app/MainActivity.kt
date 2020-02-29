package com.example.sample_app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.sample_app.R.id
import com.example.sample_app.R.layout.activity_main
import java.util.*

//メモリスト表示用Activity
class MainActivity : AppCompatActivity() {
    val memoList: ArrayList<Memo> = arrayListOf<Memo>() //memo格納用可変長配列
    val D_request_code = 1 //MainActivity→MemoActivityのリクエストコード
    var Id:Long = 0 //メモのID(今後SQLiteで運用予定)

    // 起動処理
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)

        //リストビューにメモ表示
        val memoListView: ListView = findViewById(R.id.ListView_Memo) as ListView
        memoListView.adapter = MemoAdapter(this,memoList)

        //新規作成ボタンにリスナ割り当て
        val bt_cre=findViewById<View>(R.id.button_create) as Button
        bt_cre.setOnClickListener {
            Id++ //Id数インクリメント
            startMemoActivity("",Id,"",-1) //Memo作成用Activityへ遷移
        }

        //作成済みメモの編集用にリスナ割り当て
        memoListView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            Id++ //Id数インクリメント
            //編集前情報取得
            val t = memoList[position].title
            val b = memoList[position].body_text

            startMemoActivity(t,id,b,position) //Memo作成用Activityへ遷移
        }

    }

    //Memo作成用Activity遷移処理
    fun startMemoActivity(t: String,i:Long,b:String,p: Int) {
        //遷移先にmemoActivity指定
        val intent = Intent(this,MemoActivity::class.java)
        //遷移先への情報の送信
        intent.putExtra("Title", t);
        intent.putExtra("Id", i);
        intent.putExtra("Body", b);
        intent.putExtra("Position",p) //編集時の編集前データ削除処理に使うためArraylist内のIndexを渡す
        //遷移先へのActivity開始通知
        startActivityForResult(intent,D_request_code)
    }

    //デバッグ用(ボタン押したらListViewにテスト用メモを追加)
//    fun addMemo(v: View) {
//        val d= Date()
//        val m= Memo("件名Test", 1,"本文Test",d)
//        memoList.add(m)
//        Log.d("memolist_num",memoList.size.toString())
//        val memoListView: ListView = findViewById(R.id.ListView_Memo) as ListView
//        memoListView.adapter = MemoAdapter(this,memoList)
//    }

    //メモ作成結果の受信処理
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //まず自分がmemoActivityに出したリクエストか確認
        if(requestCode == D_request_code) {
            //受信結果ごとに処理を変える
            if(resultCode== Activity.RESULT_OK) {
                //MemoActivityからMemo作成用の情報を取得
                var t = data!!.getStringExtra("Title")
                var b = data.getStringExtra("Body")
                val id = data.getLongExtra("Id",0)
                //Memo作成時刻を取得
                val d= Date()

                //編集前データのArrayList内indexを取得
                val pos = data.getIntExtra("Position",-1)
                //nullチェック
                if(t==null) t=""
                if(b==null) b=""
                //新規メモ作成&追加
                val m = Memo(t,id,b,d)
                memoList.add(m)
                //新規作成ではなく編集の場合編集前データを消去
                if(pos>=0) {
                    memoList.removeAt(pos)
                }
                //リスト表示を更新
                val memoListView: ListView = findViewById(R.id.ListView_Memo) as ListView
                memoListView.adapter = MemoAdapter(this,memoList)
            } else if(resultCode== Activity.RESULT_CANCELED) {
                Id-- //キャンセルの場合発行した新規IDをロールバック
            }
        }
    }
}
