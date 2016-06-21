ArrayMergeSort;
author: lmx;
since: 1.0;
import ArrayMergeSort.moon;

/**
  * mergeSort, default call mergeSort0(array, 0, sizeof(array), cmp);
  *
  * array: the array to be sorted by mergeSort.
  * cmp: the function pointer to a callback function
  */
def mergeSort(array[], *cmp);

/**
  * mergeSort0
  *
  * array: the array to be sorted by mergeSort.
  * fromIndex: the index of the first element, inclusive, to be sorted.
  * toIndex: the ending index, exclusive.
  * cmp: the function pointer to a callback function
  */
def mergeSort0(array[], fromIndex, toIndex, *cmp);