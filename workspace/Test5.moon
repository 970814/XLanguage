//setStdoutToFile:1.txt;
import System.s;
import Arrays.s;
def println(var);
println: {
	print(var);
	newline();
}
def main();
def smallToBig(var0, var2);
smallToBig: {
	return var0 > var2;
}
def bigToSmall(var0, var2);
bigToSmall: {
	return var0 < var2;
}
def notTrue(var0, var2);
notTrue: {
	return random(2);
}
def randomValue(&ptr);
randomValue: {
	ptr = random(10083);
}

main:{
	new array[100];
	println(currenttime());
	forEach(array, randomValue);
	//forEach(array, print);
	printar(array);
	quickSort(array, smallToBig);
	printar(array);
quickSort(array, bigToSmall);
printar(array);
	println(currenttime());
	//new time;
	//while((time = currenttime()) < 500){
		//println(time);
	//}
}



