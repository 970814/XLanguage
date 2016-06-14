import ArrayForEach.s;
import ArraySelectionSort.s;

def randomV(&ptr);
randomV: {
    ptr = 1113 * rand;
}

def myCompare(var1, var2);
myCompare: {
    var1 > var2;
}

def main();
main: {
    new array[50];
    forEach(array, randomV);
    selectionSort(array, myCompare);
    //forEach0(array, 9, 19, randomV);
    //selectionSort0(array, 9, 19, myCompare);
}