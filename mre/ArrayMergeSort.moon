ArrayMergeSort;
author: lmx;
since: 1.0;
import System.s;
/**
  * there are two sequences merge,
  * the first sequence: fromIndex0 to fromIndex2,
  *	the second sequence: fromIndex2 to toIndex,
  * cmp: the function pointer to a callback function.
  */
def merge(array[], fromIndex0, fromIndex2, toIndex, *cmp);

mergeSort: {
	mergeSort0
	(array, 0, sizeof(array), cmp);
}

mergeSort0: {
	if (toIndex - fromIndex > 1) {
        new middleIndex = int((toIndex - fromIndex) / 2) + fromIndex;
        if (toIndex - fromIndex > 2) {
            mergeSort0(array, fromIndex, middleIndex, cmp);
            mergeSort0(array, middleIndex, toIndex, cmp);
        }
        merge(array, fromIndex, middleIndex, toIndex, cmp);
    }
}

merge: {
	new temp[toIndex - fromIndex0];
    new k = 0;
    new i = fromIndex0;
    new j = fromIndex2;
    while (i < fromIndex2 && j < toIndex) {
		if(*cmp(array[j], array[i])){
			temp[k] = array[i];
			++i;
		}else{
			temp[k] = array[j];
			++j;
		}
		++k;
    }
    if (i < fromIndex2) {
        new t = fromIndex2 - i;
		new m = 0;
		while(m < t){
			array[--toIndex] = array[--fromIndex2];
			++m;
		}
    }
    //arraycopy(temp, 0, array, fromIndex0, k);
	memorycopy(temp, 0, array, fromIndex0, k);
}