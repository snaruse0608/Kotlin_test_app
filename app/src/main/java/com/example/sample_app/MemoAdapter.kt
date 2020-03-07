package com.example.sample_app

import android.annotation.SuppressLint
import com.example.sample_app.R.*
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.memo_list.view.*
import java.util.*
import kotlin.collections.ArrayList


class MemoAdapter(var context: Context, val Memos: ArrayList<Memo>) : BaseAdapter() {
    var layoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return Memos.count()
    }

    override fun getItem(position: Int): Any {
        return Memos[position]
    }

    override fun getItemId(position: Int): Long {
        return Memos[position].id
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = layoutInflater.inflate(R.layout.memo_list, parent, false)

        view.findViewById<TextView>(R.id.dateView).text = Memos[position].date_time.toString()
        view.findViewById<TextView>(R.id.titleView).text = Memos[position].title
        return view
    }

}