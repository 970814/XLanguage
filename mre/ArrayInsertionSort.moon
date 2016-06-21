ArrayInsertionSort;
author: lmx;
since: 1.0;
import System.s;

insertionSort: {
	insertionSort0
	(array, 0, sizeof(array), cmp);
}

insertionSort0: {
	while (++fromIndex < toIndex) {
		new key = array[fromIndex];
		new i = fromIndex - 1;
		new flag = 1;
		while (i >= 0 && flag) {
			if(*cmp(array[i], key)){
				array[i + 1] = array[i];
				--i;
			}else{
				flag = 0;
			}
		}
		array[i + 1] = key;
	}
}
