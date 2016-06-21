import System.s;
import ArrayMergeSort.s;
import ArrayNewMergeSort.s;
import ArrayForEach.s;
def main();
main:{
	printraw(new please_input_size_of_the_array__);
	new array[scan()];
	forEach(array, randomValue);
	newline();
	newMergeSort(array,bigToSmall);
	mergeSort(array,bigToSmall);
	
	new t0 + new t1;
	
	new start0 = currenttime();
	mergeSort(array, smallToBig);
	t0 = currenttime() - start0;
	
	start0 = currenttime();
	newMergeSort(array, smallToBig);
	t1 = currenttime() - start0;
	
	printraw(new Time_of_MergeSort_Cost__);
	print(t0);
	newline();
	printraw(new Time_of_NewMergeSort_Cost__);
	print(t1);
	newline();
	
	//forEach(array,print);
	return currenttime();
}
def smallToBig(a, b);
smallToBig: {
	return a > b;
}
def bigToSmall(a, b);
bigToSmall: {
	return a < b;
}
def randomValue(&ptr);
randomValue: {
	ptr = random(100);
	//printraw(new new_value_);
	//print(ptr);
	//newline();
}