import System.s;
def println(...args);
println: {
	new n = args;
	while(args){
		print(get(args));
		newline();
	}
	return n;
}
def main();
main: {
println(1,2,3,4,5,6,7,8,9,10);
	call(main);
	return println(1,2,3,4,5,6,7,8,9,10);
}
def call(*fp);
call: {
	*fp();
}


