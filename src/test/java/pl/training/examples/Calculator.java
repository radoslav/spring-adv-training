package pl.training.examples;

public class Calculator {

    public double add(double firstNumber, double secondNumber) {
        return firstNumber + secondNumber;
    }

    public double substract(double firstNumber, double secondNumber) {
        return firstNumber - secondNumber;
    }

    public double divide(double firstNumber, double secondNumber) {
        if (secondNumber == 0) {
            throw new IllegalArgumentException();
        }
        return firstNumber / secondNumber;
    }

}
