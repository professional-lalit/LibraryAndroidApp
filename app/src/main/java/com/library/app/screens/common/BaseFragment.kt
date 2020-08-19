package com.library.app.screens.common

import com.library.app.common.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Created by Lalit N. Hajare, Software Engineer on 28/07/2020
 */
open class BaseFragment : DaggerFragment() {

    @Inject
    lateinit var mScreenNavigator: ScreenNavigator

    @Inject
    lateinit var mDialogManager: com.dialogsmanager.module.DialogManager

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
}