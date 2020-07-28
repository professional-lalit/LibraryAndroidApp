package com.library.app.screens.common

import androidx.fragment.app.Fragment
import com.library.app.common.ViewModelProviderFactory
import javax.inject.Inject

/**
 * Created by Lalit N. Hajare, Software Engineer on 28/07/2020
 */
open class BaseFragment : Fragment() {

    @Inject
    lateinit var mScreenNavigator: ScreenNavigator
}