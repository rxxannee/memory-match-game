public class Tile {
    private String value;
    private boolean matched;
    private boolean showing;

    public Tile(String value) {
        this.value = value;
        matched = false;
        showing = false;
    }

    public String getValue() {
        return value;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public boolean isShowing() {
        return showing;
    }

    public void setShowing(boolean showing) {
        this.showing = showing;
    }
}
