package com.usu.firebasetodosapplication.util

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

object Analytics {
    fun logScreenVisit(screenName: String) {
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            param("timestamp", System.currentTimeMillis())
        }
    }

    fun logAppLaunch() {
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.APP_OPEN) {
            param("timestamp", System.currentTimeMillis())
        }
    }

    fun logTodoCreated() {
        Firebase.analytics.logEvent("todo_created") {
            param("timestamp", System.currentTimeMillis())
        }
    }
}