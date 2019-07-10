package dev.auxility.baseadapter.general;

import androidx.databinding.ViewDataBinding;
import dev.auxility.baseadapter.BR;
import dev.auxility.baseadapter.AbstractAdapter;
import dev.auxility.baseadapter.Adapter;
import dev.auxility.baseadapter.TestItem;
import dev.auxility.baseadapter.utils.TestUtils;
import dev.auxility.baseadapter.view.BaseViewHolder;
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

import static dev.auxility.baseadapter.utils.ListUtils.listOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public abstract class AdapterCommonTestCase {

  private Adapter<TestItem> adapter;
  private TestItem item1;
  private TestItem item2;
  private TestItem item3;
  private TestItem item4;
  private TestItem item5;
  private TestItem item6;

  @Before
  public void setUp() {
    item1 = new TestItem(1);
    item2 = new TestItem(2);
    item3 = new TestItem(3);
    item4 = new TestItem(4);
    item5 = new TestItem(5);
    item6 = new TestItem(6);
    adapter = provideAdapter(listOf(item1, item2, item3, item4, item5));
  }

  @Test
  public void testContains() {
    assertTrue(adapter.contains(item1));
    assertFalse(adapter.contains(item6));
  }

  @Test
  public void testContainsAll() {
    List<TestItem> testCollection1 = Arrays.asList(item3, item1, item2);
    List<TestItem> testCollection2 = Collections.emptyList();
    List<TestItem> testCollection3 = Arrays.asList(item3, item6, item2);
    assertTrue(adapter.containsAll(testCollection1));
    assertTrue(adapter.containsAll(testCollection2));
    assertFalse(adapter.containsAll(testCollection3));
  }

  @Test
  public void testIndexOf() {
    adapter.add(item2);
    assertEquals(1, adapter.indexOf(item2));
    assertEquals(-1, adapter.indexOf(item6));
  }

  @Test
  public void testLastIndexOf() {
    adapter.add(item2);
    assertEquals(5, adapter.lastIndexOf(item2));
    assertEquals(-1, adapter.lastIndexOf(item6));
  }

  //items collection returned by adapter.items must be immutable to save adapter intended behaviour
  @Test
  public void testItems() {
    assertEquals(new ArrayList<>(Arrays.asList(item1, item2, item3, item4, item5)),
        adapter.items());
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.items().add(new TestItem(0));
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.items().add(0, new TestItem(0));
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.items().set(0, new TestItem(0));
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.items().remove(item1);
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.items().remove(0);
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.items().clear();
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.items().removeAll(Arrays.asList(item1, item2));
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.items().sort(new TestItem.StubVmComparator());
      }
    });
    final List<TestItem> subList = adapter.items().subList(0, 4);
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        subList.add(new TestItem(0));
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        subList.add(0, new TestItem(0));
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        subList.set(0, new TestItem(0));
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        subList.remove(item1);
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
        subList.removeAll(Arrays.asList(item1, item2));
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        subList.sort(new TestItem.StubVmComparator());
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        Iterator<TestItem> iterator = adapter.items().iterator();
        iterator.next();
        iterator.remove();
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        ListIterator<TestItem> iterator = adapter.items().listIterator();
        iterator.next();
        iterator.remove();
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        ListIterator<TestItem> iterator = adapter.items().listIterator();
        iterator.next();
        iterator.set(item6);
      }
    });
    TestUtils.assertThrows(UnsupportedOperationException.class, new TestUtils.Block() {
      @Override public void run() {
        ListIterator<TestItem> iterator = adapter.items().listIterator();
        iterator.next();
        iterator.add(item6);
      }
    });
  }

  @Test
  public void testBinding() {
    final ViewDataBinding binding = Mockito.mock(ViewDataBinding.class);
    BaseViewHolder viewHolder = Mockito.mock(BaseViewHolder.class);
    when(viewHolder.getBinding()).thenReturn(binding);
    final TestItem item = adapter.get(0);
    doAnswer(new Answer() {
      @Override public Void answer(InvocationOnMock invocation) {
        binding.setVariable(BR.item, item);
        return null;
      }
    }).when(viewHolder).bindViewModel(item);
    adapter.bindViewHolder(viewHolder, 0);
    Mockito.verify(binding).setVariable(BR.item, item1);
  }

  protected abstract AbstractAdapter<TestItem> provideAdapter(List<TestItem> vms);
}
