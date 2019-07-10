package dev.auxility.baseadapter.sample

import androidx.lifecycle.ViewModel
import dev.auxility.baseadapter.Adapter
import dev.auxility.baseadapter.item.TitledItem

abstract class TabbedViewModel : ViewModel() {

  abstract val adapter: Adapter<TitledItem>

}