package Library;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DemagedUserInput {

    private final Scanner sc = new Scanner(System.in);

    // Hlavní metoda, která řeší poškození knihy
    public List<DamageType> askDamage() {
        List<DamageType> result = new ArrayList<>();

        System.out.println("Je kniha poškozena? (ano/ne)");

        YesNo isDamaged = askYesNo();

        if (isDamaged == YesNo.NO) {
            return result; // není poškozená
        }

        // Nyní se ptáme na jednotlivé typy poškození
        boolean adding = true;
        while (adding) {
            System.out.println("Vyber typ poškození:");
            System.out.println("1 - Roztržené stránky");
            System.out.println("2 - Špinavé");
            System.out.println("3 - Promočené");
            System.out.println("4 - Roztržený obal");

            int choice = askInt(1, 4);

            switch (choice) {
                case 1 -> result.add(DamageType.TORN_PAGES);
                case 2 -> result.add(DamageType.DIRTY);
                case 3 -> result.add(DamageType.WET);
                case 4 -> result.add(DamageType.COVER_TORN);
            }

            System.out.println("Přidat další poškození? (ano/ne)");
            adding = askYesNo() == YesNo.YES;
        }

        return result;
    }

    // ===== Pomocné metody =====

    private YesNo askYesNo() {
        YesNo result;
        do {
            result = YesNo.parse(sc.nextLine());
            if (result == null) {
                System.out.println("Neplatná volba — napiš 'ano' nebo 'ne'.");
            }
        } while (result == null);
        return result;
    }

    private int askInt(int min, int max) {
        while (true) {
            try {
                int num = Integer.parseInt(sc.nextLine());
                if (num >= min && num <= max) {
                    return num;
                }
            } catch (Exception ignore) {}
            System.out.println("Neplatná volba, zadej číslo " + min + "–" + max);
        }
    }
}