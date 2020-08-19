package com.dialogsmanager.module.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.dialogsmanager.module.R

/**
 * Created by Lalit N. Hajare, Software Engineer on 19/08/2020
 */
class ConfirmDialog(val callback: (Boolean) -> Unit) : DialogFragment() {

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
            .setCancelable(false)
            .setPositiveButton(
                R.string.yes
            ) { dialog, which ->
                callback.invoke(true)
                dismiss()
            }
            .setNegativeButton(
                R.string.no
            ) { dialog, which ->
                callback.invoke(false)
                dismiss()
            }
            .create()
    }

    companion object {
        fun getInstance(callback: (Boolean?) -> Unit): ConfirmDialog {
            return ConfirmDialog(callback)
        }
    }

}