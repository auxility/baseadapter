package dev.auxility.baseadapter.sample

import dev.auxility.baseadapter.Adapter
import dev.auxility.baseadapter.BaseAdapter
import dev.auxility.baseadapter.item.TitledItem

class BaseAdapterSampleViewModel : TabbedViewModel() {

  override val adapter: Adapter<TitledItem> =
    BaseAdapter(
        listOf(
            BaseRVItem(),
            BasePagerItem()
        )
    )

}