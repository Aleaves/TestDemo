package com.app.testdemo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.testdemo.R
import com.app.testdemo.adapter.AnimationAdapter
import com.app.testdemo.entity.SimpleEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_simple_list.*

class SimpleListActivity : AppCompatActivity() {

    private lateinit var mAdapter: AnimationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_list)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = AnimationAdapter(getList())
        mAdapter.animationEnable = true
        mAdapter.isAnimationFirstOnly = false
        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInRight)
        recyclerView.adapter = mAdapter
    }

    private fun getList(): MutableList<SimpleEntity> {
        val list = mutableListOf<SimpleEntity>()
        list.add(SimpleEntity("jack"))
        list.add(SimpleEntity("kangkang"))
        list.add(SimpleEntity("maier"))
        list.add(SimpleEntity("marry"))
        list.add(SimpleEntity("simmm"))
        list.add(SimpleEntity("Doron"))
        list.add(SimpleEntity("Pyosik"))
        list.add(SimpleEntity("Chovy"))
        list.add(SimpleEntity("Deft"))
        list.add(SimpleEntity("Keria"))
        list.add(SimpleEntity("Boss"))
        list.add(SimpleEntity("AHaHaCik"))
        list.add(SimpleEntity("Normanz"))
        list.add(SimpleEntity("Gadget"))
        list.add(SimpleEntity("SaNTas"))
        return list
    }

    fun add(view: View) {
        mAdapter.list.add(SimpleEntity("ddddd"))
        mAdapter.notifyItemInserted(mAdapter.list.size )
        recyclerView.smoothScrollToPosition(mAdapter.list.size )

    }
}
