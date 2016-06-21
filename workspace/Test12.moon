import Arrays.s;
import System.s;
def main();
main:{
	new array[10000000];
	forEach(array, randomValue);
	newMergeSort(array, smallToBig);
	newMergeSort(array, bigToSmall);
	printraw(new _array_size_);
	print(sizeof(array));
	newline();
	
	new s = currenttime();
	newMergeSort(array, smallToBig);
	s = currenttime() - s;
	
	printraw(new _cost_);
	print(s);
	newline();
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
}