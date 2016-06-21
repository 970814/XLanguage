ArrayNewMergeSort;

author: lmx;
since: 1.0;
import ArrayMergeSort.moon;

newMergeSort: {
	newMergeSort0
	(array, 0, sizeof(array), cmp);
}

newMergeSort0: {
	new max_per_n = 1;
    while (max_per_n < toIndex - fromIndex) {
		new i = fromIndex;
		while (i < toIndex) {
			new fromIndex0 = i;
			new fromIndex2 = (i += max_per_n);
			new j;
			if((i += max_per_n) > toIndex){
				j = toIndex;
			} else {
				j = i;
			}
			merge(array, fromIndex0, fromIndex2, j, cmp);
			if (i + max_per_n >= toIndex) {
				i = toIndex //break;
			}
		}
		max_per_n *= 2;
    }
}