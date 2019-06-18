package com.skiff2011.baseadapter.general;

import androidx.databinding.ViewDataBinding;
import com.skiff2011.baseadapter.AbstractItemAdapter;
import com.skiff2011.baseadapter.BR;
import com.skiff2011.baseadapter.StubItem;
import com.skiff2011.baseadapter.utils.TestUtils;
import com.skiff2011.baseadapter.view.BaseViewHolder;
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

import static com.skiff2011.baseadapter.utils.ListUtils.listOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public abstract class AdapterCommonTestCase {

  private AbstractItemAdapter<StubItem> adapter;
  private StubItem vm1;
  private StubItem vm2;
  private StubItem vm3;
  private StubItem vm4;
  private StubItem vm5;
  private StubItem vm6;

  @Before
  public void setUp() {
    vm1 = new StubItem(1);
    vm2 = new StubItem(2);
    vm3 = new StubItem(3);
    vm4 = new StubItem(4);
    vm5 = new StubItem(5);
    vm6 = new StubItem(6);
    adapter = provideAdapter(listOf(vm1, vm2, vm3, vm4, vm5));
  }

  @Test
  public void testContains() {
    assertTrue(adapter.contains(vm1));
    assertFalse(adapter.contains(vm6));
  }

  @Test
  public void testContainsAll() {
    List<StubItem> testCollection1 = Arrays.asList(vm3, vm1, vm2);
    List<StubItem> testCollection2 = Collections.emptyList();
    List<StubItem> testCollection3 = Arrays.asList(vm3, vm6, vm2);
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
        adapter.vms().add(new StubItem(0));
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.vms().add(0, new StubItem(0));
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.vms().set(0, new StubItem(0));
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
        adapter.vms().sort(new StubItem.StubVmComparator());
      }
    });
    final List<StubItem> subList = adapter.vms().subList(0, 4);
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        subList.add(new StubItem(0));
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        subList.add(0, new StubItem(0));
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        subList.set(0, new StubItem(0));
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
        subList.sort(new StubItem.StubVmComparator());
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        Iterator<StubItem> iterator = adapter.vms().iterator();
        iterator.next();
        iterator.remove();
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        ListIterator<StubItem> iterator = adapter.vms().listIterator();
        iterator.next();
        iterator.remove();
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        ListIterator<StubItem> iterator = adapter.vms().listIterator();
        iterator.next();
        iterator.set(vm6);
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        ListIterator<StubItem> iterator = adapter.vms().listIterator();
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
      @Override public Void answer(InvocationOnMock invocation) {
        binding.setVariable(BR.item, vm1);
        return null;
      }
    }).when(viewHolder).bindViewModel(vm1);
    adapter.bindViewHolder(viewHolder, 0);
    Mockito.verify(binding).setVariable(BR.item, vm1);
  }

  protected abstract AbstractItemAdapter<StubItem> provideAdapter(List<StubItem> vms);
}
