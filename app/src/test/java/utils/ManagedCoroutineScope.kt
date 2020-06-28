package utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

/**
 * Created by Lalit Hajare, Software Engineer on 9/6/20
 */
interface ManagedCoroutineScope : CoroutineScope {
    abstract fun launch(block: suspend CoroutineScope.() -> Unit) : Job
}