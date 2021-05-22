package com.sample.foodrunner.ui.main_fragment.home

import android.animation.Animator
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.finals.foodrunner.R
import com.finals.foodrunner.databinding.InnerFragmentHomeBinding

class HomeFragment : Fragment(R.layout.inner_fragment_home) {
    private lateinit var binding: InnerFragmentHomeBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)

        setHasOptionsMenu(true)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = InnerFragmentHomeBinding.bind(view)


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search) {
            openSearchView()
        }

        return true
    }


    private fun openSearchView() {
        (activity as AppCompatActivity).findViewById<EditText>(R.id.search_input_text).setText("")
        val searchOpenView =
            (activity as AppCompatActivity).findViewById<RelativeLayout>(R.id.search_open_view)
        searchOpenView.visibility = View.VISIBLE
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            searchOpenView,
            searchOpenView.right.toInt(),
            (searchOpenView.top.toInt() + searchOpenView.bottom.toInt()) / 2,
            0f,
            searchOpenView.width.toFloat()

        )

        circularReveal.duration = 300
        circularReveal.start()
        searchOpenView.requestFocus()
        showKeyboard()

    }

    private fun closeSearchView() {
        val searchOpenView =
            (activity as AppCompatActivity).findViewById<RelativeLayout>(R.id.search_open_view)
        val circularConceal = ViewAnimationUtils.createCircularReveal(
            searchOpenView,
            searchOpenView.right.toInt(),
            (searchOpenView.top.toInt() + searchOpenView.bottom.toInt()) / 2,
            searchOpenView.width.toFloat(), 0f
        )
        circularConceal.duration = 300
        circularConceal.start()
        closeKeyboard()
        circularConceal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) = Unit
            override fun onAnimationCancel(animation: Animator?) = Unit
            override fun onAnimationStart(animation: Animator?) = Unit
            override fun onAnimationEnd(animation: Animator?) {
                (activity as AppCompatActivity).findViewById<RelativeLayout>(R.id.search_open_view).visibility =
                    View.INVISIBLE
                (activity as AppCompatActivity).findViewById<EditText>(R.id.search_input_text)
                    .setText("")
                circularConceal.removeAllListeners()
            }
        })

    }

    private fun showKeyboard() {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun closeKeyboard() {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}