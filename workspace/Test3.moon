import ArrayBubbleSort.s;
import ArrayForEach.s;
import System.s;
def smallToBig(a, b);
smallToBig: {
	return a > b; 
}
def randomV(&element);
randomV: {
	return element = random(2016);//sqrt(rand * 104);
}
def main();
main: {
	new array[50];
	forEach(array, randomV);
	printar(array);
	bubbleSort(array, smallToBig);
	printar(array);
	return 0;
}


