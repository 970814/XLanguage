ArrayBubbleSort;
author: lmx;
since: 1.0;
import QuickSwap.s;//use quickSwap

bubbleSort: {
    bubbleSort0
    (array, 0, sizeof(array), cmp);
}

bubbleSort0: {
    while (fromIndex < toIndex - 1) {
        new i = 0;
        while (++i < toIndex - fromIndex) {
            if (*cmp(array[i - 1], array[i])) {
                quickSwap(array[i - 1], array[i]);
            }
        }
        ++fromIndex;
    }
}