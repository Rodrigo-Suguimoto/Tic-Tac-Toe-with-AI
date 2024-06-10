
class Cat {

    String name;
    int age;
    static int counter;

    public Cat(String name, int age) {
        final int tooManyCats = 6;

        counter++;
        if (counter >= tooManyCats) {
            System.out.println("You have too many cats");
        }
        this.name = name;
        this.age = age;
    }

    public static int getNumberOfCats() {
        return counter;
    }
}