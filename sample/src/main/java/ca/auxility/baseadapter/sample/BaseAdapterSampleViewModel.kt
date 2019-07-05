package ca.auxility.baseadapter.sample

import ca.auxility.baseadapter.Adapter
import ca.auxility.baseadapter.BaseAdapter
import ca.auxility.baseadapter.item.TitledItem

class BaseAdapterSampleViewModel : TabbedViewModel() {

  override val adapter: Adapter<TitledItem> =
    BaseAdapter(
        listOf(
            BaseRVItem(),
            BasePagerItem()
        )
    )

}