//X language
//函数式编程, 支持c++风格的传引用调用, 也有c语言的函数指针回调函数, 同样支持引用外部文件, 标准库函数.
//也有java程序员熟悉使用的new关键字, 还有严格的语法检查, 和委婉的异常报告方式.
//更强大的是, 其有丰富的内置运算符, 并且可根据程序员的喜好使用自然语言或者使用记号标识.
import ArrayQuickSort.s;
import ArrayForEach.s;

def smallToBig(var0, var1);
smallToBig: {
    var0 > var1;
}

def randomV(&ptr);
randomV: {
    ptr = random(2016);
}

external def quickSort(array[], *cmp);
external def forEach(array[], *consumer);

def main();
main:{
    new array[200];
    forEach(array, randomV);
    quickSort(array, smallToBig);
    print(array);
}



