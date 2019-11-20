package com.zhongjiang.hotel.base.keyboard.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Created by yshrsmz on 15/03/17.
 */
class KeyboardUtil internal constructor(){
    companion object {

        /**
         * Show keyboard and focus to given EditText
         *
         * @param context Context
         * @param target  EditText to focus
         */
        fun showKeyboard(context: Context?, target: EditText?) {
            if (context == null || target == null) {
                return
            }

            val imm = getInputMethodManager(context)

            imm.showSoftInput(target, InputMethodManager.SHOW_IMPLICIT)
        }

        /**
         * Show keyboard and focus to given EditText.
         * Use this method if target EditText is in Dialog.
         *
         * @param dialog Dialog
         * @param target EditText to focus
         */
        fun showKeyboardInDialog(dialog: Dialog?, target: EditText?) {
            if (dialog == null || target == null) {
                return
            }

            dialog.window?.let {
                it.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
            }
            target.requestFocus()
        }

        /**
         * hide keyboard
         *
         * @param context Context
         * @param target  View that currently has focus
         */
        fun hideKeyboard(context: Context?, target: View?) {
            if (context == null || target == null) {
                return
            }

            val imm = getInputMethodManager(context)
            imm.hideSoftInputFromWindow(target.windowToken, 0)
        }

        /**
         * hide keyboard
         *
         * @param activity Activity
         */
        fun hideKeyboard(activity: Activity) {
            val view = activity.window.decorView

            if (view != null) {
                hideKeyboard(activity, view)
            }
        }

        private fun getInputMethodManager(context: Context): InputMethodManager {
            return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        }
    }
}
