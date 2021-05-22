package com.finals.foodrunner

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import kotlin.properties.Delegates

class SearchView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {
    lateinit var toolbar:Toolbar


    init {
        LayoutInflater.from(context).inflate(R.layout.search_view, this, true)

         toolbar = findViewById<Toolbar>(R.id.search_toolbar)
        toolbar.inflateMenu(R.menu.search_menu)
        //Because no other menu will be there so there is no need to check any id
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.search) {
                openSearchView();
            }
            true

        }
        val closeButton = findViewById<View>(R.id.close_search_button)
        closeButton.setOnClickListener {
            if (closeButton.isVisible) {
                closeSearchView();
            }

        }

    }


    private fun openSearchView() {
        findViewById<EditText>(R.id.search_input_text).setText("")
        val searchOpenView=findViewById<RelativeLayout>(R.id.search_open_view)
        searchOpenView.visibility=View.VISIBLE
        val circularReveal= ViewAnimationUtils.createCircularReveal(
            searchOpenView,
            searchOpenView.right.toInt(),
            (searchOpenView.top.toInt()+searchOpenView.bottom.toInt())/2,
            0f,
            width.toFloat()

        )

        circularReveal.duration=300
        circularReveal.start()
        this.requestFocus()
        showKeyboard()

    }
    private fun closeSearchView(){
        val searchOpenView=findViewById<RelativeLayout>(R.id.search_open_view)
        val circularConceal = ViewAnimationUtils.createCircularReveal(
            searchOpenView,
            searchOpenView.right.toInt(),
            (searchOpenView.top.toInt()+searchOpenView.bottom.toInt())/2,
            width.toFloat(), 0f
        )
        circularConceal.duration = 300
        circularConceal.start()
        closeKeyboard()
        circularConceal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) = Unit
            override fun onAnimationCancel(animation: Animator?) = Unit
            override fun onAnimationStart(animation: Animator?) = Unit
            override fun onAnimationEnd(animation: Animator?) {
                findViewById<RelativeLayout>(R.id.search_open_view).visibility = View.INVISIBLE
                findViewById<EditText>(R.id.search_input_text).setText("")
                circularConceal.removeAllListeners()
            }
        })

    }

    fun showKeyboard() {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun closeKeyboard() {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

}