package model.minigame.vasebreaker;

/**
 * Represents a vase in vasebreaker minigame
 */
class Vase {
    private String type; // normal, plant, gargantuar
    private Object content;
    private boolean isBroken;

    Vase(String type, Object content) {
        this.type = type;
        this.content = content;
        this.isBroken = false;
    }

    public String getType() {
        return type;
    }

    public Object getContent() {
        return content;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void breakOpen() {
        isBroken = true;
    }
}
