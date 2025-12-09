package Library;

public enum DamageType {
    TORN_PAGES(20),DIRTY(10),WET(35),COVER_TORN(15);

    private final int percent;
    DamageType(int percent){
        this.percent=percent;
    }

    public int getPercent() {
        return percent;
    }
}
