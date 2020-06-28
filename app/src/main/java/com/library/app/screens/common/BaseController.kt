package com.library.app.screens.common

import androidx.fragment.app.FragmentManager

/**
 * Created by Lalit Hajare, Software Engineer on 26/6/20
 * Consists common behaviour between all controllers.
 */
interface BaseController {
    fun getDialogFragmentManager(): FragmentManager
}