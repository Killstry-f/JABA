package etaptri;

import java.util.Scanner;

public class Calc {
    public static void main(String[] args) {
        System.out.println("Введите операцию: ");
        System.out.println("1. Сложение");
        System.out.println("2. Вычитание");
        System.out.println("3. Умножение");
        System.out.println("4. Деление");
        
        Scanner scanner = new Scanner(System.in);
        int operation = scanner.nextInt();
        
        System.out.println("Введите первое число: ");
        double x = scanner.nextDouble();
        
        System.out.println("Введите второе число: ");
        double y = scanner.nextDouble();
        
        double result = 0;
        
        switch (operation) {
            case 1:
                result = x + y;
                break;
            case 2:
                result = x - y;
                break;
            case 3:
                result = x * y;
                break;
            case 4:
                if (y != 0) {
                    result = x / y;
                } else {
                    System.out.println("Ошибка: деление на ноль!");
                    return;
                }
                break;
            default:
                System.out.println("Неверная операция!");
                return;
        }
        
        System.out.println("Результат = " + result);
        System.out.println("пупупупу");
        
        scanner.close();
    }
}