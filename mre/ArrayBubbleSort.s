ArrayBubbleSort;
author: lmx;
since: 1.0;
import ArrayBubbleSort.moon;

/**
  * bubbleSort, default call bubbleSort0(array, 0, sizeof(array), cmp);
  *
  * array: the array to be sorted by bubbleSort.
  * cmp: the function pointer to a callback function
  */
def bubbleSort(array[], *cmp);

/**
  * bubbleSort0
  *
  * array: the array to be sorted by bubbleSort.
  * fromIndex: the index of the first element, inclusive, to be sorted.
  * toIndex: the ending index, exclusive.
  * cmp: the function pointer to a callback function
  */
def bubbleSort0(array[], fromIndex, toIndex, *cmp);