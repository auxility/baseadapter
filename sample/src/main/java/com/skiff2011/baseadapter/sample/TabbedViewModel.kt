package com.skiff2011.baseadapter.sample

import androidx.lifecycle.ViewModel
import com.skiff2011.baseadapter.ItemAdapter
import com.skiff2011.baseadapter.item.TitledItem

abstract class TabbedViewModel : ViewModel() {

  abstract val adapter: ItemAdapter<TitledItem>

}