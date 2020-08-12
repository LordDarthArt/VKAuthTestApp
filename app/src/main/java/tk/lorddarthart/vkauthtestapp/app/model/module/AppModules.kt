package tk.lorddarthart.vkauthtestapp.app.model.module

import org.koin.dsl.module
import tk.lorddarthart.vkauthtestapp.app.model.data.AppData

val appDataModule = module { AppData() }