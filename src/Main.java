public class Main {
    public static void main(String[] args) {
        System.out.println("adco0f projects ^^'" +
                "\nWhy would you use a proper debugging tool, when console is still an option?");
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                new CanvasWindow(35);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
