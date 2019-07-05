# BaseAdapter
**_Use one adapter for all common ViewGroups._**
Have you ever seen Android project with dozens of RecyclerView or ViewPager Adapters? 
Using BaseAdapter allows you to get rid of boilerplate code and focus mainly on ViewGroups items' logic.
Designed to be used with Android Databinding Library. 
You don't have to create Different Adapters for different ViewGroups, 
just create BaseAdapter and bind to it via xml or corresponding static method and thats all. No more boilerplate adapters in your project.
## Quick overview
- Simple and extendible API similiar to List
- androidx support
- Supports RecyclerView, ViewPager
- Multiple ViewTypes support
- All notify methods of RecyclerView support
- BaseAdapter for simple cases
- FilterableAdapter for more complex cases if filtering is required
- Supports RecyclerView DiffUtil.Callback
- Supports TabLayout integration with ViewPager in case of using TitledItem
- Simple variable grid for RecyclerView with SpanGridLayoutManager and SpanItem
- Supports ViewPager item`s view state save/restore
- Bindable properties for Adapter size and empty flag
- Supports Serialization
## Installation ##

**DataBinding must be enabled for module you are using adapter in.**

You can add library to your project via Gradle

Step 1: Add in your root build.gradle of your project
```
allprojects {
    repositories {
      ...
      maven { url 'https://jitpack.io' }
    }
  }
```
Step 2: Add the dependency to your app gradle file
```
dependencies {
  ...
  implementation 'ca.auxility.baseadapter:$latestVersion'
  ...
}
```
## Usage ##
1. Create item`s layout xml file and place it under layout directory
```
<?xml version="1.0" encoding="utf-8"?>
<layout>
  <YourViews>
  ...
  </YourViews>
</layout>
```
2. Create class implementing Item interface or its successors and override ```getLayoutId``` method
```
public class YourItem implements Item {

   @Override public int getLayoutId() {
    return R.layout.your_item_layout_id;
  }
  
}
```
3. Add variable with name **item** (it is important) and type of class you created in previous step
```
<?xml version="1.0" encoding="utf-8"?>
<layout>
  <data>
    <variable
        name="item"
        type="com.yourpackage.YourItem"
        />
  </data>
  <YourViews>
  ...
  </YourViews>
</layout>
```
4. Create Adapter instance and pass vms to display
```
public class YourViewModel {
  
  Adapter<YourItem> adapter;
  ...
  List<YourItem> items = new ArrayList();
  items.add(new YourItem());
  adapter = new BaseAdapter(items);
  ...
}
```
5. Bind view to adapter with a help of predefined Binding Adapters in xml 
or corresponding static method from code in Fragment or Activity for example
```
<?xml version="1.0" encoding="utf-8"?>
<layout>
  <data>
    <variable
        name="yourViewModel"
        type="com.yourpackage.YourViewModel"
        />
  </data>
  
  ...

  <androidx.recyclerview.widget.RecyclerView
      android:id="@+id/recyclerView"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      adapter="@{yourViewModel.adapter}"
      app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
      />
      
  ...

</layout>
```
Alternatively
```
...
import ca.auxility.baseadapter.view.recyclerview.RecyclerViewBindingAdapter;
...
RecyclerView rv;
Adapter<YourItem> adapter;
...
RecyclerViewBindingAdapter._bindAdapter(rv, adapter);
```

You can use different Item types inside BaseAdapter

```
public class YourItem1 implements SpanItem {

   @Override public int getLayoutId() {
    return R.layout.your_item_layout_id;
  }
}
```

```
public class YourItem implements SpanItem {

   @Override public int getLayoutId() {
    return R.layout.your_item_layout_id;
  }
}
```

```
public class YourViewModel {
  
  Adapter<Item> adapter;
  ...
  List<Item> items = new ArrayList();
  items.add(new YourItem());
  items.add(new YourItem1());
  adapter = new BaseAdapter(items);
  ...
}
```

Using ```FilterableAdapter``` looks the same, except adapter initialization
```
public class YourViewModel {
  
  Adapter<YourItem> adapter;
  ...
  List<YourItem> items = new ArrayList();
  items.add(new YourItem());
  adapter = new FilterableAdapter(items, new SerializablePredicate<YourItem>() {
      @Override public Boolean apply(@NonNull YourItem item) {
        ... //implement filtering logic
      }
    });
  ...
}
```
You can use all types of adapters with other ViewGroups (only ViewPager by now)
```
...
<androidx.viewpager.widget.ViewPager
        android:id="@+id/pager"
        adapter="@{item.adapter}"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        />
        ...
```

Adapter and its successors support TabLayout integration with ViewPager 
in case of implementing ```TitledItem``` interface instead of ```Item``` and overriding ```String getTitle()``` method
```
  ...
  Viewpager pager;
  TabLayout tabLayout;
  ...
  tabLayout.setupWithViewPager(pager);
```

Sometimes it is required to use ```GridLayoutManager``` for RecyclerView with different span sizes (variable grid). 
In this case you can impelement ```SpanItem``` instead of ```Item``` and override ```int getSpanSize()``` method 
along with ```SpanGridLayoutManager``` as layout manager for RecyclerView. 
This number represents the relative size of current item in RecyclerView, 
where maximum span size is passed to as a constructor parameter SpanGridLayoutManager.

```
public class YourItem1 implements SpanItem {

   @Override public int getLayoutId() {
    return R.layout.your_item_layout_id;
  }
  
  @Override int getSpanSize() {
    return 1;
  }
  
}
```

```
public class YourItem implements SpanItem {

   @Override public int getLayoutId() {
    return R.layout.your_item1_layout_id;
  }
  
  @Override int getSpanSize() {
    return 2;
  }
  
}
```

```
public class MyFragment extends Fragment {

  RecyclerView rv;
  Adapter<YourItem> adapter;

  @Override
  public View onCreateView(...) {
    ...
    //adapter initialization with instances of YourItem and YourItem1
    RecyclerView.LayoutManager gridManager = SpanGridLayoutManager(getContext(), 3, adapter)
    //adapter binding
    ...
  }

}
```
As a result YourItem will take 1/3 and YourItem1 2/3 of RecyclerView width or height depending on orientation.
## Coming Soon ###
- TabLayout complete support
- PagingAdapter
- Stateless Binding Adapter for ViewPager
- ViewGroupAdapter
- ViewPager2 support
- AutoCompleteTextView support
- Deprecated AbsListView support (ListView, GridView)

## License ##
```MIT License

Copyright (c) 2019 Auxility

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
