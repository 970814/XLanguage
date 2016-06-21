import System.s;
import Arrays.s;
def compileSorter();
compileSorter: {
	//printraw(new Please_input_the_size_of_compile_array_);
	new array[100/*scan()*/];
	new i = -1;
	forEach(array, randomValue);
	newMergeSort(array, bigToSmall);
	
	new start = currenttime();
	
	bubbleSort(array, smallToBig);
	bubbleSort(array, bigToSmall);
	selectionSort(array, smallToBig);
	selectionSort(array, bigToSmall);
	insertionSort(array, smallToBig);
	insertionSort(array, bigToSmall);
	quickSort(array, smallToBig);
	quickSort(array, bigToSmall);
	mergeSort(array, smallToBig);
	mergeSort(array, bigToSmall);
	newMergeSort(array, smallToBig);
	newMergeSort(array, bigToSmall);
	
	return currenttime() - start; 
}
def theWorst(array[], times[]);
theWorst: {
	new i= -1;
	new cost;
	//printar(array);
	printrawln(new Sort_start);
	//For each sort, consider the worst case scenario.
	/**
	  * sort 1
	  */
	cost = currenttime();
	bubbleSort(array, smallToBig);
	times[++i] = currenttime() - cost;
	
	//printar(array);
	/**
	  * sort 2
	  */
	cost = currenttime();
	selectionSort(array, bigToSmall);
	times[++i] = currenttime() - cost;
	
	//printar(array);
	/**
	  * sort 3
	  */
	cost = currenttime();
	insertionSort(array, smallToBig);
	times[++i] = currenttime() - cost;
	
	//printar(array);
	/**
	  * sort 4
	  */
	cost = currenttime();
	quickSort(array, bigToSmall);
	times[++i] = currenttime() - cost;
	
	//printar(array);
	/**
	  * sort 5
	  */
	cost = currenttime();
	mergeSort(array, smallToBig);
	times[++i] = currenttime() - cost;
	
	//printar(array);
	/**
	  * sort 6
	  */
	cost = currenttime();
	newMergeSort(array, bigToSmall);
	times[++i] = currenttime() - cost;
	
	//printar(array);
	
	i = -1;
	
	printrawln(new The_Worst___);
	printraw(new _1_Time_of_BubbleSort_Cost__);
	println(times[++i]);
	printraw(new _2_Time_of_SelectionSort_Cost__);
	println(times[++i]);
	printraw(new _3_Time_of_InsertionSort_Cost__);
	println(times[++i]);
	printraw(new _4_Time_of_QuickSort_Cost__);
	println(times[++i]);
	printraw(new _5_Time_of_MergeSort_Cost__);
	println(times[++i]);
	printraw(new _6_Time_of_NewMergeSort_Cost__);
	println(times[++i]);
}

def theBest(array[], times[]);
theBest: {
	new i= -1;
	new cost;
	//printar(array);
	printrawln(new Sort_start);
	//For each sort, consider the best case scenario.
	/**
	  * sort 1
	  */
	cost = currenttime();
	bubbleSort(array, bigToSmall);
	times[++i] = currenttime() - cost;
	
	//printar(array);
	/**
	  * sort 2
	  */
	cost = currenttime();
	selectionSort(array, bigToSmall);
	times[++i] = currenttime() - cost;
	
	//printar(array);
	/**
	  * sort 3
	  */
	cost = currenttime();
	insertionSort(array, bigToSmall);
	times[++i] = currenttime() - cost;
	
	//printar(array);
	/**
	  * sort 4
	  */
	cost = currenttime();
	quickSort(array, bigToSmall);
	times[++i] = currenttime() - cost;
	
	//printar(array);
	/**
	  * sort 5
	  */
	cost = currenttime();
	mergeSort(array, bigToSmall);
	times[++i] = currenttime() - cost;
	
	//printar(array);
	/**
	  * sort 6
	  */
	cost = currenttime();
	newMergeSort(array, bigToSmall);
	times[++i] = currenttime() - cost;
	
	//printar(array);
	
	i = -1;
	
	printrawln(new The_Best___);
	printraw(new _1_Time_of_BubbleSort_Cost__);
	println(times[++i]);
	printraw(new _2_Time_of_SelectionSort_Cost__);
	println(times[++i]);
	printraw(new _3_Time_of_InsertionSort_Cost__);
	println(times[++i]);
	printraw(new _4_Time_of_QuickSort_Cost__);
	println(times[++i]);
	printraw(new _5_Time_of_MergeSort_Cost__);
	println(times[++i]);
	printraw(new _6_Time_of_NewMergeSort_Cost__);
	println(times[++i]);
}


def theRandom(array[], times[]);
theRandom: {
	new i= -1;
	new cost;
	//printar(array);
	printrawln(new Sort_start);
	//For each sort, consider the random case scenario.
	forEach(array, randomValue);
	/**
	  * sort 1
	  */
	cost = currenttime();
	bubbleSort(array, bigToSmall);
	times[++i] = currenttime() - cost;
	
	//printar(array);
	forEach(array, randomValue);
	/**
	  * sort 2
	  */
	cost = currenttime();
	selectionSort(array, bigToSmall);
	times[++i] = currenttime() - cost;
	
	//printar(array);
	forEach(array, randomValue);
	/**
	  * sort 3
	  */
	cost = currenttime();
	insertionSort(array, bigToSmall);
	times[++i] = currenttime() - cost;
	
	//printar(array);
	forEach(array, randomValue);
	/**
	  * sort 4
	  */
	cost = currenttime();
	quickSort(array, bigToSmall);
	times[++i] = currenttime() - cost;
	
	//printar(array);
	forEach(array, randomValue);
	/**
	  * sort 5
	  */
	cost = currenttime();
	mergeSort(array, bigToSmall);
	times[++i] = currenttime() - cost;
	
	//printar(array);
	forEach(array, randomValue);
	/**
	  * sort 6
	  */
	cost = currenttime();
	newMergeSort(array, bigToSmall);
	times[++i] = currenttime() - cost;
	
	//printar(array);
	
	i = -1;
	
	printrawln(new The_Random___);
	printraw(new _1_Time_of_BubbleSort_Cost__);
	println(times[++i]);
	printraw(new _2_Time_of_SelectionSort_Cost__);
	println(times[++i]);
	printraw(new _3_Time_of_InsertionSort_Cost__);
	println(times[++i]);
	printraw(new _4_Time_of_QuickSort_Cost__);
	println(times[++i]);
	printraw(new _5_Time_of_MergeSort_Cost__);
	println(times[++i]);
	printraw(new _6_Time_of_NewMergeSort_Cost__);
	println(times[++i]);
}
def main();
main:{
	new cost = compileSorter();
	printraw(new For_the_first_compile_cost_time__);
	println(cost);
	
	new times[6];
	
	//printraw(new please_input_size_of_the_test_array__);
	//new size = scan();
	new array[100000];
	printraw(new test_array_size__);
	println(sizeof(array));
	forEach(array, randomValue);
	newMergeSort(array, bigToSmall);
	theBest(array, times);
	newline();
	newline();
	theRandom(array, times);
	newline();
	newline();
	theWorst(array, times);
	newline();
	newline();
	
	
	//forEach(array,print);
	printrawln(new _Run_this_program_cost_time__);
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
	ptr = random(1000000);
	//printraw(new new_value_);
	//print(ptr);
	//newline();
}
def println(var);
println: {
	print(var);
	newline();
}
def printrawln(&ptr);
printrawln: {
	printraw(ptr);
	newline();
}