package eu.theappshop.baseadapter.observer;

public interface VMObserver {

    void notifyDataSetChanged();

    void notifyItemInserted(int position);

    void notifyItemRangeInserted(int positionStart, int itemCount);

    void notifyItemRemoved(int position);

    void notifyItemRangeRemoved(int positionStart, int itemCount);
}
