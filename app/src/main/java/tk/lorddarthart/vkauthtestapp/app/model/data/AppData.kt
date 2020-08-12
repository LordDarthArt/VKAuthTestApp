package tk.lorddarthart.vkauthtestapp.app.model.data

data class AppData(
        val currentUserId: String? = null
) {
    val noCurrentUser: Boolean
        get() { return currentUserId.isNullOrBlank() }
}