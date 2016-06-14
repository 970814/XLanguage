import System.s;
def setArrayValueByInput(array[]);
setArrayValueByInput: {
    new size = sizeof(array);
    while(--size >= 0) {
        printraw(new 请输入数组的值在索引);
        print(size);
        printraw(new _);
        array[size] = scan();               //从标准输入获取数据为数组每一个值赋值;
                                            //scan()有关更多信息查看System.s标准库文档;
    }
}
def main();
main: {
    printraw(new 请输入数组的长度);
    new array[scan()];                      //实例化一个数组长度由用户输入决定;
    printar(array);                         //打印初始化数组元素;
    setArrayValueByInput(array);            //由用户设置数组的每一个元素;
    printar(array);                         //再次打印数组元素;
    return 0;
}

