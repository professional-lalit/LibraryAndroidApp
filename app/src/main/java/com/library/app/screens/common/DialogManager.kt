package com.library.app.screens.common

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.library.app.R
import com.library.app.common.Constants

/**
 * Created by Lalit Hajare, Software Engineer on 22/6/20
 * Manages the dialogs to be shown.
 * Gets the instance of dialog from 'InfoDialog' or 'PromptDialog' which extends DialogFragment class.
 * @see com.library.app.screens.common.InfoDialog
 * @see com.library.app.screens.common.PromptDialog
 * This class 'DialogManager' also provides if the dialog with particular TAG is shown,
 * so the repeated dialogs are not shown when config changes occur
 */
class DialogManager constructor(val mContext: Context) {

    /**
     * Required to maintain registry of `shown` DialogFragments
     */
    private var fragmentManager: FragmentManager? = null

    /**
     * Identifiers used to avoid repetitive dialogs on screen.
     * These identifiers are used as `tag` while showing `DialogFragment`.
     */
    object DialogIdentifiers {
        const val DIALOG_FORGOT_PASSWORD_SUCCESS = "forgot_password_success_prompt"
        const val DIALOG_FORGOT_PASSWORD_FAILURE = "forgot_password_failure_prompt"
        const val DIALOG_CHANGE_PASSWORD_SUCCESS = "change_password_success_prompt"
        const val DIALOG_CHANGE_PASSWORD_FAILURE = "change_password_failure_prompt"
    }

    /**
     * The function to show Information Dialog, which is used to just notify user and has a single
     * neutral button on it.
     */
    private fun showInfo(
        fragMgr: FragmentManager,
        tag: String,
        title: String,
        msg: String,
        callback: () -> Unit
    ) {
        fragmentManager = fragMgr
        val bundle = Bundle()
        bundle.putString("title", title)
        bundle.putString("message", msg)
        val dialogFragment = InfoDialog.getInstance(callback)
        dialogFragment.arguments = bundle
        dialogFragment.show(fragmentManager!!, tag)
    }

    /**
     * returns whether the dialog with specific tag is already been showing on screen,
     * so that the overlapping same dialogs don't appear.
     */
    private fun isDialogShown(tag: String): Boolean {
        if (fragmentManager != null) {
            return fragmentManager!!.findFragmentByTag(tag) != null
        }
        return false
    }

    /**
     * Used when the OTP is successfully sent to the email,
     * while password recovery process
     */
    fun showForgotPasswordSuccessDialog(
        fragMgr: FragmentManager,
        message: String,
        callback: () -> Unit
    ) {
        val title = mContext.getString(R.string.password_recovery)
        val tag = DialogIdentifiers.DIALOG_FORGOT_PASSWORD_SUCCESS
        fragmentManager = fragMgr
        if (!isDialogShown(tag))
            showInfo(fragmentManager!!, tag, title, message, callback)
    }

    /**
     * Used when the OTP is not sent to the email,
     * while password recovery process
     */
    fun showForgotPasswordFailureDialog(
        fragMgr: FragmentManager,
        message: String,
        callback: () -> Unit
    ) {
        val title = mContext.getString(R.string.password_recovery)
        val tag = DialogIdentifiers.DIALOG_FORGOT_PASSWORD_FAILURE
        fragmentManager = fragMgr
        if (!isDialogShown(tag))
            showInfo(fragmentManager!!, tag, title, message, callback)
    }

    fun showChangePasswordSuccessDialog(
        fragMgr: FragmentManager,
        message: String,
        callback: () -> Unit
    ) {
        val title = mContext.getString(R.string.update_password)
        val tag = DialogIdentifiers.DIALOG_CHANGE_PASSWORD_SUCCESS
        fragmentManager = fragMgr
        if (!isDialogShown(tag))
            showInfo(fragmentManager!!, tag, title, message, callback)
    }

    fun showChangePasswordFailureDialog(
        fragMgr: FragmentManager,
        message: String,
        callback: () -> Unit
    ) {
        val title = mContext.getString(R.string.update_password)
        val tag = DialogIdentifiers.DIALOG_CHANGE_PASSWORD_FAILURE
        fragmentManager = fragMgr
        if (!isDialogShown(tag))
            showInfo(fragmentManager!!, tag, title, message, callback)
    }


}