ArrayQuickSort;
author: lmx;
since: 1.0;

quickSort: {
    quickSort0
    (array, 0, sizeof(array) - 1, cmp);
}

quickSort0: {
    if (left < 0 || right < 0 || left >= right) {
		return 0;
    }
    new fromIndex = left;
    new toIndex = right;
    new key = array[left];
    new isRight = 1;
    while (left < right) {
        if (isRight) {
            if (*cmp(key, array[right])) {
                isRight = 0;
                array[left] = array[right];
                ++left;
            } else {
                --right;
            }
        } else {
            if (not *cmp(key, array[left])) {
                isRight = 1;
                array[right] = array[left];
                --right;
            } else {
                ++left;
            }
        }
    }
    array[left] = key;
    quickSort0(array, fromIndex, left - 1, cmp);
    quickSort0(array, left + 1, toIndex, cmp);
}