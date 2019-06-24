package com.skiff2011.baseadapter.filterableadapter;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import com.skiff2011.baseadapter.AdapterObserver;
import com.skiff2011.baseadapter.BR;
import com.skiff2011.baseadapter.FilterableItemAdapter;
import com.skiff2011.baseadapter.TestItem;
import com.skiff2011.baseadapter.misc.function.AbsFilter;
import com.skiff2011.baseadapter.misc.function.Predicate;
import com.skiff2011.baseadapter.misc.function.SerializablePredicate;
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

public class FilterableAdapterTestCase {

  private FilterableItemAdapter<TestItem> adapter;
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

  private Predicate<TestItem> multipleThreePredicate = new Predicate<TestItem>() {
    @Override public Boolean apply(@NonNull TestItem object) {
      return object.value % 3 == 0;
    }
  };

  private Predicate<TestItem> notMultipleThreePredicate = new Predicate<TestItem>() {
    @Override public Boolean apply(@NonNull TestItem object) {
      return object.value % 3 != 0;
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

  private SerializablePredicate<TestItem> filter = new TestPredicate();

  private Observable.OnPropertyChangedCallback fakeObservable;

  @Before
  public void setUp() {
    adapter = new FilterableItemAdapter<>(
        listOf(
            new TestItem(1),
            new TestItem(2),
            new TestItem(3),
            new TestItem(4),
            new TestItem(5),
            new TestItem(6)
        ), new TestPredicate()
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
    adapter.add(new TestItem(2));
    assertTrue(adapter.isEmpty());
    adapter.add(new TestItem(1));
    assertFalse(adapter.isEmpty());
  }

  @Test
  public void testGetSize() {
    assertEquals(ListUtils.filter(adapter.items(), filter).size(), adapter.getSize());
    adapter.clear();
    assertEquals(0, adapter.getSize());
    adapter.add(new TestItem(2));
    assertEquals(0, adapter.getSize());
    adapter.add(new TestItem(1));
    assertEquals(1, adapter.getSize());
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
    TestItem removedVm1 = adapter.remove(0);
    TestItem item1 = items.remove(0);
    //Verify correct item removed
    assertEquals(item1, removedVm1);
    TestItem removedVm2 = adapter.remove(0);
    TestItem item2 = items.remove(0);
    //Verify correct item removed
    assertEquals(item2, removedVm2);
    //Verify displayed items after removal
    List<TestItem> filteredItems = ListUtils.filter(items, filter);
    assertEquals(filteredItems, observer.items);
    assertEquals(items, adapter.items());
  }

  @Test
  public void testRemoveByIndexSize() {
    adapter.remove(0);
    adapter.remove(0);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testRemoveByIndexEmpty() {
    int i = adapter.items().size();
    while (i > 0) {
      adapter.remove(0);
      i--;
    }
    //verify empty property changed if item was removed
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testRemoveByVMContent() {
    //verify item was removed
    List<TestItem> items = new ArrayList<>(adapter.items());
    TestItem item1 = items.remove(0);
    TestItem item2 = items.remove(0);
    assertTrue(adapter.remove(item1));
    assertTrue(adapter.remove(item2));
    //verify removal result = false if item is absent in adapter
    assertFalse(adapter.remove(new TestItem(Integer.MAX_VALUE)));
    //Verify displayed items after removal
    List<TestItem> filteredItems = ListUtils.filter(items, filter);
    assertEquals(filteredItems, observer.items);
    assertEquals(items, adapter.items());
  }

  @Test
  public void testRemoveByVmSize() {
    adapter.remove(new TestItem(Integer.MAX_VALUE));
    adapter.remove(adapter.items().get(0));
    adapter.remove(adapter.items().get(0));
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testRemoveByVmEmpty() {
    int size = adapter.items().size();
    //verify size property is not changed if absent item removal has attempted
    while (size > 0) {
      adapter.remove(adapter.items().get(0));
      size--;
    }
    //verify empty property changed if item removed
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testRemovePredicateContent() {
    List<TestItem> result = ListUtils.filter(adapter.items(), notMultipleThreePredicate);
    List<TestItem> filteredResult = ListUtils.filter(result, filter);
    adapter.removeIf(multipleThreePredicate);
    //verify correct items removed
    assertEquals(filteredResult, observer.items);
    assertEquals(result, adapter.items());
  }

  @Test
  public void testRemovePredicateSize() {
    //verify no size property change if no item removed
    adapter.removeIf(falsePredicate);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
    adapter.removeIf(evenPredicate);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
    //verify size property change if some items removed
    adapter.removeIf(multipleThreePredicate);
    verify(fakeObservable, atLeastOnce()).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testRemovePredicateEmpty() {
    //verify no empty property change if not all items removed
    adapter.removeIf(multipleThreePredicate);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.empty);
    //verify empty property change if all items removed
    adapter.removeIf(truePredicate);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testRemovePredicateDiffTrueContent() {
    List<TestItem> result = ListUtils.filter(adapter.items(), notMultipleThreePredicate);
    List<TestItem> filteredResult = ListUtils.filter(result, filter);
    adapter.removeIf(multipleThreePredicate, true);
    //verify correct items removed
    assertEquals(filteredResult, observer.items);
    assertEquals(result, adapter.items());
  }

  @Test
  public void testRemovePredicateDiffTrueSize() {
    //verify no size property change if no item removed
    adapter.removeIf(falsePredicate, true);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
    adapter.removeIf(evenPredicate, true);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
    //verify size property change if some items removed
    adapter.removeIf(multipleThreePredicate, true);
    verify(fakeObservable, atLeastOnce()).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testRemovePredicateDiffTrueEmpty() {
    //verify no empty property change if not all items removed
    adapter.removeIf(multipleThreePredicate, true);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.empty);
    //verify empty property change if all items removed
    adapter.removeIf(truePredicate, true);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testRemovePredicateDiffFalseContent() {
    List<TestItem> result = ListUtils.filter(adapter.items(), notMultipleThreePredicate);
    List<TestItem> filteredResult = ListUtils.filter(result, filter);
    adapter.removeIf(multipleThreePredicate, false);
    //verify correct items removed
    assertEquals(filteredResult, observer.items);
    assertEquals(result, adapter.items());
  }

  @Test
  public void testRemovePredicateDiffFalseSize() {
    //verify no size property change if no item removed
    adapter.removeIf(falsePredicate, true);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
    adapter.removeIf(evenPredicate, true);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
    //verify size property change if some items removed
    adapter.removeIf(multipleThreePredicate, true);
    verify(fakeObservable, atLeastOnce()).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testRemovePredicateDiffFalseEmpty() {
    //verify no empty property change if not all items removed
    adapter.removeIf(multipleThreePredicate, true);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.empty);
    //verify empty property change if all items removed
    adapter.removeIf(truePredicate, true);
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
        adapter.removeRange(2, adapter.items().size() + 1);
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
    List<TestItem> filteredItems = ListUtils.filter(items.subList(2, items.size()), filter);
    assertEquals(filteredItems, observer.items);
    assertEquals(items.subList(2, items.size()), adapter.items());
  }

  @Test
  public void testRemoveRangeSize() {
    adapter.removeRange(0, 1);
    adapter.removeRange(0, 1);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testRemoveRangeEmpty() {
    adapter.removeRange(0, adapter.items().size() - 1);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
    adapter.removeRange(0, 1);
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
    TestItem vm1 = adapter.get(1);
    assertEquals(vm1, adapter.items().get(2));
  }

  @Test
  public void testRefresh() {
    List<TestItem> prevItems = new ArrayList<>(adapter.items());
    List<TestItem> prevFilteredItems = ListUtils.filter(adapter.items(), adapter.getFilter());
    adapter.refresh();
    assertEquals(prevItems, adapter.items());
    assertEquals(prevFilteredItems, observer.items);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSwapFilterContent() {
    List<TestItem> prevItems = new ArrayList<>(adapter.items());
    List<TestItem> prevFilteredItems = ListUtils.filter(adapter.items(), evenPredicate);
    adapter.setFilter(new AbsFilter<TestItem>() {
      @Override public Boolean apply(@NonNull TestItem object) {
        return evenPredicate.apply(object);
      }
    });
    assertEquals(prevItems, adapter.items());
    assertEquals(prevFilteredItems, observer.items);
  }

  @Test
  public void testSwapFilterSize() {
    adapter.remove(0);
    adapter.setFilter(new AbsFilter<TestItem>() {
      @Override public Boolean apply(@NonNull TestItem object) {
        return evenPredicate.apply(object);
      }
    });
    verify(fakeObservable, times(2)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testSwapFilterEmpty() {
    adapter.setFilter(new AbsFilter<TestItem>() {
      @Override public Boolean apply(@NonNull TestItem object) {
        return falsePredicate.apply(object);
      }
    });
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
    adapter.setFilter(new AbsFilter<TestItem>() {
      @Override public Boolean apply(@NonNull TestItem object) {
        return truePredicate.apply(object);
      }
    });
    verify(fakeObservable, times(2)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testAddContent() {
    List<TestItem> items = new ArrayList<>(adapter.items());
    TestItem item1 = new TestItem(1);
    TestItem item2 = new TestItem(2);
    items.add(item1);
    items.add(item2);
    adapter.add(item1);
    adapter.add(item2);
    List<TestItem> filteredItems = ListUtils.filter(items, adapter.getFilter());
    assertEquals(items, adapter.items());
    assertEquals(filteredItems, observer.items);
  }

  @Test
  public void testAddSize() {
    adapter.add(new TestItem(1));
    adapter.add(new TestItem(2));
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testAddEmpty() {
    TestItem item1 = new TestItem(1);
    TestItem item2 = new TestItem(2);
    adapter.clear();
    adapter.add(item2);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
    adapter.add(item1);
    verify(fakeObservable, times(2)).onPropertyChanged(adapter, BR.empty);
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
    TestItem item1 = new TestItem(1);
    TestItem item2 = new TestItem(2);
    adapter.add(0, item1);
    adapter.add(0, item2);
    items.add(0, item1);
    items.add(0, item2);
    assertEquals(items, adapter.items());
    assertEquals(ListUtils.filter(items, adapter.getFilter()), observer.items);
  }

  @Test
  public void testAddByIndexSize() {
    TestItem item1 = new TestItem(1);
    TestItem item2 = new TestItem(2);
    adapter.add(0, item1);
    adapter.add(0, item2);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testAddByIndexEmpty() {
    TestItem item1 = new TestItem(1);
    TestItem item2 = new TestItem(2);
    adapter.clear();
    adapter.add(0, item2);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
    adapter.add(0, item1);
    verify(fakeObservable, times(2)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testAddAllContent() {
    List<TestItem> items = new ArrayList<>(adapter.items());
    TestItem item1 = new TestItem(1);
    TestItem item2 = new TestItem(2);
    List<TestItem> newItems = listOf(item1, item2);
    items.addAll(newItems);
    adapter.addAll(newItems);
    assertEquals(items, adapter.items());
    assertEquals(ListUtils.filter(items, adapter.getFilter()), observer.items);
  }

  @Test
  public void testAddAllSize() {
    List<TestItem> newItems1 =
        listOf(new TestItem(1), new TestItem(3));
    List<TestItem> newItems2 =
        listOf(new TestItem(2), new TestItem(4));
    List<TestItem> emptyList = listOf();
    adapter.addAll(emptyList);
    adapter.addAll(newItems1);
    adapter.addAll(newItems2);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testAddAllEmpty() {
    adapter.clear();
    List<TestItem> newItems1 =
        listOf(new TestItem(1), new TestItem(3));
    List<TestItem> newItems2 =
        listOf(new TestItem(2), new TestItem(4));
    List<TestItem> emptyList = listOf();
    adapter.addAll(emptyList);
    adapter.addAll(newItems1);
    adapter.addAll(newItems2);
    verify(fakeObservable, times(2)).onPropertyChanged(adapter, BR.empty);
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
    TestItem item1 = new TestItem(1);
    TestItem item2 = new TestItem(2);
    List<TestItem> newItems = listOf(item1, item2);
    items.addAll(0, newItems);
    adapter.addAll(0, newItems);
    assertEquals(items, adapter.items());
    assertEquals(ListUtils.filter(items, adapter.getFilter()), observer.items);
  }

  @Test
  public void testAddAllByIndexSize() {
    List<TestItem> newItems1 =
        listOf(new TestItem(1), new TestItem(3));
    List<TestItem> newItems2 =
        listOf(new TestItem(2), new TestItem(4));
    List<TestItem> emptyList = listOf();
    adapter.addAll(emptyList);
    adapter.addAll(newItems1);
    adapter.addAll(newItems2);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testAddAllByIndexEmpty() {
    adapter.clear();
    List<TestItem> newItems1 =
        listOf(new TestItem(1), new TestItem(3));
    List<TestItem> newItems2 =
        listOf(new TestItem(2), new TestItem(4));
    List<TestItem> emptyList = listOf();
    adapter.addAll(emptyList);
    adapter.addAll(newItems1);
    adapter.addAll(newItems2);
    verify(fakeObservable, times(2)).onPropertyChanged(adapter, BR.empty);
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
    TestItem item1 = new TestItem(1);
    TestItem item2 = new TestItem(2);
    items.set(0, item1);
    adapter.set(0, item1);
    items.set(1, item2);
    adapter.set(1, item2);
    assertEquals(items, adapter.items());
    assertEquals(ListUtils.filter(items, adapter.getFilter()), observer.items);
  }

  @Test
  public void testSetByIndexSize() {
    TestItem item1 = new TestItem(7);
    TestItem item2 = new TestItem(8);
    adapter.set(0, item1);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
    adapter.set(1, item2);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
    adapter.set(1, item1);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
    adapter.set(0, item2);
    verify(fakeObservable, times(2)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testSetByIndexEmpty() {
    TestItem itemTrue = adapter.items().get(0);
    TestItem itemFalse = adapter.items().get(1);
    TestItem item1 = new TestItem(7);
    TestItem item2 = new TestItem(8);
    adapter.clear();
    adapter.add(itemFalse);
    adapter.set(0, item2);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
    adapter.set(0, item1);
    verify(fakeObservable, times(2)).onPropertyChanged(adapter, BR.empty);
    adapter.set(0, item2);
    verify(fakeObservable, times(3)).onPropertyChanged(adapter, BR.empty);
    adapter.clear();
    adapter.add(itemTrue);
    adapter.set(0, item1);
    verify(fakeObservable, times(4)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionContent() {
    List<TestItem> newItems = listOf(new TestItem(1), new TestItem(2));
    adapter.set(newItems);
    assertEquals(newItems, adapter.items());
    assertEquals(ListUtils.filter(newItems, adapter.getFilter()), observer.items);
  }

  @Test
  public void testSetCollectionSizeChanged() {
    List<TestItem> newItems = listOf(new TestItem(1), new TestItem(2));
    adapter.set(newItems);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testSetCollectionSizeUnChanged() {
    List<TestItem> newItems = new ArrayList<>();
    for (int i = 0; i < adapter.items().size(); i++) {
      newItems.add(new TestItem(10 + i));
    }
    adapter.set(newItems);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testSetCollectionEmptyWasAndChanged() {
    List<TestItem> newItems = listOf(new TestItem(1), new TestItem(3));
    adapter.clear();
    adapter.set(newItems);
    verify(fakeObservable, times(2)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionEmptyWasAndUnChanged() {
    List<TestItem> newItems = listOf();
    List<TestItem> newItems1 = listOf(new TestItem(2), new TestItem(4));
    adapter.clear();
    adapter.set(newItems);
    adapter.set(newItems1);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionEmptyWasNotAndChanged() {
    List<TestItem> newItems = listOf(new TestItem(2), new TestItem(4));
    adapter.set(newItems);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionEmptyWasNotAndUnChanged() {
    List<TestItem> newItems = listOf(new TestItem(1), new TestItem(3));
    adapter.set(newItems);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionContentTrueDiffUtil() {
    List<TestItem> newItems = listOf(new TestItem(1), new TestItem(2));
    adapter.set(newItems, true);
    assertEquals(newItems, adapter.items());
    assertEquals(ListUtils.filter(newItems, adapter.getFilter()), observer.items);
  }

  @Test
  public void testSetCollectionSizeChangedTrueDiffUtil() {
    List<TestItem> newItems = listOf(new TestItem(1), new TestItem(2));
    adapter.set(newItems, true);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testSetCollectionSizeUnChangedTrueDiffUtil() {
    List<TestItem> newItems = new ArrayList<>();
    for (int i = 0; i < adapter.items().size(); i++) {
      newItems.add(new TestItem(10 + i));
    }
    adapter.set(newItems, true);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testSetCollectionEmptyWasAndChangedTrueDiffUtil() {
    List<TestItem> newItems = listOf(new TestItem(1), new TestItem(3));
    adapter.clear();
    adapter.set(newItems, true);
    verify(fakeObservable, times(2)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionEmptyWasAndUnChangedTrueDiffUtil() {
    List<TestItem> newItems = listOf();
    List<TestItem> newItems1 = listOf(new TestItem(2), new TestItem(4));
    adapter.clear();
    adapter.set(newItems, true);
    adapter.set(newItems1, true);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionEmptyWasNotAndChangedTrueDiffUtil() {
    List<TestItem> newItems = listOf(new TestItem(2), new TestItem(4));
    adapter.set(newItems, true);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionEmptyWasNotAndUnChangedTrueDiffUtil() {
    List<TestItem> newItems = listOf(new TestItem(1), new TestItem(3));
    adapter.set(newItems, true);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionContentFalseDiffUtil() {
    List<TestItem> newItems = listOf(new TestItem(1), new TestItem(2));
    adapter.set(newItems, false);
    assertEquals(newItems, adapter.items());
    assertEquals(ListUtils.filter(newItems, adapter.getFilter()), observer.items);
  }

  @Test
  public void testSetCollectionSizeChangedFalseDiffUtil() {
    List<TestItem> newItems = listOf(new TestItem(1), new TestItem(2));
    adapter.set(newItems, false);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testSetCollectionSizeUnChangedFalseDiffUtil() {
    List<TestItem> newItems = new ArrayList<>();
    for (int i = 0; i < adapter.items().size(); i++) {
      newItems.add(new TestItem(10 + i));
    }
    adapter.set(newItems, false);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testSetCollectionEmptyWasAndChangedFalseDiffUtil() {
    List<TestItem> newItems = listOf(new TestItem(1), new TestItem(3));
    adapter.clear();
    adapter.set(newItems, false);
    verify(fakeObservable, times(2)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionEmptyWasAndUnChangedFalseDiffUtil() {
    List<TestItem> newItems = listOf();
    List<TestItem> newItems1 = listOf(new TestItem(2), new TestItem(4));
    adapter.clear();
    adapter.set(newItems, false);
    adapter.set(newItems1, false);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionEmptyWasNotAndChangedFalseDiffUtil() {
    List<TestItem> newItems = listOf(new TestItem(2), new TestItem(4));
    adapter.set(newItems, false);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testSetCollectionEmptyWasNotAndUnChangedFalseDiffUtil() {
    List<TestItem> newItems = listOf(new TestItem(1), new TestItem(3));
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
    items.remove(0);
    Iterator<TestItem> iterator = adapter.iterator();
    iterator.next();
    iterator.remove();
    iterator.next();
    iterator.remove();
    assertEquals(items, adapter.items());
    assertEquals(ListUtils.filter(items, adapter.getFilter()), observer.items);
  }

  @Test
  public void testIteratorRemoveSize() {
    Iterator<TestItem> iterator = adapter.iterator();
    iterator.next();
    iterator.remove();
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
    assertEquals(adapter.items().size(), listIterator.nextIndex());
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
    items.remove(0);
    ListIterator<TestItem> iterator = adapter.listIterator();
    iterator.next();
    iterator.remove();
    iterator.next();
    iterator.remove();
    assertEquals(items, adapter.items());
    assertEquals(ListUtils.filter(items, adapter.getFilter()), observer.items);
  }

  @Test
  public void testListIteratorRemoveSize() {
    ListIterator<TestItem> iterator = adapter.listIterator();
    iterator.next();
    iterator.remove();
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
    TestItem item1 = adapter.items().get(0);
    TestItem item2 = adapter.items().get(1);
    ListIterator<TestItem> listIterator = adapter.listIterator();
    listIterator.next();
    listIterator.next();
    TestItem assertItem1 = listIterator.previous();
    TestItem assertItem2 = listIterator.previous();
    assertEquals(item2, assertItem1);
    assertEquals(item1, assertItem2);
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
    TestItem item1 = new TestItem(1);
    TestItem item2 = new TestItem(2);
    ListIterator<TestItem> listIterator = adapter.listIterator();
    List<TestItem> items = new ArrayList<>(adapter.items());
    items.add(1, item1);
    items.add(2, item2);
    listIterator.next();
    listIterator.add(item1);
    listIterator.add(item2);
    assertEquals(items, adapter.items());
    assertEquals(ListUtils.filter(items, adapter.getFilter()), observer.items);
  }

  @Test
  public void testListIteratorAddSize() {
    ListIterator<TestItem> listIterator = adapter.listIterator();
    listIterator.add(new TestItem(1));
    listIterator.add(new TestItem(2));
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testListIteratorAddEmpty() {
    adapter.clear();
    ListIterator<TestItem> listIterator = adapter.listIterator();
    listIterator.add(new TestItem(1));
    listIterator.add(new TestItem(2));
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
  public void testListIteratorSetContentChanged() {
    List<TestItem> items = new ArrayList<>(adapter.items());
    TestItem newItem = new TestItem(Integer.MAX_VALUE);
    ListIterator<TestItem> listIterator = adapter.listIterator();
    listIterator.next();
    listIterator.set(newItem);
    items.set(0, newItem);
    assertEquals(items, adapter.items());
    assertEquals(ListUtils.filter(items, adapter.getFilter()), observer.items);
  }

  @Test
  public void testListIteratorSetSize() {
    ListIterator<TestItem> listIterator = adapter.listIterator();
    TestItem trueItem = new TestItem(7);
    TestItem falseItem = new TestItem(8);
    listIterator.next();
    listIterator.set(trueItem);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
    listIterator.next();
    listIterator.set(falseItem);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.size);
    listIterator = adapter.listIterator();
    listIterator.next();
    listIterator.set(falseItem);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.size);
    listIterator.next();
    listIterator.set(trueItem);
    verify(fakeObservable, times(2)).onPropertyChanged(adapter, BR.size);
  }

  @Test
  public void testListIteratorSetEmpty() {
    List<TestItem> items = new ArrayList<>(adapter.items());
    adapter.removeRange(0, adapter.items().size() - 2);
    ListIterator<TestItem> listIterator = adapter.listIterator();
    TestItem trueItem = new TestItem(7);
    TestItem falseItem = new TestItem(8);
    listIterator.next();
    listIterator.set(trueItem);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.empty);
    listIterator.next();
    listIterator.set(falseItem);
    verify(fakeObservable, never()).onPropertyChanged(adapter, BR.empty);
    listIterator = adapter.listIterator();
    listIterator.next();
    listIterator.set(falseItem);
    verify(fakeObservable, times(1)).onPropertyChanged(adapter, BR.empty);
    listIterator.next();
    listIterator.set(trueItem);
    verify(fakeObservable, times(2)).onPropertyChanged(adapter, BR.empty);
  }

  @Test
  public void testListIteratorInitialIndex() {
    ListIterator<TestItem> listIterator = adapter.listIterator(3);
    TestItem item = adapter.items().get(3);
    assertEquals(item, listIterator.next());
    assertEquals(item, listIterator.previous());
  }
}
