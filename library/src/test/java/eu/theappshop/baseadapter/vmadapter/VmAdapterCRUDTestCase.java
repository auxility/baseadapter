package eu.theappshop.baseadapter.vmadapter;

import eu.theappshop.baseadapter.AdapterObserver;
import eu.theappshop.baseadapter.BR;
import eu.theappshop.baseadapter.StubVM;
import eu.theappshop.baseadapter.adapterv2.VmAdapter;
import eu.theappshop.baseadapter.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;

import static eu.theappshop.baseadapter.utils.ListUtils.listOf;
import static eu.theappshop.baseadapter.utils.TestUtils.assertThrows;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class VmAdapterCRUDTestCase {

  private VmAdapter<StubVM> adapter;
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
    adapter = spy(new VmAdapter<>(listOf(vm1, vm2, vm3, vm4, vm5, vm6)));
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
    verify(observer, times(1)).onNotifyItemRemoved(0);
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
  public void testRemoveByVm() {

  }

  @Test
  public void testRemovePredicate() {

  }

  @Test
  public void testRemovePredicateDiffUtil() {

  }

  @Test
  public void testRemoveRange() {

  }

  @Test
  public void testClear() {

  }

  @Test
  public void testClearDiffUtil() {

  }
}
