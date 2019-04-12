package eu.theappshop.baseadapter.general;

import android.databinding.ViewDataBinding;
import eu.theappshop.baseadapter.BR;
import eu.theappshop.baseadapter.StubVM;
import eu.theappshop.baseadapter.adapter.BaseViewHolder;
import eu.theappshop.baseadapter.adapterv2.Adapter;
import eu.theappshop.baseadapter.utils.TestUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public abstract class AdapterCommonTestCase {

  private Adapter<StubVM> adapter;
  private StubVM vm1;
  private StubVM vm2;
  private StubVM vm3;
  private StubVM vm4;
  private StubVM vm5;
  private StubVM vm6;

  @Before
  public void setUp() {
    vm1 = new StubVM(1);
    vm2 = new StubVM(2);
    vm3 = new StubVM(3);
    vm4 = new StubVM(4);
    vm5 = new StubVM(5);
    vm6 = new StubVM(6);
    adapter = provideAdapter(new ArrayList<>(Arrays.asList(vm1, vm2, vm3, vm4, vm5)));
  }

  @Test
  public void testContains() {
    assertTrue(adapter.contains(vm1));
    assertFalse(adapter.contains(vm6));
  }

  @Test
  public void testContainsAll() {
    List<StubVM> testCollection1 = Arrays.asList(vm3, vm1, vm2);
    List<StubVM> testCollection2 = Collections.emptyList();
    List<StubVM> testCollection3 = Arrays.asList(vm3, vm6, vm2);
    assertTrue(adapter.containsAll(testCollection1));
    assertTrue(adapter.containsAll(testCollection2));
    assertFalse(adapter.containsAll(testCollection3));
  }

  @Test
  public void testIndexOf() {
    adapter.add(vm2);
    assertEquals(1, adapter.indexOf(vm2));
    assertEquals(-1, adapter.indexOf(vm6));
  }

  @Test
  public void testLastIndexOf() {
    adapter.add(vm2);
    assertEquals(5, adapter.lastIndexOf(vm2));
    assertEquals(-1, adapter.lastIndexOf(vm6));
  }

  @Test
  public void testVms() {
    assertEquals(new ArrayList<>(Arrays.asList(vm1, vm2, vm3, vm4, vm5)), adapter.vms());
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.vms().add(new StubVM(0));
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.vms().add(0, new StubVM(0));
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.vms().set(0, new StubVM(0));
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.vms().remove(vm1);
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.vms().remove(0);
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.vms().clear();
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.vms().removeAll(Arrays.asList(vm1, vm2));
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.vms().sort(new StubVM.StubVmComparator());
      }
    });
    final List<StubVM> subList = adapter.vms().subList(0, 4);
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        subList.add(new StubVM(0));
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        subList.add(0, new StubVM(0));
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        subList.set(0, new StubVM(0));
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        subList.remove(vm1);
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        subList.remove(0);
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        subList.clear();
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        subList.removeAll(Arrays.asList(vm1, vm2));
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        subList.sort(new StubVM.StubVmComparator());
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        Iterator<StubVM> iterator = adapter.vms().iterator();
        iterator.next();
        iterator.remove();
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        ListIterator<StubVM> iterator = adapter.vms().listIterator();
        iterator.next();
        iterator.remove();
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        ListIterator<StubVM> iterator = adapter.vms().listIterator();
        iterator.next();
        iterator.set(vm6);
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        ListIterator<StubVM> iterator = adapter.vms().listIterator();
        iterator.next();
        iterator.add(vm6);
      }
    });
  }

  @Test
  public void testBinding() {
    final ViewDataBinding binding = Mockito.mock(ViewDataBinding.class);
    BaseViewHolder viewHolder = Mockito.mock(BaseViewHolder.class);
    when(viewHolder.getBinding()).thenReturn(binding);
    doAnswer(new Answer() {
      @Override public Void answer(InvocationOnMock invocation) throws Throwable {
        binding.setVariable(BR.viewModel, vm1);
        return null;
      }
    }).when(viewHolder).bindViewModel(vm1);
    adapter.bindViewHolder(viewHolder, 0);
    Mockito.verify(binding).setVariable(BR.viewModel, vm1);
  }

  protected abstract Adapter<StubVM> provideAdapter(List<StubVM> vms);
}
