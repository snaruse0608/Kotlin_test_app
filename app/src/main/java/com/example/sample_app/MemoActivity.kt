package com.example.sample_app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

//メモ作成・編集用Activity
class MemoActivity : AppCompatActivity() {
    val D_title_max_length = 20 //件名の最大長
    val D_body_max_length = 500 //本文の最大長

    //Activity起動処理
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)
        //起動元(MainActivity)からの情報受信
        val intent: Intent = getIntent()
        var t =intent.getStringExtra("Title")
        //nullチェック
        if(t==null) t=""
        var b =intent.getStringExtra("Body")
        //nullチェック
        if(b==null) b=""
        val pos = intent.getIntExtra("Position",-1)

        //受信情報を元に編集前タイトル文をEdittextに設定
        val title_String: EditText=findViewById(R.id.title_text)
        if(t.length>D_title_max_length) {
            title_String.setText("")
        } else {
            title_String.setText(t)
        }
        //受信情報を元に編集前本文をEditTextに設定
        val body_String: EditText=findViewById(R.id.body_text)
        if(b.length>D_body_max_length) {
            body_String.setText("")
        } else {
            body_String.setText(b)
        }

        //メモ登録実行処理のリスナ割り当て
        val bt_exe = findViewById<View>(R.id.button_exec) as Button
        bt_exe.setOnClickListener {
            createMemo(pos)
        }

        //キャンセル処理のリスナ割り当て
        val bt_can = findViewById<View>(R.id.button_cancel) as Button
        bt_can.setOnClickListener {
            cancelMemo()
        }
    }

    //メモ登録処理
    fun createMemo(pos :Int) {
        //edittext内データを取得
        val title_String: EditText=findViewById(R.id.title_text)
        val t: String = title_String.text.toString()
        val body_String: EditText=findViewById(R.id.body_text)
        val b: String = body_String.text.toString()

        //MainActivityへ結果を返す
        val intent = Intent()
        intent.putExtra("Title", t)
        intent.putExtra("Body",b)
        intent.putExtra("Position",pos)
        //メモ登録の場合OKを返す
        setResult(Activity.RESULT_OK,intent)
        finish()
    }
    //キャンセル処理
    fun cancelMemo() {
        //MainActivityにキャンセルを返す
        val intent = Intent()
        setResult(Activity.RESULT_CANCELED,intent)
        finish()
    }
}

