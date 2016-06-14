ArrayForEach;
author: lmx;
since: 1.0;


forEach: {
    forEach0
    (array, 0, sizeof(array), consumer);
}

forEach0: {
    while(--toIndex >= fromIndex){
        *consumer(array[toIndex]);
    }
}