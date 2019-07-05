package ca.auxility.baseadapter.sample

import androidx.lifecycle.ViewModel
import ca.auxility.baseadapter.Adapter
import ca.auxility.baseadapter.item.TitledItem

abstract class TabbedViewModel : ViewModel() {

  abstract val adapter: Adapter<TitledItem>

}