package com.example.sample_app

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.sample_app.R.id
import com.example.sample_app.R.layout.activity_main
import java.util.*


class MainActivity : AppCompatActivity() {
    val memoList: ArrayList<Memo> = arrayListOf<Memo>()
    //var memoAdapter: MemoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        val memoListView: ListView = findViewById(R.id.ListView_Memo) as ListView
        memoListView.adapter = MemoAdapter(this,memoList)

    }

    fun addMemo(v: View) {
        val d= Date()
        val m= Memo("件名Test", 1,"本文Test",d)
        memoList.add(m)
        Log.d("memolist_num",memoList.size.toString())
        val memoListView: ListView = findViewById(R.id.ListView_Memo) as ListView
        memoListView.adapter = MemoAdapter(this,memoList)
//        val memoAdapter: MemoAdapter = MemoAdapter(this,memoList)
//        memoListView.adapter=memoAdapter
//        memoAdapter.notifyDataSetChanged()
    }
}
