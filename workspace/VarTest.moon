import System.s;
def main();
main: {
	new n = testVar(new array[10], 1, 2, 3, 4, 5, rand, rand*2);
	printar(array);
	newline();
	return n;
}
def testVar(array[], ...args);
testVar: {
	new i = -1;
	while(args){
		array[++i] = get(args);
	}
	return i;
}
