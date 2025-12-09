package Library;

import java.util.List;

    public class Damage {

        private final List<DamageType> damageList;
        private final int totalDamage;
        private final int damageLimit = 60; // nad tím kniha zničena

        public Damage(List<DamageType> damageList) {
            this.damageList = damageList;
            this.totalDamage = calculateTotalDamage();
        }

        private int calculateTotalDamage() {
            return damageList.stream()
                    .mapToInt(DamageType::getPercent)
                    .sum();
        }

        public int getTotalDamage() {
            return totalDamage;
        }

        public boolean isDestroyed() {
            return totalDamage > damageLimit;
        }

        public List<DamageType> getDamageList() {
            return damageList;
        }

        @Override
        public String toString() {
            return "Damage{" +
                    "totalDamage=" + totalDamage +
                    "%, types=" + damageList +
                    ", destroyed=" + isDestroyed() +
                    '}';
        }
    }

