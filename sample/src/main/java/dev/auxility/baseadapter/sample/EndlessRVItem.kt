package dev.auxility.baseadapter.sample

import android.util.Log
import dev.auxility.baseadapter.EndlessAdapter
import dev.auxility.baseadapter.item.Item
import dev.auxility.baseadapter.item.TitledItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val THRESHOLD = 10
const val PAGE_SIZE = 20
const val TOTAL_COUNT = 100

class EndlessRVItem(private val scope: CoroutineScope) : TitledItem, EndlessAdapter.OnLoadMoreListener {

    val adapter: EndlessAdapter<Item> = EndlessAdapter(THRESHOLD, this)

    override fun onLoadMore(currentSize: Int) {
        Log.d("mytag", "loading $PAGE_SIZE items starting at $currentSize from $TOTAL_COUNT")
        if (currentSize < TOTAL_COUNT) {
            adapter.isInProgress = true
            scope.launch {
                adapter.add(ProgressItem())
                delay(2000)
                adapter.remove(adapter.size - 1)
                adapter.addAll(List(PAGE_SIZE) {
                    TestItem(currentSize + it)
                })
                adapter.isInProgress = false
            }
        } else {
            adapter.isComplete = true
        }
    }

    override fun getLayoutId(): Int = R.layout.item_endless_rv

    override fun getTitle(): String = "RecyclerView"
}