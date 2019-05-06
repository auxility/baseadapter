package com.skiff2011.baseadapter.filterableadapter;

import com.skiff2011.baseadapter.AdapterObserver;
import com.skiff2011.baseadapter.BR;
import com.skiff2011.baseadapter.FilterableVmAdapter;
import com.skiff2011.baseadapter.StubVM;
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

  private FilterableVmAdapter<StubVM> adapter;
  private AdapterObserver<StubVM> observer;
  private StubVM vm1;
  private StubVM vm2;
  private StubVM vm3;
  private StubVM vm4;
  private StubVM vm5;
  private StubVM vm6;
  private StubVM vm7;
  private StubVM vm8;
  private StubVM vm9;
  private StubVM vm10;

  private List<StubVM> generateVmsList() {
    return listOf(vm1, vm2, vm3, vm4, vm5, vm6);
  }

  @Before
  public void setUp() {
    vm1 = new StubVM(1);
    vm2 = new StubVM(2);
    vm3 = new StubVM(3);
    vm4 = new StubVM(4);
    vm5 = new StubVM(5);
    vm6 = new StubVM(6);
    vm7 = new StubVM(7);
    vm8 = new StubVM(8);
    vm9 = new StubVM(9);
    vm10 = new StubVM(10);
    adapter = spy(new FilterableVmAdapter<>(generateVmsList(), new TestPredicate()));
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
        adapter.get(adapter.vms().size());
      }
    });
    assertThrows(IndexOutOfBoundsException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.get(ListUtils.filter(generateVmsList(),
            new TestPredicate()).size());
      }
    });
    StubVM vm = adapter.get(
        adapter.indexOf(ListUtils.filter(generateVmsList(),
            new TestPredicate()).get(0)));
    assertEquals(vm, vm1);
  }

  @Test
  public void testInitialFilter() {
    assertEquals(ListUtils.filter(generateVmsList(),
        new TestPredicate()), observer.vms);
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
        adapter.remove(adapter.vms().size());
      }
    });
  }

  @Test
  public void testRemoveByIndexContent() {
    StubVM removedVm = adapter.remove(adapter.indexOf(vm2));
    //Test correct VM removed
    assertEquals(vm2, removedVm);
    //Test vms to be displayed after removal
    assertEquals(listOf(vm1, vm3, vm5), observer.vms);
    //Test correct notify method was called
    verify(observer, times(0)).notifyOnItemRemoved(anyInt());
    verify(adapter, times(0)).notifyPropertyChanged(BR.empty);
    StubVM removedVmFiltered = adapter.remove(adapter.indexOf(vm3));
    //Test correct VM removed
    assertEquals(vm3, removedVmFiltered);
    //Test vms to be displayed after removal
    assertEquals(listOf(vm1, vm5), observer.vms);
    //Test correct notify method was called
    verify(observer, times(1)).notifyOnItemRemoved(1);
    //verify(adapter.adapter, times(0)).notifyPropertyChanged(BR.empty);
  }

  @Test
  public void testRemoveByIndexSizeAndEmptyAll() {
    int filteredItemsSize = adapter.getSize();
    int i = adapter.vms().size();
    while (i > 0) {
      adapter.remove(0);
      i--;
    }
    verify(adapter, times(filteredItemsSize)).notifyPropertyChanged(BR.size);
    verify(adapter, times(1)).notifyPropertyChanged(BR.empty);
    assertEquals(Collections.emptyList(), observer.vms);
  }

  @Test
  public void testRemoveByIndexSizeAndEmptyOnlyFiltered() {
    int filteredItemsSize = adapter.getSize();
    List<StubVM> filteredVms = ListUtils.filter(adapter.vms(), new TestPredicate());
    for (StubVM stubVm : filteredVms) {
      adapter.remove(adapter.indexOf(stubVm));
    }
    verify(adapter, times(filteredItemsSize)).notifyPropertyChanged(BR.size);
    verify(adapter, times(1)).notifyPropertyChanged(BR.empty);
    assertEquals(Collections.emptyList(), observer.vms);
  }
}
