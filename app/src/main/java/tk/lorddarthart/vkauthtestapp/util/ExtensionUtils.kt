package tk.lorddarthart.vkauthtestapp.util

import androidx.fragment.app.Fragment
import kotlinx.coroutines.*

val Fragment.isOnBackPressable: Boolean
    get() { return this is IOnBackPressable }

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