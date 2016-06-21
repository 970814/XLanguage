ArrayQuickSort;
author: lmx;
since: 1.0;
import ArrayQuickSort.moon;

/**
  * quickSort, default call quickSort0(array, 0, sizeof(array) - 1, cmp);
  *
  * array: the array to be sorted
  * cmp: the function pointer to a callback function
  */
def quickSort(array[], *cmp);

/**
  * quickSort0
  *
  * array: the array to be sorted
  * left: the index of the first element, inclusive, to be sorted
  * right: the index of the last element, inclusive, to be sorted
  * cmp: the function pointer to a callback function
  */
def quickSort0(array[], left, right, *cmp);
