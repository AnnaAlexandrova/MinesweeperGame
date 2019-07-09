package game;

class Cell {
    private boolean isMine;
    private boolean isOpen;
    private boolean isFlag;

    Cell() {
        this.isMine = false;
        this.isOpen = false;
        this.isFlag = false;
    }

    boolean isMine() {
        return isMine;
    }

    void setMine(boolean mine) {
        isMine = mine;
    }

    boolean isOpen() {
        return isOpen;
    }

    void setOpen(boolean open) {
        isOpen = open;
    }

    boolean isFlag() {
        return isFlag;
    }

    void setFlag(boolean flag) {
        isFlag = flag;
    }
}
