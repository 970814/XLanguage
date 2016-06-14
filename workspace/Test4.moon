set standard out to file: 1.txt;
def main();
import System.s;
main:{
	print(5);
	test(22,5,45,4,6,74,7,8,currenttime());
	currenttime();
	new src[20];
	new dest[30];
	memorycopy(src, 0, dest, 0, sizeof(src));
	currenttime();
	printar(src);
	printar(dest);
}
def test(...args);
test: {
	while(args){
		print(get args);
		newline();
	}
}

