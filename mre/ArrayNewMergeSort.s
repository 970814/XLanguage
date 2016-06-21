ArrayNewMergeSort;
author: lmx;
since: 1.0;
import ArrayNewMergeSort.moon;

/**
  * newMergeSort, default call newMergeSort0(array, 0, sizeof(array), cmp);
  *
  * array: the array to be sorted by newMergeSort.
  * cmp: the function pointer to a callback function
  */
def newMergeSort(array[], *cmp);

/**
  * newMergeSort0
  *
  * array: the array to be sorted by newMergeSort.
  * fromIndex: the index of the first element, inclusive, to be sorted.
  * toIndex: the ending index, exclusive.
  * cmp: the function pointer to a callback function
  */
def newMergeSort0(array[], fromIndex, toIndex, *cmp);