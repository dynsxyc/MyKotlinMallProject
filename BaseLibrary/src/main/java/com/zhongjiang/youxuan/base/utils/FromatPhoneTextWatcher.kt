package com.zhongjiang.youxuan.base.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class FromatPhoneTextWatcher(var etPhone: EditText, var mMonth: ()-> Boolean) : TextWatcher {

    // 特殊下标位置
    private val PHONE_INDEX_3 = 3
    private val PHONE_INDEX_4 = 4
    private val PHONE_INDEX_8 = 8
    private val PHONE_INDEX_9 = 9

    override fun afterTextChanged(s: Editable?) {
        mMonth()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        if (s.isNullOrEmpty()) {
            return
        }
        val sb = StringBuilder()
        for (i in 0 until s.length) {
            if (i != PHONE_INDEX_3 && i != PHONE_INDEX_8 && s[i] == ' ') {
                continue
            } else {
                sb.append(s[i])
                if ((sb.length == PHONE_INDEX_4 || sb.length == PHONE_INDEX_9) && sb[sb.length - 1] != ' ') {
                    sb.insert(sb.length - 1, ' ')
                }
            }
        }
        if (sb.toString() != s.toString()) {
            var index = start + 1
            if (sb[start] == ' ') {
                if (before == 0) {
                    index++
                } else {
                    index--
                }
            } else {
                if (before == 1) {
                    index--
                }
            }
            etPhone.setText(sb.toString())
            etPhone.setSelection(index)
        }
    }
}