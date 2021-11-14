package bullscows;

import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[] alpha = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        int bulls = 0;
        int cows = 0;
        String user = "";
        int turn = 1;
        System.out.println("Input the length of the secret code:");
        String limit = scanner.nextLine();
        if (limit.matches("[a-zA-Z]") || limit.contains("-") || limit.equals("0")) {
            System.out.println("Error: \"" + limit + "\" isn't a valid number.");
        } else if(Integer.parseInt(limit) > 36) {
            System.out.println("Error: can't generate a secret number with a length of " + limit +
                    " because there aren't enough unique digits.");
        } else {
            System.out.println("Input the number of possible symbols in the code:");
            String num = scanner.nextLine();
            if (num.matches("[a-zA-Z]") || num.contains("-")) {
                System.out.println("Error: \"" + num + "\" isn't a valid number.");
            } else if(Integer.parseInt(limit) > Integer.parseInt(num)) {
                System.out.println("Error: can't generate a secret number with a length of " + limit +
                        " because there aren't enough unique digits.");
            } else if (Integer.parseInt(num) > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            } else {
                StringBuilder secret = secret_rand(limit, num, alpha);
                phrase(limit, num, alpha);
                while (true) {
                    System.out.println("Turn " + turn + ":");
                    user = scanner.next();
                    turn += 1;
                    for (int i = 0; i < secret.length(); i++) {
                        if (user.charAt(i) == secret.charAt(i)) {
                            bulls += 1;
                        } else if (user.contains(String.valueOf(secret.charAt(i)))) {
                            cows += 1;
                        }
                    }
                    count_animals(bulls, cows);
                    if (bulls == Integer.parseInt(limit)) {
                        System.out.println("Congratulations! You guessed the secret code.");
                        break;
                    } else {
                        bulls = 0;
                        cows = 0;
                    }
                }
            }
        }
    }

    private static void phrase(String limit, String num, char[] alpha) {
        StringBuilder star = new StringBuilder();
        star.append("*".repeat(Math.max(0, Integer.parseInt(limit))));
        if (Integer.parseInt(num) < 11) {
            System.out.println("The secret is prepared: " + star + " (0-9).");
        } else {
            System.out.println("The secret is prepared: " + star + " (0-9, " + "a-" + alpha[Integer.parseInt(num)-11] + ")." );
        }
        System.out.println("Okay, let's start a game!");
    }

    private static void count_animals(int bulls, int cows) {
        String msg = "";
        if (bulls == 0 && cows == 0) {
            msg = "None";
        } else if (bulls != 0 && cows == 0) {
            msg = bulls + " bull(s)";
        } else if (bulls == 0 && cows > 0) {
            msg = cows + " cow(s)";
        } else {
            msg = bulls + " bull(s) and " +  + cows + " cow(s)";
        }
        System.out.println("Grade: " + msg + ".");
    }

    public static StringBuilder secret_rand(String limit, String num, char[] alpha) {
        StringBuilder secret = new StringBuilder();
        while (secret.length() < Integer.parseInt(limit)) {
            Random rand = new Random();
            int r_d = rand.nextInt(Integer.parseInt(num));
            if (r_d <= 9) {
                if (!secret.toString().contains(String.valueOf(r_d))) {
                    secret.append((r_d));
                }
            } else  if (!secret.toString().contains(String.valueOf(alpha[r_d - 10]))){
                secret.append((alpha[r_d - 10]));
            }
            if (secret.length() == Integer.parseInt(limit)) {
                break;
            }
        }
        return secret;
    }
}

