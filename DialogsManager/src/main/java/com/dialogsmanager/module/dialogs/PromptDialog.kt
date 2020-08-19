package com.dialogsmanager.module.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.dialogsmanager.module.R


/**
 * Created by Lalit Hajare, Software Engineer on 26/6/20
 */
class PromptDialog(val callback: (String?) -> Unit) : DialogFragment() {

    private lateinit var mTitle: String
    private lateinit var mMessage: String

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        mTitle = args!!.getString("title", "")!!
        mMessage = args.getString("message", "")!!
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val edtText = EditText(context!!)
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle(mTitle)
        builder.setMessage(mMessage)
        builder.setCancelable(false)
        builder.setView(edtText)
            .setNeutralButton(
                R.string.ok
            ) { dialog, which ->
                callback.invoke(edtText.text.toString().trim())
                dismiss()
            }
        return builder.create()
    }

    companion object {
        fun getInstance(callback: (String?) -> Unit): PromptDialog {
            return PromptDialog(callback)
        }
    }

}