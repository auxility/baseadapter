package eu.theappshop.baseadapter.adapter;

import java.util.LinkedList;
import java.util.List;

import eu.theappshop.baseadapter.misc.Filter;
import eu.theappshop.baseadapter.vm.VM;

public class FilterableAdapter<V extends VM> extends BaseAdapter<V> {

    private final List<Filter<VM>> filters = new LinkedList<>();



}
