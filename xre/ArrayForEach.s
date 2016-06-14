ArrayForEach;
author: lmx;
since: 1.0;
import ArrayForEach.moon;

/**
  * forEach, default call forEach0(array, 0, sizeof(array), consumer);
  *
  * array: for each the element of array, callback function consumer
  * consumer: the function pointer to a callback function
  */
def forEach(array[], *consumer);

/**
  * forEach0, do the same thing for each of (toIndex - fromIndex) elements
  *
  * array: for each the element of array, callback function consumer
  * fromIndex: the index of the first element, inclusive, to be sorted.
  * toIndex: the ending index, exclusive.
  * consumer: the function pointer to a callback function
  */
def forEach0(array[], fromIndex, toIndex, *consumer);