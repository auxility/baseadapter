package eu.theappshop.baseadapter.vmadapter;

import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.AdapterObserver;
import eu.theappshop.baseadapter.BR;
import eu.theappshop.baseadapter.StubVM;
import eu.theappshop.baseadapter.adapterv2.BaseVmAdapter;
import eu.theappshop.baseadapter.adapterv2.Predicate;
import eu.theappshop.baseadapter.utils.ListUtils;
import eu.theappshop.baseadapter.utils.TestUtils;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static eu.theappshop.baseadapter.utils.ListUtils.listOf;
import static eu.theappshop.baseadapter.utils.TestUtils.assertThrows;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class VmAdapterTestCase {

  private BaseVmAdapter<StubVM> adapter;
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
    adapter = spy(new BaseVmAdapter<>(listOf(vm1, vm2, vm3, vm4, vm5, vm6)));
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
    StubVM removedVm = adapter.remove(0);
    //Test correct VM removed
    assertEquals(vm1, removedVm);
    //Test vms to be displayed after removal
    assertEquals(listOf(vm2, vm3, vm4, vm5, vm6), observer.vms);
    //Test correct notify method was called
    verify(observer, times(1)).notifyItemRemoved(0);
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
    verify(observer, times(1)).notifyItemRemoved(indexOfVm);
    verify(observer, times(1)).notifyItemRemoved(anyInt());
    verify(adapter, times(0)).notifyPropertyChanged(BR.empty);
  }

  @Test
  public void testRemoveByVmSizeAndEmpty() {
    List<StubVM> vmsToRemove = new ArrayList<>(adapter.vms());
    int size = vmsToRemove.size();
    for (StubVM vm : vmsToRemove) {
      adapter.remove(vm);
    }
    verify(adapter, times(size)).notifyPropertyChanged(BR.size);
    verify(adapter, times(1)).notifyPropertyChanged(BR.empty);
  }

  @Test
  public void testRemovePredicate() {
    adapter.removeIf(new Predicate<StubVM>() {
      @Override public Boolean apply(@NonNull StubVM object) {
        return object.value % 2 == 0;
      }
    });
    verify(adapter, times(1)).notifyPropertyChanged(BR.size);
    verify(adapter, times(0)).notifyPropertyChanged(BR.empty);
    assertEquals(ListUtils.listOf(vm1, vm3, vm5), observer.vms);
  }

  @Test
  public void testRemovePredicateSizeAndEmpty() {
    adapter.removeIf(new Predicate<StubVM>() {
      @Override public Boolean apply(@NonNull StubVM object) {
        return false;
      }
    });
    verify(adapter, times(0)).notifyPropertyChanged(BR.size);
    verify(adapter, times(0)).notifyPropertyChanged(BR.empty);
    assertEquals(ListUtils.listOf(vm1, vm2, vm3, vm4, vm5, vm6), observer.vms);
    adapter.removeIf(new Predicate<StubVM>() {
      @Override public Boolean apply(@NonNull StubVM object) {
        return true;
      }
    });
    verify(adapter, times(1)).notifyPropertyChanged(BR.size);
    verify(adapter, times(1)).notifyPropertyChanged(BR.empty);
    assertTrue(observer.vms.isEmpty());
  }

  @Test
  public void testRemovePredicateDiff() {
    adapter.removeIf(new Predicate<StubVM>() {
      @Override public Boolean apply(@NonNull StubVM object) {
        return object.value % 2 == 0;
      }
    }, true);
    verify(adapter, times(1)).notifyPropertyChanged(BR.size);
    verify(adapter, times(0)).notifyPropertyChanged(BR.empty);
    assertEquals(ListUtils.listOf(vm1, vm3, vm5), observer.vms);
  }

  @Test
  public void testRemovePredicateSizeAndEmptyDiff() {
    adapter.removeIf(new Predicate<StubVM>() {
      @Override public Boolean apply(@NonNull StubVM object) {
        return false;
      }
    });
    verify(adapter, times(0)).notifyPropertyChanged(BR.size);
    verify(adapter, times(0)).notifyPropertyChanged(BR.empty);
    assertEquals(ListUtils.listOf(vm1, vm2, vm3, vm4, vm5, vm6), observer.vms);
    adapter.removeIf(new Predicate<StubVM>() {
      @Override public Boolean apply(@NonNull StubVM object) {
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
    verify(observer, times(1)).notifyItemRangeRemoved(0, 2);
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
    StubVM vm = adapter.get(0);
    assertEquals(vm, vm1);
  }

  @Test
  public void testGetSize() {
    assertEquals(6, adapter.getSize());
  }
}
