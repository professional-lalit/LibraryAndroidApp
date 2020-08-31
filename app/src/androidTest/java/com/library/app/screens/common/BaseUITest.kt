package com.library.app.screens.common

import androidx.test.espresso.intent.Intents
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

/**
 * Created by Lalit N. Hajare, Software Engineer on 10/08/2020
 */
abstract class BaseUITest {

    protected var webServer = MockWebServer()



    protected fun initTest() {
        webServer.start(8080)
        Intents.init()
    }

    protected fun clearTest() {
        webServer.shutdown()
        Intents.release()
    }

}