package com.app.testdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.View
import com.app.testdemo.activity.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lists.add("1")
        list2.add("name")
        list2.add("222")

        map1["token"] = "1222"
        map1["id"] = "1"
        val str = "1234票"
        val spannableString = SpannableString(str)
        val spanSize = RelativeSizeSpan(0.8f)
        spannableString.setSpan(
            spanSize,
            str.length - 2,
            str.length - 1,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        tvName.text = spannableString

    }

    private fun getName(): String = "11222"

    var array1 = intArrayOf(1, 2, 3)

    var lists = ArrayList<String>()

    var list2 = mutableListOf<String>()

    val map1 = mutableMapOf<String, String>()


    companion object {
        const val ACTIVITY_NAME = "MainActivity"
    }

    fun star(view: View) {
        love.addLiveStar()
    }

    fun startList(view: View) {
        startActivity(Intent(this, ListActivity::class.java))
    }

    fun startProgress(view: View) {
        startActivity(Intent(this, ProgressActivity::class.java))
    }

    fun startConsLayout(view: View) {
        startActivity(Intent(this, ConsLayoutActivity::class.java))
    }

    fun startPlugin(view: View) {
        startActivity(Intent(this, PluginActivity::class.java))
    }

    fun startView(view: View) {
        startActivity(Intent(this,TestViewActivity::class.java))
    }

}
