package com.paopeye.localexpensetracker.feature.util

import android.text.method.PasswordTransformationMethod
import android.view.View


class NumericKeyBoardTransformationMethod :
    PasswordTransformationMethod() {
    override fun getTransformation(source: CharSequence, view: View?): CharSequence {
        return source
    }
}