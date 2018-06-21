package eu.theappshop.baseadapter.adapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import eu.theappshop.baseadapter.misc.Filter;
import eu.theappshop.baseadapter.vm.VM;

public class FilterableAdapter<V extends VM> extends BaseAdapter<V> {

    private final List<Filter<V>> filters;

    private List<V> allVms;

    public FilterableAdapter() {
        allVms = new ArrayList<>();
        filters = new LinkedList<>();
    }

    public FilterableAdapter(List<V> allVms) {
        super(allVms);
        filters = new LinkedList<>();
    }

    public FilterableAdapter(List<V> allVms, List<Filter<V>> filters) {
        this.allVms = allVms;
        this.filters = filters;
    }
}
