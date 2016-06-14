import System.s;
def main();
//judge an element wheather is a prime.
def isPrime(value);
isPrime: {
	new i = 2;
	if(value < i){
		return 0;
	}
	//new flag = 1;
	while(i power 2 <= value /*&& flag*/){
		if(value mod i == 0){
			return 0;
		}
		++i;
	}
	return 1;
}
def println(var);
println: {
	print(var);
	newline();
}
def main();
main: {
	new i = 0;
	new count = 0;
	while(++i <= 100000){
		if(isPrime(i)){
			print(i);
			if(++count | 10 == 9){
				newline();
			}
		}
	}
	//scan();
	return 0;
}












