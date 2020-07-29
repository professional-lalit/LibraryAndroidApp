package com.library.app.utils

import android.widget.Toast
import com.library.app.common.CustomApplication

/**
 * Created by Lalit N. Hajare, Software Engineer on 29/07/2020
 */
object Utils {
    fun showToast(msg: String) {
        Toast.makeText(CustomApplication.appInstance, msg, Toast.LENGTH_SHORT).show()
    }
}