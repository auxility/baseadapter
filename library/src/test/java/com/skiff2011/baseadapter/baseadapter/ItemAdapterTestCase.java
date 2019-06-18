package com.skiff2011.baseadapter.baseadapter;

import androidx.annotation.NonNull;
import com.skiff2011.baseadapter.AdapterObserver;
import com.skiff2011.baseadapter.BR;
import com.skiff2011.baseadapter.BaseItemAdapter;
import com.skiff2011.baseadapter.StubItem;
import com.skiff2011.baseadapter.misc.function.Predicate;
import com.skiff2011.baseadapter.utils.ListUtils;
import com.skiff2011.baseadapter.utils.TestUtils;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static com.skiff2011.baseadapter.utils.ListUtils.listOf;
import static com.skiff2011.baseadapter.utils.TestUtils.assertThrows;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ItemAdapterTestCase {

  private BaseItemAdapter<StubItem> adapter;
  private AdapterObserver<StubItem> observer;
  private StubItem vm1;
  private StubItem vm2;
  private StubItem vm3;
  private StubItem vm4;
  private StubItem vm5;
  private StubItem vm6;
  private StubItem vm7;
  private StubItem vm8;
  private StubItem vm9;
  private StubItem vm10;

  @Before
  public void setUp() {
    vm1 = new StubItem(1);
    vm2 = new StubItem(2);
    vm3 = new StubItem(3);
    vm4 = new StubItem(4);
    vm5 = new StubItem(5);
    vm6 = new StubItem(6);
    vm7 = new StubItem(7);
    vm8 = new StubItem(8);
    vm9 = new StubItem(9);
    vm10 = new StubItem(10);
    adapter = spy(new BaseItemAdapter<>(listOf(vm1, vm2, vm3, vm4, vm5, vm6)));
    observer = spy(new AdapterObserver<>(adapter));
    adapter.registerObserver(observer);
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
        adapter.remove(adapter.getSize());
      }
    });
  }

  @Test
  public void testRemoveByIndexContent() {
    StubItem removedVm = adapter.remove(0);
    //Test correct Item removed
    assertEquals(vm1, removedVm);
    //Test vms to be displayed after removal
    assertEquals(listOf(vm2, vm3, vm4, vm5, vm6), observer.vms);
    //Test correct notify method was called
    verify(observer, times(1)).notifyOnItemRemoved(0);
    verify(adapter, times(0)).notifyPropertyChanged(BR.empty);
  }

  @Test
  public void testRemoveByIndexSizeAndEmpty() {
    int size = adapter.getSize();
    int i = size;
    while (i > 0) {
      adapter.remove(0);
      i--;
    }
    verify(adapter, times(size)).notifyPropertyChanged(BR.size);
    verify(adapter, times(1)).notifyPropertyChanged(BR.empty);
  }

  @Test
  public void testRemoveByVmContent() {
    int indexOfVm = adapter.indexOf(vm1);
    assertTrue(adapter.remove(vm1));
    assertFalse(adapter.remove(vm10));
    assertEquals(listOf(vm2, vm3, vm4, vm5, vm6), observer.vms);
    verify(observer, times(1)).notifyOnItemRemoved(indexOfVm);
    verify(observer, times(1)).notifyOnItemRemoved(anyInt());
    verify(adapter, times(0)).notifyPropertyChanged(BR.empty);
  }

  @Test
  public void testRemoveByVmSizeAndEmpty() {
    List<StubItem> vmsToRemove = new ArrayList<>(adapter.vms());
    int size = vmsToRemove.size();
    for (StubItem vm : vmsToRemove) {
      adapter.remove(vm);
    }
    verify(adapter, times(size)).notifyPropertyChanged(BR.size);
    verify(adapter, times(1)).notifyPropertyChanged(BR.empty);
  }

  @Test
  public void testRemovePredicate() {
    adapter.removeIf(new Predicate<StubItem>() {
      @Override public Boolean apply(@NonNull StubItem object) {
        return object.value % 2 == 0;
      }
    });
    verify(adapter, times(1)).notifyPropertyChanged(BR.size);
    verify(adapter, times(0)).notifyPropertyChanged(BR.empty);
    assertEquals(ListUtils.listOf(vm1, vm3, vm5), observer.vms);
  }

  @Test
  public void testRemovePredicateSizeAndEmpty() {
    adapter.removeIf(new Predicate<StubItem>() {
      @Override public Boolean apply(@NonNull StubItem object) {
        return false;
      }
    });
    verify(adapter, times(0)).notifyPropertyChanged(BR.size);
    verify(adapter, times(0)).notifyPropertyChanged(BR.empty);
    assertEquals(ListUtils.listOf(vm1, vm2, vm3, vm4, vm5, vm6), observer.vms);
    adapter.removeIf(new Predicate<StubItem>() {
      @Override public Boolean apply(@NonNull StubItem object) {
        return true;
      }
    });
    verify(adapter, times(1)).notifyPropertyChanged(BR.size);
    verify(adapter, times(1)).notifyPropertyChanged(BR.empty);
    assertTrue(observer.vms.isEmpty());
  }

  @Test
  public void testRemovePredicateDiff() {
    adapter.removeIf(new Predicate<StubItem>() {
      @Override public Boolean apply(@NonNull StubItem object) {
        return object.value % 2 == 0;
      }
    }, true);
    verify(adapter, times(1)).notifyPropertyChanged(BR.size);
    verify(adapter, times(0)).notifyPropertyChanged(BR.empty);
    assertEquals(ListUtils.listOf(vm1, vm3, vm5), observer.vms);
  }

  @Test
  public void testRemovePredicateSizeAndEmptyDiff() {
    adapter.removeIf(new Predicate<StubItem>() {
      @Override public Boolean apply(@NonNull StubItem object) {
        return false;
      }
    });
    verify(adapter, times(0)).notifyPropertyChanged(BR.size);
    verify(adapter, times(0)).notifyPropertyChanged(BR.empty);
    assertEquals(ListUtils.listOf(vm1, vm2, vm3, vm4, vm5, vm6), observer.vms);
    adapter.removeIf(new Predicate<StubItem>() {
      @Override public Boolean apply(@NonNull StubItem object) {
        return true;
      }
    }, true);
    verify(adapter, times(1)).notifyPropertyChanged(BR.size);
    verify(adapter, times(1)).notifyPropertyChanged(BR.empty);
    assertTrue(observer.vms.isEmpty());
  }

  @Test
  public void testRemoveRange() {
    assertThrows(IndexOutOfBoundsException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.removeRange(-1, 2);
      }
    });
    assertThrows(IndexOutOfBoundsException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.removeRange(2, adapter.getSize() + 1);
      }
    });
    assertThrows(IllegalArgumentException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.removeRange(2, 0);
      }
    });
    adapter.removeRange(0, 2);
    assertEquals(ListUtils.listOf(vm3, vm4, vm5, vm6), observer.vms);
    verify(adapter, times(1)).notifyPropertyChanged(BR.size);
    verify(adapter, times(0)).notifyPropertyChanged(BR.empty);
    verify(observer, times(1)).notifyOnItemRangeRemoved(0, 2);
  }

  @Test
  public void testRemoveRangeAll() {
    adapter.removeRange(0, adapter.getSize());
    assertTrue(observer.vms.isEmpty());
    verify(adapter, times(1)).notifyPropertyChanged(BR.size);
    verify(adapter, times(1)).notifyPropertyChanged(BR.empty);
  }

  @Test
  public void testClear() {
    adapter.clear();
    assertTrue(observer.vms.isEmpty());
    verify(adapter, times(1)).notifyPropertyChanged(BR.size);
    verify(adapter, times(1)).notifyPropertyChanged(BR.empty);
  }

  @Test
  public void testClearDiff() {
    adapter.clear(true);
    assertTrue(observer.vms.isEmpty());
    verify(adapter, times(1)).notifyPropertyChanged(BR.size);
    verify(adapter, times(1)).notifyPropertyChanged(BR.empty);
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
        adapter.get(adapter.getSize());
      }
    });
    StubItem vm = adapter.get(0);
    assertEquals(vm, vm1);
  }

  @Test
  public void testGetSize() {
    assertEquals(6, adapter.getSize());
  }
}
