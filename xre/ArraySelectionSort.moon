ArrayQuickSort;
author: lmx;
since: 1.0;
import QuickSwap.s;//use quickSwap

selectionSort: {
    selectionSort0
    (array, 0, sizeof(array), cmp);
}

selectionSort0: {
	new theMostIndex;
	new j;
    while(fromIndex < toIndex - 1){
         //new theMostIndex = (new j = fromIndex); //it will be slow.
		 theMostIndex = (j = fromIndex);
         while(++j < toIndex){
              if(*cmp(array[theMostIndex], array[j])){
                    theMostIndex = j;
              }
         }
         if(theMostIndex != fromIndex){
              //arrayElementSwap(array, minIndex, fromIndex);
              quickSwap(array[theMostIndex], array[fromIndex]);
         }
         ++fromIndex;
    }
}
