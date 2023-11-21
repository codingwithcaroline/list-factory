import java.util.Iterator;
import java.util.Random;
 /**
 * RandomizedList.java. Describes the abstract behavior of a
 * randomized list collection; that is, a list with order defined as "random
 * order." The order described by a radomized list is "random" in the sense
 * that the element accessed by either the sample or remove method is selected
 * uniformly at random from the current elements in the list. In addition, an
 * iterator on a randomized list will sequentially access each element in some
 * uniformly random sequence. Simultaneous iterators on the same randomized
 * list are independent of each other. That is, they will with high probability
 * have different iteration sequences.
 * 
 * @author   Caroline Kirkconnell (CarolineKirkconnell8@gmail.com)
 * @version  2019-10-20
 */
public class RandomizedListClient<T> implements RandomizedList<T> {
   private T[] elements;
   private int size;
   private Random gen = new Random();
   private int light;
   
   @SuppressWarnings("unchecked")
   public RandomizedListClient() {
   
      elements = (T[]) new Object[1];
      size = 0;
   }
   /**
    * Adds the specified element to this list. If the element is null, this
    * method throws an IllegalArgumentException.
    */
   public void add(T element) {
   if (element == null) {
      throw new IllegalArgumentException();
   }
   if (size < elements.length) {
      elements[size] = element;
      size++;
   }
   else {
      expandList();
      add(element);
   }
   
   }
   @SuppressWarnings("unchecked")
   private void expandList() {
   T[] here = (T[]) new Object[size()*2];
   System.arraycopy(elements, 0, here, 0, elements.length);
   elements = here;
   }
  
   /**
    * Selects and removes an element selected uniformly at random from the
    * elements currently in the list. If the list is empty this method returns
    * null.
    */
   @SuppressWarnings("unchecked")
   public T remove() {
      if (isEmpty()) {
         return null;
      }
      if (size() == 1) {
         T here = elements [0];
         elements[0] = null;
         size--; 
         return here;
      }
      
      light = gen.nextInt(size());
      T rs = elements[light];
      elements[light] = elements[size() - 1];
      elements[size() - 1] = null;
      size--;
      
      if ((elements.length / 2) > size()) {
         repo();
      }
      
      return rs;
      }
      
      @SuppressWarnings("unchecked")
      private void repo() {
      T[] temp1 = (T[]) new Object[elements.length / 2];
      System.arraycopy(elements, 0, temp1, 0, temp1.length);
      
      elements = temp1;
      }  
   /**
    * Selects but does not remove an element selected uniformly at random from
    * the elements currently in the list. If the list is empty this method
    * return null.
    */
   public T sample() {
      if (isEmpty()) {
         return null;
      }
      
      light = gen.nextInt(size());
      return elements[light];
      }
   public int size() {
      return size;
   }
   public boolean isEmpty() {
      return (size == 0);
   }
   public Iterator<T> iterator() {
      return new RandomIterator();
   }
   private class RandomIterator implements Iterator<T> {
   public boolean hasNext() {
      if (!isEmpty()) {
         return true;
      }
      return false;
      }
      public T next() {
      light = gen.nextInt(size());
      return elements[light];
      }
      public void remove() {
         throw new UnsupportedOperationException();
      }
    }
   
}