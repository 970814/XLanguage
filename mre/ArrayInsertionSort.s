ArrayInsertionSort;
author: lmx;
since: 1.0;
import ArrayInsertionSort.moon;

/**
  * insertionSort, default call mergeSort0(array, 0, sizeof(array), cmp);
  *
  * array: the array to be sorted by insertionSort.
  * cmp: the function pointer to a callback function
  */
def insertionSort(array[], *cmp);

/**
  * insertionSort0
  *
  * array: the array to be sorted by insertionSort.
  * fromIndex: the index of the first element, inclusive, to be sorted.
  * toIndex: the ending index, exclusive.
  * cmp: the function pointer to a callback function
  */
def insertionSort0(array[], fromIndex, toIndex, *cmp);