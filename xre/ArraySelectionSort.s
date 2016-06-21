ArraySelectionSort;
author: lmx;
since: 1.0;
import ArraySelectionSort.moon;

/**
  * selectionSort, default call selectionSort0(array, 0, sizeof(array), cmp);
  *
  * array: the array to be sorted by directionSelectionSort.
  * cmp: the function pointer to a callback function
  */
def selectionSort(array[], *cmp);

/**
  * selectionSort0
  *
  * array: the array to be sorted by directionSelectionSort.
  * fromIndex: the index of the first element, inclusive, to be sorted.
  * toIndex: the ending index, exclusive.
  * cmp: the function pointer to a callback function
  */
def selectionSort0(array[], fromIndex, toIndex, *cmp);