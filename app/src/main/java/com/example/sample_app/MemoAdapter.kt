package com.example.sample_app

import com.example.sample_app.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import java.text.SimpleDateFormat

//メモ表示用ListView描画アダプタ
class MemoAdapter(var context: Context, val Memos: ArrayList<Memo>) : BaseAdapter() {
    //おまじない
    var layoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    //ListViewが持つメモの件数の合計を返す
    override fun getCount(): Int {
        return Memos.count()
    }

    //指定されたインデックスのメモを返す
    override fun getItem(position: Int): Any {
        return Memos[position]
    }

    //指定されたインデックスのメモのIDを返す
    override fun getItemId(position: Int): Long {
        return Memos[position].id.toLong()
    }
    //指定されたインデックスのメモのチェックボックスの内容を返す
    fun getItemChecked(position: Int): Boolean {
        return Memos[position].delete_check
    }

    //1個1個のView表示
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //memo_list.xmlのlayoutを呼び出し
        val view = layoutInflater.inflate(R.layout.memo_list, parent, false)

        //日付と件名をListViewに表示
        view.findViewById<TextView>(R.id.dateView).text = Memos[position].date_time
        view.findViewById<TextView>(R.id.titleView).text = Memos[position].title
        //view.findViewById<CheckBox>(R.id.checkBox).isChecked=Memos[position].delete_check

        //CheckBoxにチェック情報を格納(これをやらないとListViewをスクロールするとチェックの位置がおかしいことになる)
        //(画面外のListView要素を使いまわすためスクロールして画面の外に出たViewのCheckBoxの内容が引き継がれるみたい)
        val cb = view.findViewById<CheckBox>(R.id.checkBox)
        cb.isChecked=Memos[position].delete_check
        //チェックボックスがタッチされたら、チェックボックスのチェック有無情報更新
        cb.setOnClickListener {
            Memos[position].delete_check=cb.isChecked
        }

        return view
    }

}