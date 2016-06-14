import ArrayForEach.s;

external def forEach(array[], *consumer);

def up(&ptr);
up: {
    ++ptr;
}

def down(&ptr);
down: {
    ptr-=10;
}

def randomValue(&ptr);
randomValue: {
    if(sizeof(ptr)) {
        ptr = random(ptr);
    } else {
        ptr = random(100);
    }
}

def main();
main: {
    forEach(new array[10], up);
    forEach(array, down);
    forEach(array, randomValue);
}
