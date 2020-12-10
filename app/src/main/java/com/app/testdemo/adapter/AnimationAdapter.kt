package com.app.testdemo.adapter

import com.app.testdemo.R
import com.app.testdemo.entity.SimpleEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class AnimationAdapter(val list: MutableList<SimpleEntity>) :
    BaseQuickAdapter<SimpleEntity, BaseViewHolder>(R.layout.adapter_simple_item, list) {

    override fun convert(holder: BaseViewHolder, item: SimpleEntity) {
        holder.setText(R.id.tvLeftName, item.name)
        holder.setText(R.id.tvRightName, item.name)
    }
}