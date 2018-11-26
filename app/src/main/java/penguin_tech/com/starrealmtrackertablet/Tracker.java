package penguin_tech.com.starrealmtrackertablet;

public class Tracker {

    private int authority;
    private int trade;
    private int combat;

    public Tracker() {
        authority = 50;
        trade = 0;
        combat = 0;
    }

    public Tracker(int authority, int trade, int combat) {
        this.authority = authority;
        this.trade = trade;
        this.combat = combat;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    public void changeAuthority(int value) {
        authority += value;
    }

    public int getTrade() {
        return trade;
    }

    public void setTrade(int trade) {
        this.trade = trade;
    }

    public void changeTrade(int value) {
        trade += value;
    }

    public int getCombat() {
        return combat;
    }

    public void setCombat(int combat) {
        this.combat = combat;
    }

    public void changeCombat(int value) {
        combat += value;
    }

    public void reset() {
        trade = 0;
        combat = 0;
    }

}
