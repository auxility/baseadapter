package ca.auxility.baseadapter.sample

import androidx.lifecycle.ViewModel
import ca.auxility.baseadapter.ItemAdapter
import ca.auxility.baseadapter.item.TitledItem

abstract class TabbedViewModel : ViewModel() {

  abstract val adapter: ItemAdapter<TitledItem>

}