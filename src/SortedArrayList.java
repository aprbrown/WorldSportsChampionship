import java.util.ArrayList;

/**
 * Class to extend capability of ArrayList by enforcing order when items are added to the list
 * @param <E>
 *
 * @author Andrew Brown
 */
public class SortedArrayList<E extends Comparable<? super E>> extends ArrayList<E> {

    /**
     * Overrides the boolean add(E e) method. If the list is empty the item is added automatically, subsequent items are
     * compared to existing elements and if the existing element is greater than the new element, the new element is
     * inserted into its position.
     *
     * @param e element to be appended to this list
     * @return {@code true} (as specified by {Collection#add})
     */
    @Override
    public boolean add(E e) {
        if (isEmpty()) {
            super.add(e);

        } else {
            for (int i = 0; i < modCount; i++) {
                if (e.compareTo(get(i)) < 0){
                    add(i, e);
                    break;
                }
                if (i == modCount-1) {
                    super.add(e);
                    break;
                }

            }
        }
        return true;
    }
}