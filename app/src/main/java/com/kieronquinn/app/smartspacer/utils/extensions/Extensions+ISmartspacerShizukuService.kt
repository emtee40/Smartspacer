package com.kieronquinn.app.smartspacer.utils.extensions

import android.os.RemoteException
import com.kieronquinn.app.smartspacer.IRunningAppObserver
import com.kieronquinn.app.smartspacer.ISmartspacerShizukuService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun ISmartspacerShizukuService.runningApp() = callbackFlow {
    val observer = object: IRunningAppObserver.Stub() {
        override fun onRunningAppChanged(packageName: String) {
            trySend(packageName)
        }
    }
    setProcessObserver(observer)
    awaitClose {
        try {
            setProcessObserver(null)
        }catch (e: RemoteException){
            //Process died
        }
    }
}