QuickSwap;
author: lmx;
since: 1.0;

quickSwap:{
    new temp = ptr0;
    ptr0 = ptr2;
    ptr2 = temp;
}