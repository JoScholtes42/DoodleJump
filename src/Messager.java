public class Messager {

    private static Doodle doodle;
    private static MainFrame mainFrame;
    private static DrawPanel drawPanel;


    public static Doodle getDoodle() {
        return doodle;
    }

    public static void setDoodle(Doodle doodle) {
        Messager.doodle = doodle;
    }

    public static MainFrame getMainFrame() {
        return mainFrame;
    }

    public static void setMainFrame(MainFrame mainFrame) {
        Messager.mainFrame = mainFrame;
    }

    public static DrawPanel getDrawPanel() {
        return drawPanel;
    }

    public static void setDrawPanel(DrawPanel drawPanel) {
        Messager.drawPanel = drawPanel;
    }

    public static void gameOver() throws InterruptedException {
        drawPanel.setGameOver(true);
        doodle.gameOver();
        mainFrame.gameOver();

    }
}
