package tw.meowdev.android.starter.list;

import java.util.ArrayList;

public class CircularLzList<E> {

    private final int capacity;
    private int head;   // The position of current `start`.
    private int start_;  // Current first index of data in this list.
    private int size_;  // How many data buffered in this list.

    private ArrayList<E> arrList;

    public CircularLzList(int capacity) {
        arrList = new ArrayList<E>(capacity);

        this.capacity = capacity;
        this.head = 0;
        this.size_ = 0;
    }

    public int start()
    {
        return this.start_;
    }

    public int size()
    {
        return this.size_;
    }

    private int getArrIndex(int index) {
        if (!checkInBuffer(index)) return -1;

        int offset = index - this.start_;
        return (offset + this.head) % arrList.size();
    }

    public boolean checkInBuffer(int index) {
        return !(index < this.start_ || index >= this.start_+this.size_);
    }

    public E get(int index) {
        int arrIndex = getArrIndex(index);
        if (arrIndex < 0) return null;
        return arrList.get(arrIndex);
    }

    public void push(E e) {
        if (this.start_ > 0) {
            int arrSize = arrList.size();
            this.start_--;
            this.head = (this.head-1+arrSize) % arrSize;


            arrList.set(this.head, e);
        }
    }

    public void append(E e) {
        if (this.size_ < this.capacity) {
            arrList.add(e);
            this.size_++;
        }
        else {
            this.start_ = this.start_+1;
            this.head = (this.head+1) % this.capacity;

            arrList.set(getArrIndex(this.start_+this.size_-1), e);
        }
    }
}
