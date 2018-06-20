package eu.theappshop.baseadapter.vm;

public interface SpannedVM extends VM {

    int MAX_SPAN_SIZE = 12;

    /**
     * @return weight of the item it terms of spanned grid. MAX_SPAN_SIZE - full width, 1 - smallest span
     */
    int getSpanSize();
}
