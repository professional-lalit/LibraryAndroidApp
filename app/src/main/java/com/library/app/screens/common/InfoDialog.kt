package com.library.app.screens.common

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.library.app.R

/**
 * Created by Lalit Hajare, Software Engineer on 22/6/20
 */
class InfoDialog(val callback: () -> Unit) : DialogFragment() {

    private lateinit var mTitle: String
    private lateinit var mMessage: String

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        mTitle = args!!.getString("title", "")!!
        mMessage = args.getString("message", "")!!
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context!!)
            .setTitle(mTitle)
            .setMessage(mMessage)
            .setNeutralButton(
                R.string.ok
            ) { dialog, which ->
                callback.invoke()
                dismiss()
            }
            .create()
    }

    companion object {
        fun getInstance(callback: () -> Unit): InfoDialog {
            return InfoDialog(callback)
        }
    }

}