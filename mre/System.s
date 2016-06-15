System;
author: lmx;
since: 1.0;

/**
  * read a line from standard input.
  * then return double value parsed by it.
  */
def native scan();

/**
  * write the \'\\n\' to standard out.
  */
def native newline();

/**
  * write the var to standard out.
  * var: the value.
  */
def native print(var);

/**
  * write an array to standard out.
  * array: the value.
  */
def native printar(ar[]);

/**
  * get time operating system.
  */
def native currenttime();

/**
  * write the name of var to standard out.
  * name: pointer reference a var.
  */
def native printraw(&name);

/**
  * Its bottom is implemented by c ++，so it is very quick.
  * src: the source array.
  * srcPos: starting position in the source array.
  * dest: the destination array.
  * destPos: starting position in the destination data.
  * length: the number of array elements to be copied.
  */
def native memorycopy(src[], srcPos, dest[], destPos, length);

/**
  * src: the source array.
  * srcPos: starting position in the source array.
  * dest: the destination array.
  * destPos: starting position in the destination data.
  * length: the number of array elements to be copied.
  */
def arraycopy(src[], srcPos, dest[], destPos, length);

arraycopy: {
	--srcPos;
	--destPos;
	while(--length >= 0){
		dest[++destPos] = src[++srcPos];
	}
}



