package com.skiff2011.baseadapter.filterableadapter;

import com.skiff2011.baseadapter.AdapterObserver;
import com.skiff2011.baseadapter.BR;
import com.skiff2011.baseadapter.FilterableItemAdapter;
import com.skiff2011.baseadapter.TestItem;
import com.skiff2011.baseadapter.misc.ListUtils;
import com.skiff2011.baseadapter.utils.TestUtils;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static com.skiff2011.baseadapter.utils.ListUtils.listOf;
import static com.skiff2011.baseadapter.utils.TestUtils.assertThrows;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FilterableAdapterTestCase {

  private FilterableItemAdapter<TestItem> adapter;
  private AdapterObserver<TestItem> observer;
  private TestItem vm1;
  private TestItem vm2;
  private TestItem vm3;
  private TestItem vm4;
  private TestItem vm5;
  private TestItem vm6;
  private TestItem vm7;
  private TestItem vm8;
  private TestItem vm9;
  private TestItem vm10;

  private List<TestItem> generateVmsList() {
    return listOf(vm1, vm2, vm3, vm4, vm5, vm6);
  }

  @Before
  public void setUp() {
    vm1 = new TestItem(1);
    vm2 = new TestItem(2);
    vm3 = new TestItem(3);
    vm4 = new TestItem(4);
    vm5 = new TestItem(5);
    vm6 = new TestItem(6);
    vm7 = new TestItem(7);
    vm8 = new TestItem(8);
    vm9 = new TestItem(9);
    vm10 = new TestItem(10);
    adapter = spy(new FilterableItemAdapter<>(generateVmsList(), new TestPredicate()));
    observer = spy(new AdapterObserver<>(adapter));
    adapter.registerObserver(observer);
  }

  @Test
  public void testGet() {
    assertThrows(IndexOutOfBoundsException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.get(-1);
      }
    });
    assertThrows(IndexOutOfBoundsException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.get(adapter.items().size());
      }
    });
    assertThrows(IndexOutOfBoundsException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.get(ListUtils.filter(generateVmsList(),
            new TestPredicate()).size());
      }
    });
    TestItem vm = adapter.get(
        adapter.indexOf(ListUtils.filter(generateVmsList(),
            new TestPredicate()).get(0)));
    assertEquals(vm, vm1);
  }

  @Test
  public void testInitialFilter() {
    assertEquals(ListUtils.filter(generateVmsList(),
        new TestPredicate()), observer.items);
  }

  @Test
  public void testGetSize() {
    assertEquals(
        ListUtils.filter(generateVmsList(), new TestPredicate())
            .size(), adapter.getSize());
  }

  @Test
  public void testRemoveByIndexExceptions() {
    assertThrows(IndexOutOfBoundsException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.remove(-1);
      }
    });
    assertThrows(IndexOutOfBoundsException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.remove(adapter.items().size());
      }
    });
  }

  @Test
  public void testRemoveByIndexContent() {
    TestItem removedVm = adapter.remove(adapter.indexOf(vm2));
    //Test correct Item removed
    assertEquals(vm2, removedVm);
    //Test items to be displayed after removal
    assertEquals(listOf(vm1, vm3, vm5), observer.items);
    //Test correct notify method was called
    verify(observer, times(0)).notifyOnItemRemoved(anyInt());
    verify(adapter, times(0)).notifyPropertyChanged(BR.empty);
    TestItem removedVmFiltered = adapter.remove(adapter.indexOf(vm3));
    //Test correct Item removed
    assertEquals(vm3, removedVmFiltered);
    //Test items to be displayed after removal
    assertEquals(listOf(vm1, vm5), observer.items);
    //Test correct notify method was called
    verify(observer, times(1)).notifyOnItemRemoved(1);
    //verify(adapter.adapter, times(0)).notifyPropertyChanged(BR.empty);
  }

  @Test
  public void testRemoveByIndexSizeAndEmptyAll() {
    int filteredItemsSize = adapter.getSize();
    int i = adapter.items().size();
    while (i > 0) {
      adapter.remove(0);
      i--;
    }
    verify(adapter, times(filteredItemsSize)).notifyPropertyChanged(BR.size);
    verify(adapter, times(1)).notifyPropertyChanged(BR.empty);
    assertEquals(Collections.emptyList(), observer.items);
  }

  @Test
  public void testRemoveByIndexSizeAndEmptyOnlyFiltered() {
    int filteredItemsSize = adapter.getSize();
    List<TestItem> filteredVms = ListUtils.filter(adapter.items(), new TestPredicate());
    for (TestItem stubVm : filteredVms) {
      adapter.remove(adapter.indexOf(stubVm));
    }
    verify(adapter, times(filteredItemsSize)).notifyPropertyChanged(BR.size);
    verify(adapter, times(1)).notifyPropertyChanged(BR.empty);
    assertEquals(Collections.emptyList(), observer.items);
  }
}
