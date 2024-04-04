import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class CanvasWindow extends JFrame {

    int lastX, lastY;
    final int default_stroke_width = 3;
    final int eraser_stroke_width = 40;
    int stroke_width = default_stroke_width;
    Canvas canvas;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JButton saveButton = new JButton();

    JFileChooser saveCanvasState = new JFileChooser();

    File file = null;

    Color selected_color = Color.BLACK;
    Color[] colors = {
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.DARK_GRAY,
            Color.GRAY,
            Color.GREEN,
            Color.LIGHT_GRAY,
            Color.MAGENTA,
            Color.ORANGE,
            Color.PINK,
            Color.RED,
            Color.YELLOW,
            Color.WHITE
    };

    int scaledWidth = (int)(screenSize.getWidth()*0.8);
    int scaledWidthCopy = scaledWidth;
    int scaledHeight = (int)(screenSize.getHeight()*0.8);

    public CanvasWindow(int sleepTime) throws InterruptedException {
        super("paintTool 1");

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(scaledWidth, scaledHeight));
        //canvas = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Color bcolor = getBackground();
        System.out.println(this.getBackground());
        Graphics2D g = (Graphics2D) canvas.getGraphics();
        this.add(canvas);

        canvas.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                drawOval(e);
            }
            @Override
            public void mousePressed(MouseEvent e){
                lastX = e.getX();
                lastY = e.getY();
            }
        });
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                drawOval(e);
            }
        });



        add(canvas);
        setSize(scaledWidth, scaledHeight);
        setVisible(true);
        setLayout(null);
        System.out.println(getSize());
        setLocationRelativeTo(null);
        aestheticMenuHooverAnimation(100,sleepTime);
        aestheticColorTransformationAnimation(Color.WHITE.getBlue());
        //redColorChooserButton.setBounds(getWidth()-200, getHeight()-10, 100, 100);
        createButtons();

        //System.out.println("current colour" + canvas.getBackground());
    }

    private void drawOval(MouseEvent e) {
        //Graphics g = canvas.getGraphics();
        Graphics2D g = (Graphics2D) canvas.getGraphics();
        g.setColor(selected_color);
        int x = e.getX();
        int y = e.getY();
        //g.fillOval(e.getX(), e.getY(), 5, 5);
        g.setStroke(new BasicStroke(stroke_width));
        g.drawLine(x, y, lastX, lastY);
        lastX = x;
        lastY = y;

    }

    private void aestheticMenuHooverAnimation(int targetMax, int sleepValue) throws InterruptedException {
        int iterator = -1;
        int targetWidth = targetMax + scaledWidth;
        System.out.println("width: " + getWidth());
        System.out.println("targetWidth: " + targetWidth);
        while(getWidth() <= targetWidth){
            iterator++;
            System.out.println("width at iteration: " + iterator + " is equal to: " + getWidth());
            scaledWidth = getWidth();
            scaledWidth += 2.5;
            setSize(scaledWidth, getHeight());
            sleep(sleepValue);
        }

    }

    private void aestheticMenuHoverAnimationGPT(int targetMax) {
        final int targetWidth = targetMax + scaledWidth;
        System.out.println("width: " + getWidth());
        System.out.println("targetWidth: " + targetWidth);

        // Create a new Timer that ticks every 35 milliseconds
        Timer timer = new Timer(35, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Increment the width slightly towards the targetWidth
                if (getWidth() < targetWidth) {
                    scaledWidth += 2.5; // Adjust this value as needed for smoothness
                    setSize((int) scaledWidth, getHeight());
                    System.out.println("width at iteration: " + getWidth());
                } else {
                    // Stop the timer once the target width is reached
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        timer.start(); // Start the animation
    }


    private void aestheticColorTransformationAnimation(int targetValue) {
        // Initial color values
        final int[] actualColorValueBlue = {getBackground().getBlue()};
        final int[] actualColorValueRed = {getBackground().getRed()};
        final int[] actualColorValueGreen = {getBackground().getGreen()};

        // Calculate differences
        final int diffB = (int) Math.ceil((double) (targetValue - actualColorValueBlue[0]) / 21);
        final int diffG = (int) Math.ceil((double) (targetValue - actualColorValueGreen[0]) / 21);
        final int diffR = (int) Math.ceil((double) (targetValue - actualColorValueRed[0]) / 21);

        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(canvas.getBackground().getBlue() == targetValue &&
                        canvas.getBackground().getRed() == targetValue &&
                        canvas.getBackground().getGreen() == targetValue)) {

                    // Update values within array to bypass final requirement
                    actualColorValueBlue[0] += diffB;
                    actualColorValueRed[0] += diffR;
                    actualColorValueGreen[0] += diffG;

                    // Clamp values
                    actualColorValueBlue[0] = Math.min(actualColorValueBlue[0], targetValue);
                    actualColorValueRed[0] = Math.min(actualColorValueRed[0], targetValue);
                    actualColorValueGreen[0] = Math.min(actualColorValueGreen[0], targetValue);

                    canvas.setBackground(new Color(actualColorValueRed[0], actualColorValueGreen[0], actualColorValueBlue[0]));
                    canvas.repaint();
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        timer.setInitialDelay(0);
        timer.start();
    }

    private void createButtons() {
        double height2 = scaledHeight - 50;
        height2 /= colors.length + 1;
        int height = (int) height2;
        int tmp = 0;
        for (int i = 0; i < colors.length; i++) {
            JButton button = new JButton();
            add(button);
            button.setBounds(scaledWidthCopy, i * height, scaledWidth - scaledWidthCopy, height);
            tmp = i;
            System.out.println(colors[i]);
            button.setBackground(colors[i]);
            button.setForeground(colors[i]);
            button.setOpaque(true);
            button.setBorderPainted(false);
            int finalI = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selected_color = colors[finalI];
                    stroke_width = (finalI == colors.length - 1 ? eraser_stroke_width : default_stroke_width);
                }
            });
        }

        saveButton.setBounds(scaledWidthCopy, (tmp + 1) * height, scaledWidth - scaledWidthCopy, scaledHeight - ((tmp + 1) * height));
        saveButton.setVisible(true);
        saveButton.setText("SAVE");
        this.add(saveButton);
        JFrame jframe = this;
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = saveCanvasState.showSaveDialog(jframe);
                if (result == JFileChooser.APPROVE_OPTION) {
                    System.out.println("aaa");
                    file = saveCanvasState.getSelectedFile();
                    System.out.println(file.getAbsolutePath());
                    fileSave(file);
                }
            }
        });



    }

    //TODO apache2PDF canvas converter
    private void fileSave(File f){
        try {
            BufferedImage bufferedImage = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();
            canvas.printAll(g);
            g.dispose();

            ImageIO.write(bufferedImage, "PNG", new File("bomoge.png"));

            PDDocument document = new PDDocument();
            PDPage page = new PDPage(new PDRectangle(canvas.getWidth(),canvas.getHeight()));
            System.out.println(page);
            document.addPage(page);
            /*
            PDImageXObject image = LosslessFactory.createFromImage(document, bufferedImage);

            PDPageContentStream pdpcs = new PDPageContentStream(document, page);
            pdpcs.drawImage(image,0,0);
            */
            /*
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.drawImage(org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject.createFromByteArray(document, toByteArray(bufferedImage), "image"), 0, 0);
            }
            */



            document.save(f);

            document.close();

        }
        catch (Exception e){
            System.out.println("----------------------------------------");
        }
    }

    private byte[] toByteArray(BufferedImage bufferedImage) {
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        try {
            javax.imageio.ImageIO.write(bufferedImage, "png", baos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return baos.toByteArray();
    }
}