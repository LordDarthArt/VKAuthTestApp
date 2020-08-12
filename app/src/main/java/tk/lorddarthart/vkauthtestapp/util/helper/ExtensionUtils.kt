package tk.lorddarthart.vkauthtestapp.util.helper

import androidx.fragment.app.Fragment
import kotlinx.coroutines.*
import tk.lorddarthart.vkauthtestapp.util.marker.IOnBackPressable

val Fragment.isOnBackPressable: Boolean
    get() { return this is IOnBackPressable
    }

fun launch(dispatchers: CoroutineDispatcher = Dispatchers.Main, action: () -> Unit)  {
    CoroutineScope(dispatchers).launch {
        action()
    }
}

fun launchPostponed(millis: Long, dispatchers: CoroutineDispatcher = Dispatchers.Main, action: () -> Unit)  {
    CoroutineScope(dispatchers).launch {
        delay(millis)
        action()
    }
}