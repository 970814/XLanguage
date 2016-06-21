import System.s;
import ArrayInsertionSort.s;
import ArrayForEach.s;
def main();
main:{
	printraw(new please_input_size_of_the_array__);
	new array[scan()];
	forEach(array, randomValue);
	newline();
	insertionSort(array, bigToSmall);
	forEach(array,print);
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
	printraw(new new_value_);
	print(ptr);
	newline();
}