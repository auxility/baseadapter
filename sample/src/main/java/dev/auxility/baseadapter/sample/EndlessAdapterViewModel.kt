package dev.auxility.baseadapter.sample

import androidx.lifecycle.viewModelScope
import dev.auxility.baseadapter.Adapter
import dev.auxility.baseadapter.BaseAdapter
import dev.auxility.baseadapter.item.TitledItem

class EndlessAdapterViewModel : TabbedViewModel() {

    override val adapter: Adapter<TitledItem> = BaseAdapter(
            listOf(EndlessRVItem(viewModelScope))
    )
}