package com.skiff2011.baseadapter.baseadapter;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import com.skiff2011.baseadapter.AdapterObserver;
import com.skiff2011.baseadapter.BR;
import com.skiff2011.baseadapter.BaseItemAdapter;
import com.skiff2011.baseadapter.TestItem;
import com.skiff2011.baseadapter.misc.function.Predicate;
import com.skiff2011.baseadapter.utils.ListUtils;
import com.skiff2011.baseadapter.utils.TestUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

import static com.skiff2011.baseadapter.utils.ListUtils.listOf;
import static com.skiff2011.baseadapter.utils.TestUtils.assertThrows;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ItemAdapterTestCase {

  private BaseItemAdapter<TestItem> adapter;
  private AdapterObserver<TestItem> observer;

  private Predicate<TestItem> evenPredicate = new Predicate<TestItem>() {
    @Override public Boolean apply(@NonNull TestItem object) {
      return object.value % 2 == 0;
    }
  };

  private Predicate<TestItem> oddPredicate = new Predicate<TestItem>() {
    @Override public Boolean apply(@NonNull TestItem object) {
      return object.value % 2 != 0;
    }
  };

  private Predicate<TestItem> truePredicate = new Predicate<TestItem>() {
    @Override public Boolean apply(@NonNull TestItem object) {
      return true;
    }
  };

  private Predicate<TestItem> falsePredicate = new Predicate<TestItem>() {
    @Override public Boolean apply(@NonNull TestItem object) {
      return false;
    }
  };

  private Observable.OnPropertyChangedCallback fakeObservable;

  @Before
  public void setUp() {
    adapter = new BaseItemAdapter<>(
        listOf(
            new TestItem(1),
            new TestItem(2),
            new TestItem(3),
            new TestItem(4),
            new TestItem(5),
            new TestItem(6)
        )
    );
    observer = new AdapterObserver<>(adapter);
    fakeObservable = mock(Observable.OnPropertyChangedCallback.class);
    adapter.addOnPropertyChangedCallback(fakeObservable);
  }

  @Test
  public void testEmpty() {
    assertFalse(adapter.isEmpty());
    adapter.clear();
    assertTrue(adapter.isEmpty());
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
    List<TestItem> items = new ArrayList<>(adapter.items());
    TestItem removedVm = adapter.remove(0);
    TestItem item = items.remove(0);
    //Verify correct item removed
    assertEquals(item, removedVm);
    //Verify displayed items after removal
    assertEquals(items, observer.items);
    assertEquals(items, adapter.items());
  }

  @Test
  public void testRemoveByIndexSize() {
    int size = adapter.getSize();
    int i = size;
    while (i > 0) {
      adapter.remove(0);
      i--;
    }
    //verify size property changed if item was removed
    verify(fakeObservable, times(size)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testRemoveByIndexEmpty() {
    int i = adapter.getSize();
    while (i > 0) {
      adapter.remove(0);
      i--;
    }
    //verify empty property changed if item was removed
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testRemoveByVmContent() {
    //verify item was removed
    List<TestItem> items = new ArrayList<>(adapter.items());
    TestItem item = items.remove(0);
    assertTrue(adapter.remove(item));
    //verify removal result = false if item is absent in adapter
    assertFalse(adapter.remove(new TestItem(Integer.MAX_VALUE)));
    //Verify displayed items after removal
    assertEquals(items, observer.items);
    assertEquals(items, adapter.items());
  }

  @Test
  public void testRemoveByVmSize() {
    adapter.remove(new TestItem(Integer.MAX_VALUE));
    //verify size property is not changed if absent item removal has attempted
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
    adapter.remove(adapter.items().get(0));
    //verify size property changed if item removed
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testRemoveByVmEmpty() {
    int size = adapter.items().size();
    //verify size property is not changed if absent item removal has attempted
    while (size > 0) {
      adapter.remove(adapter.get(0));
      size--;
    }
    //verify empty property changed if item removed
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  //TODO test diffUtil flag correct behaviour

  @Test
  public void testRemovePredicateContent() {
    List<TestItem> result = ListUtils.filter(adapter.items(), oddPredicate);
    adapter.removeIf(evenPredicate);
    //verify correct items removed
    assertEquals(result, observer.items);
    assertEquals(result, adapter.items());
  }

  @Test
  public void testRemovePredicateSize() {
    //verify no size property change if no item removed
    adapter.removeIf(falsePredicate);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
    //verify size property change if some items removed
    adapter.removeIf(evenPredicate);
    verify(fakeObservable, atLeastOnce()).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testRemovePredicateEmpty() {
    //verify no empty property change if not all items removed
    adapter.removeIf(evenPredicate);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.empty);
    //verify empty property change if all items removed
    adapter.removeIf(truePredicate);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testRemovePredicateDiffTrueContent() {
    List<TestItem> result = ListUtils.filter(adapter.items(), oddPredicate);
    adapter.removeIf(evenPredicate, true);
    //verify correct items removed
    assertEquals(result, observer.items);
    assertEquals(result, adapter.items());
  }

  @Test
  public void testRemovePredicateDiffTrueSize() {
    //verify no size property change if no item removed
    adapter.removeIf(falsePredicate, true);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
    //verify size property change if some items removed
    adapter.removeIf(evenPredicate, true);
    verify(fakeObservable, atLeastOnce()).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testRemovePredicateDiffTrueEmpty() {
    //verify no empty property change if not all items removed
    adapter.removeIf(evenPredicate, true);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.empty);
    //verify empty property change if all items removed
    adapter.removeIf(truePredicate, true);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testRemovePredicateDiffFalseContent() {
    List<TestItem> result = ListUtils.filter(adapter.items(), oddPredicate);
    adapter.removeIf(evenPredicate, false);
    //verify correct items removed
    assertEquals(result, observer.items);
    assertEquals(result, adapter.items());
  }

  @Test
  public void testRemovePredicateDiffFalseSize() {
    //verify no size property change if no item removed
    adapter.removeIf(falsePredicate, false);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
    //verify size property change if some items removed
    adapter.removeIf(evenPredicate, false);
    verify(fakeObservable, atLeastOnce()).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testRemovePredicateDiffFalseEmpty() {
    //verify no empty property change if not all items removed
    adapter.removeIf(evenPredicate, false);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.empty);
    //verify empty property change if all items removed
    adapter.removeIf(truePredicate, false);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testRemoveRangeExceptions() {
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
  }

  @Test
  public void testRemoveRangeContent() {
    List<TestItem> items = new ArrayList<>(adapter.items());
    adapter.removeRange(0, 2);
    assertEquals(items.subList(2, items.size()), observer.items);
    assertEquals(items.subList(2, items.size()), adapter.items());
  }

  @Test
  public void testRemoveRangeSize() {
    adapter.removeRange(0, adapter.items().size() - 2);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testRemoveRangeEmpty() {
    adapter.removeRange(0, 2);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.empty);
    adapter.removeRange(0, adapter.items().size());
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testClearContent() {
    adapter.clear();
    assertTrue(observer.items.isEmpty());
    assertTrue(adapter.items().isEmpty());
  }

  @Test
  public void testClearSize() {
    adapter.clear();
    adapter.clear();
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testClearEmpty() {
    adapter.clear();
    adapter.clear();
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testClearDiffTrueContent() {
    adapter.clear(true);
    assertTrue(observer.items.isEmpty());
    assertTrue(adapter.items().isEmpty());
  }

  @Test
  public void testClearDiffTrueSize() {
    adapter.clear(true);
    adapter.clear(true);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testClearDiffTrueEmpty() {
    adapter.clear(true);
    adapter.clear(true);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testClearDiffFalseContent() {
    adapter.clear(false);
    assertTrue(observer.items.isEmpty());
    assertTrue(adapter.items().isEmpty());
  }

  @Test
  public void testClearDiffFalseSize() {
    adapter.clear(false);
    adapter.clear(false);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testClearDiffFalseEmpty() {
    adapter.clear(false);
    adapter.clear(false);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testGetExceptions() {
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
  }

  @Test
  public void testGetContent() {
    TestItem vm = adapter.get(0);
    assertEquals(vm, adapter.items().get(0));
  }

  @Test
  public void testGetSize() {
    assertEquals(adapter.items().size(), adapter.getSize());
  }

  @Test
  public void testRefresh() {
    List<TestItem> prevItems = adapter.items();
    adapter.refresh();
    assertEquals(prevItems, adapter.items());
    assertEquals(prevItems, observer.items);
  }

  @Test
  public void testAddContent() {
    List<TestItem> items = new ArrayList<>(adapter.items());
    TestItem item = new TestItem(Integer.MAX_VALUE);
    items.add(item);
    adapter.add(item);
    assertEquals(items, adapter.items());
    assertEquals(items, observer.items);
  }

  @Test
  public void testAddSize() {
    adapter.add(new TestItem(Integer.MAX_VALUE));
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testAddEmpty() {
    TestItem item = new TestItem(Integer.MAX_VALUE);
    List<TestItem> items = listOf(item);
    adapter.clear();
    adapter.add(item);
    verify(fakeObservable, times(2)).onPropertyChanged(adapter, BR.empty);
    assertEquals(items, adapter.items());
    assertEquals(items, observer.items);
  }

  @Test
  public void testAddByIndexExceptions() {
    assertThrows(IndexOutOfBoundsException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.add(-1, new TestItem(0));
      }
    });
    assertThrows(IndexOutOfBoundsException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.add(adapter.items().size() + 1, new TestItem(0));
      }
    });
  }

  @Test
  public void testAddByIndexContent() {
    List<TestItem> items = new ArrayList<>(adapter.items());
    TestItem item = new TestItem(Integer.MAX_VALUE);
    items.add(0, item);
    adapter.add(0, item);
    assertEquals(items, adapter.items());
    assertEquals(items, observer.items);
  }

  @Test
  public void testAddByIndexSize() {
    TestItem item = new TestItem(Integer.MAX_VALUE);
    adapter.add(0, item);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testAddByIndexEmpty() {
    TestItem item = new TestItem(Integer.MAX_VALUE);
    adapter.clear();
    adapter.add(0, item);
    verify(fakeObservable, times(2)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testAddAllContent() {
    List<TestItem> items = new ArrayList<>(adapter.items());
    TestItem item1 = new TestItem(Integer.MAX_VALUE);
    TestItem item2 = new TestItem(Integer.MAX_VALUE);
    List<TestItem> newItems = listOf(item1, item2);
    items.addAll(newItems);
    adapter.addAll(newItems);
    assertEquals(items, adapter.items());
    assertEquals(items, observer.items);
  }

  @Test
  public void testAddAllSize() {
    List<TestItem> newItems =
        listOf(new TestItem(Integer.MAX_VALUE), new TestItem(Integer.MAX_VALUE));
    adapter.addAll(newItems);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testAddAllSizeNotChanged() {
    List<TestItem> newItems = listOf();
    adapter.addAll(newItems);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testAddAllEmpty() {
    adapter.clear();
    List<TestItem> newItems =
        listOf(new TestItem(Integer.MAX_VALUE), new TestItem(Integer.MAX_VALUE));
    adapter.addAll(newItems);
    verify(fakeObservable, times(2)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testAddAllEmptyNotChanged() {
    adapter.clear();
    List<TestItem> newItems = listOf();
    adapter.addAll(newItems);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testAddAllByIndexExceptions() {
    final List<TestItem> newItems =
        listOf(new TestItem(Integer.MAX_VALUE), new TestItem(Integer.MAX_VALUE));
    assertThrows(IndexOutOfBoundsException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.addAll(-1, newItems);
      }
    });
    assertThrows(IndexOutOfBoundsException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.addAll(adapter.items().size() + 1, newItems);
      }
    });
  }

  @Test
  public void testAddAllByIndexContent() {
    List<TestItem> items = new ArrayList<>(adapter.items());
    TestItem item1 = new TestItem(Integer.MAX_VALUE);
    TestItem item2 = new TestItem(Integer.MAX_VALUE);
    List<TestItem> newItems = listOf(item1, item2);
    items.addAll(0, newItems);
    adapter.addAll(0, newItems);
    assertEquals(items, adapter.items());
    assertEquals(items, observer.items);
  }

  @Test
  public void testAddAllByIndexSize() {
    List<TestItem> newItems =
        listOf(new TestItem(Integer.MAX_VALUE), new TestItem(Integer.MAX_VALUE));
    adapter.addAll(0, newItems);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testAddAllByIndexSizeNotChanged() {
    List<TestItem> newItems = listOf();
    adapter.addAll(0, newItems);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testAddAllByIndexEmpty() {
    adapter.clear();
    List<TestItem> newItems =
        listOf(new TestItem(Integer.MAX_VALUE), new TestItem(Integer.MAX_VALUE));
    adapter.addAll(0, newItems);
    verify(fakeObservable, times(2)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testAddAllByIndexEmptyNotChanged() {
    adapter.clear();
    List<TestItem> newItems = listOf();
    adapter.addAll(0, newItems);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetByIndexExceptions() {
    assertThrows(IndexOutOfBoundsException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.set(-1, new TestItem(0));
      }
    });
    assertThrows(IndexOutOfBoundsException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.set(adapter.items().size() + 1, new TestItem(0));
      }
    });
    adapter.clear();
    assertThrows(IndexOutOfBoundsException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.set(0, new TestItem(0));
      }
    });
  }

  @Test
  public void testSetByIndexContent() {
    List<TestItem> items = new ArrayList<>(adapter.items());
    TestItem item = new TestItem(Integer.MAX_VALUE);
    items.set(0, item);
    adapter.set(0, item);
    assertEquals(items, adapter.items());
    assertEquals(items, observer.items);
  }

  @Test
  public void testSetCollectionContent() {
    List<TestItem> newItems = listOf(new TestItem(0), new TestItem(1));
    adapter.set(newItems);
    assertEquals(newItems, adapter.items());
    assertEquals(newItems, observer.items);
  }

  @Test
  public void testSetCollectionSizeChanged() {
    List<TestItem> newItems = listOf(new TestItem(0), new TestItem(1));
    adapter.set(newItems);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testSetCollectionSizeUnChanged() {
    List<TestItem> newItems = new ArrayList<>();
    for (int i = 0; i < adapter.getSize(); i++) {
      newItems.add(new TestItem(10 + i));
    }
    adapter.set(newItems);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testSetCollectionEmptyWasAndChanged() {
    List<TestItem> newItems = listOf(new TestItem(0), new TestItem(1));
    adapter.clear();
    adapter.set(newItems);
    verify(fakeObservable, times(2)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionEmptyWasAndUnChanged() {
    List<TestItem> newItems = listOf();
    adapter.clear();
    adapter.set(newItems);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionEmptyWasNotAndChanged() {
    List<TestItem> newItems = listOf();
    adapter.set(newItems);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionEmptyWasNotAndUnChanged() {
    List<TestItem> newItems = listOf(new TestItem(0), new TestItem(1));
    adapter.set(newItems);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionContentTrueDiffUtil() {
    List<TestItem> newItems = listOf(new TestItem(0), new TestItem(1));
    adapter.set(newItems, true);
    assertEquals(newItems, adapter.items());
    assertEquals(newItems, observer.items);
  }

  @Test
  public void testSetCollectionSizeChangedTrueDiffUtil() {
    List<TestItem> newItems = listOf(new TestItem(0), new TestItem(1));
    adapter.set(newItems, true);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testSetCollectionSizeUnChangedTrueDiffUtil() {
    List<TestItem> newItems = new ArrayList<>();
    for (int i = 0; i < adapter.getSize(); i++) {
      newItems.add(new TestItem(10 + i));
    }
    adapter.set(newItems, true);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testSetCollectionEmptyWasAndChangedTrueDiffUtil() {
    List<TestItem> newItems = listOf(new TestItem(0), new TestItem(1));
    adapter.clear();
    adapter.set(newItems, true);
    verify(fakeObservable, times(2)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionEmptyWasAndUnChangedTrueDiffUtil() {
    List<TestItem> newItems = listOf();
    adapter.clear();
    adapter.set(newItems, true);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionEmptyWasNotAndChangedTrueDiffUtil() {
    List<TestItem> newItems = listOf();
    adapter.set(newItems, true);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionEmptyWasNotAndUnChangedTrueDiffUtil() {
    List<TestItem> newItems = listOf(new TestItem(0), new TestItem(1));
    adapter.set(newItems, true);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionContentFalseDiffUtil() {
    List<TestItem> newItems = listOf(new TestItem(0), new TestItem(1));
    adapter.set(newItems, false);
    assertEquals(newItems, adapter.items());
    assertEquals(newItems, observer.items);
  }

  @Test
  public void testSetCollectionSizeChangedFalseDiffUtil() {
    List<TestItem> newItems = listOf(new TestItem(0), new TestItem(1));
    adapter.set(newItems, false);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testSetCollectionSizeUnChangedFalseDiffUtil() {
    List<TestItem> newItems = new ArrayList<>();
    for (int i = 0; i < adapter.getSize(); i++) {
      newItems.add(new TestItem(10 + i));
    }
    adapter.set(newItems, false);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testSetCollectionEmptyWasAndChangedFalseDiffUtil() {
    List<TestItem> newItems = listOf(new TestItem(0), new TestItem(1));
    adapter.clear();
    adapter.set(newItems, false);
    verify(fakeObservable, times(2)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionEmptyWasAndUnChangedFalseDiffUtil() {
    List<TestItem> newItems = listOf();
    adapter.clear();
    adapter.set(newItems, false);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionEmptyWasNotAndChangedFalseDiffUtil() {
    List<TestItem> newItems = listOf();
    adapter.set(newItems, false);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionEmptyWasNotAndUnChangedFalseDiffUtil() {
    List<TestItem> newItems = listOf(new TestItem(0), new TestItem(1));
    adapter.set(newItems, false);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testIteratorHasNextNotEmpty() {
    assertTrue(adapter.iterator().hasNext());
  }

  @Test
  public void testIteratorHasNextEmpty() {
    adapter.clear();
    assertFalse(adapter.iterator().hasNext());
  }

  @Test
  public void testIteratorNextNotEmpty() {
    TestItem item = adapter.items().get(0);
    assertEquals(item, adapter.iterator().next());
  }

  @Test
  public void testIteratorNextEmpty() {
    adapter.clear();
    assertThrows(NoSuchElementException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.iterator().next();
      }
    });
  }

  @Test
  public void testIteratorRemoveException() {
    assertThrows(IllegalStateException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.iterator().remove();
      }
    });
  }

  @Test
  public void testIteratorRemoveContent() {
    List<TestItem> items = new ArrayList<>(adapter.items());
    items.remove(0);
    Iterator<TestItem> iterator = adapter.iterator();
    iterator.next();
    iterator.remove();
    assertEquals(items, adapter.items());
    assertEquals(items, observer.items);
  }

  @Test
  public void testIteratorRemoveSize() {
    Iterator<TestItem> iterator = adapter.iterator();
    iterator.next();
    iterator.remove();
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testIteratorRemoveEmpty() {
    Iterator<TestItem> iterator = adapter.iterator();
    while (iterator.hasNext()) {
      iterator.next();
      iterator.remove();
    }
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testListIteratorHasNextNotEmpty() {
    assertTrue(adapter.listIterator().hasNext());
  }

  @Test
  public void testListIteratorHasNextEmpty() {
    adapter.clear();
    assertFalse(adapter.listIterator().hasNext());
  }

  @Test
  public void testListIteratorNextNotEmpty() {
    TestItem item = adapter.get(0);
    assertEquals(item, adapter.listIterator().next());
  }

  @Test
  public void testListIteratorNextEmpty() {
    adapter.clear();
    assertThrows(NoSuchElementException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.listIterator().next();
      }
    });
  }

  @Test
  public void testListIteratorNextIndex() {
    ListIterator<TestItem> listIterator = adapter.listIterator();
    listIterator.next();
    assertEquals(1, listIterator.nextIndex());
  }

  @Test
  public void testListIteratorNextIndexEnd() {
    ListIterator<TestItem> listIterator = adapter.listIterator();
    while (listIterator.hasNext()) {
      listIterator.next();
    }
    assertEquals(adapter.getSize(), listIterator.nextIndex());
  }

  @Test
  public void testListIteratorNextIndexEmpty() {
    adapter.clear();
    assertEquals(0, adapter.listIterator().nextIndex());
  }

  @Test
  public void testListIteratorRemoveContent() {
    List<TestItem> items = new ArrayList<>(adapter.items());
    items.remove(0);
    ListIterator<TestItem> iterator = adapter.listIterator();
    iterator.next();
    iterator.remove();
    assertEquals(items, adapter.items());
    assertEquals(items, observer.items);
  }

  @Test
  public void testListIteratorRemoveSize() {
    ListIterator<TestItem> iterator = adapter.listIterator();
    iterator.next();
    iterator.remove();
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testListIteratorRemoveEmpty() {
    ListIterator<TestItem> iterator = adapter.listIterator();
    while (iterator.hasNext()) {
      iterator.next();
      iterator.remove();
    }
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testListIteratorHasPrevious() {
    ListIterator<TestItem> listIterator = adapter.listIterator();
    listIterator.next();
    assertTrue(listIterator.hasPrevious());
  }

  @Test
  public void testListIteratorHasNotPrevious() {
    assertFalse(adapter.listIterator().hasPrevious());
  }

  @Test
  public void testListIteratorPreviousHasNotPrevious() {
    assertThrows(NoSuchElementException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.listIterator().previous();
      }
    });
  }

  @Test
  public void testListIteratorPreviousHasPrevious() {
    TestItem item = adapter.get(0);
    ListIterator<TestItem> listIterator = adapter.listIterator();
    listIterator.next();
    assertEquals(item, listIterator.previous());
  }

  @Test
  public void testListIteratorPreviousIndex() {
    ListIterator<TestItem> listIterator = adapter.listIterator();
    listIterator.next();
    assertEquals(0, listIterator.previousIndex());
  }

  @Test
  public void testListIteratorPreviousIndexBeginning() {
    assertEquals(-1, adapter.listIterator().previousIndex());
  }

  @Test
  public void testListIteratorAddContent() {
    TestItem item = new TestItem(Integer.MAX_VALUE);
    ListIterator<TestItem> listIterator = adapter.listIterator();
    List<TestItem> items = new ArrayList<>(adapter.items());
    items.add(1, item);
    listIterator.next();
    int prevIndex = listIterator.previousIndex();
    int nextIndex = listIterator.nextIndex();
    listIterator.add(item);
    assertEquals(items, adapter.items());
    assertEquals(items, observer.items);
    assertEquals(listIterator.nextIndex(), nextIndex + 1);
    assertEquals(listIterator.previousIndex(), prevIndex + 1);
    assertEquals(item, listIterator.previous());
  }

  @Test
  public void testListIteratorAddSize() {
    adapter.listIterator().add(new TestItem(Integer.MAX_VALUE));
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testListIteratorAddEmpty() {
    adapter.clear();
    adapter.listIterator().add(new TestItem(Integer.MAX_VALUE));
    verify(fakeObservable, times(2)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testListIteratorSetExceptions() {
    final TestItem item = new TestItem(Integer.MAX_VALUE);
    assertThrows(IllegalStateException.class, new TestUtils.Block() {
      @Override public void run() {
        adapter.listIterator().set(item);
      }
    });
    assertThrows(IllegalStateException.class, new TestUtils.Block() {
      @Override public void run() {
        ListIterator<TestItem> listIterator = adapter.listIterator();
        listIterator.next();
        listIterator.remove();
        listIterator.set(new TestItem(Integer.MAX_VALUE));
      }
    });
    assertThrows(IllegalStateException.class, new TestUtils.Block() {
      @Override public void run() {
        ListIterator<TestItem> listIterator = adapter.listIterator();
        listIterator.next();
        listIterator.add(new TestItem(Integer.MAX_VALUE));
        listIterator.set(new TestItem(Integer.MAX_VALUE));
      }
    });
    assertThrows(IllegalStateException.class, new TestUtils.Block() {
      @Override public void run() {
        ListIterator<TestItem> listIterator = adapter.listIterator();
        listIterator.next();
        listIterator.next();
        listIterator.previous();
        listIterator.remove();
        listIterator.set(new TestItem(Integer.MAX_VALUE));
      }
    });
    assertThrows(IllegalStateException.class, new TestUtils.Block() {
      @Override public void run() {
        ListIterator<TestItem> listIterator = adapter.listIterator();
        listIterator.next();
        listIterator.next();
        listIterator.previous();
        listIterator.add(new TestItem(Integer.MAX_VALUE));
        listIterator.set(new TestItem(Integer.MAX_VALUE));
      }
    });
  }

  @Test
  public void testListIteratorSetContentChangedNext() {
    List<TestItem> items = new ArrayList<>(adapter.items());
    TestItem newItem = new TestItem(Integer.MAX_VALUE);
    ListIterator<TestItem> listIterator = adapter.listIterator();
    listIterator.next();
    listIterator.set(newItem);
    items.set(0, newItem);
    assertEquals(items, adapter.items());
    assertEquals(items, observer.items);
  }

  @Test
  public void testListIteratorSetContentChangedPrevious() {
    List<TestItem> items = new ArrayList<>(adapter.items());
    TestItem newItem = new TestItem(Integer.MAX_VALUE);
    ListIterator<TestItem> listIterator = adapter.listIterator();
    listIterator.next();
    listIterator.previous();
    listIterator.set(newItem);
    items.set(0, newItem);
    assertEquals(items, adapter.items());
    assertEquals(items, observer.items);
  }

  @Test
  public void testListIteratorInitialIndex() {
    ListIterator<TestItem> listIterator = adapter.listIterator(3);
    TestItem item = adapter.items().get(3);
    assertEquals(item, listIterator.next());
    assertEquals(item, listIterator.previous());
  }
}
