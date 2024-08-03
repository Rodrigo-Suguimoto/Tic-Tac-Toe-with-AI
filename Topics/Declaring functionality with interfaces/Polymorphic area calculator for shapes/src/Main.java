import java.util.Scanner;

interface Shape {
    double calculateArea();
}

class Rectangle implements Shape {
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public double calculateArea() {
        return this.length * this.width;
    }
}

class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * Math.pow(this.radius, 2);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String shapeType = scanner.nextLine();

        Shape shape;

        if ("rectangle".equalsIgnoreCase(shapeType)) {
            double length = scanner.nextDouble();
            double width = scanner.nextDouble();
            shape = new Rectangle(length, width);
        } else if ("circle".equalsIgnoreCase(shapeType)) {
            double radius = scanner.nextDouble();
            shape = new Circle(radius);
        } else {
            System.out.println("Invalid shape type!");
            return;
        }

        double area = shape.calculateArea();
        System.out.printf("%.2f\n", area);
    }
}
