package testingComms;

import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class RearrangeOrderOfPanelsTest2 {
  public JComponent makeUI() {
    Box box = Box.createVerticalBox();
    
    		
    Box topBox = Box.createHorizontalBox();
    Box middleBox = Box.createHorizontalBox();
    Box bottomBox = Box.createHorizontalBox();
    
    box.add(topBox);
    box.add(middleBox);
    box.add(bottomBox);
    
    DragMouseAdapter dh = new DragMouseAdapter();
    DragMouseAdapter dh2 = new DragMouseAdapter();
    
    topBox.addMouseListener(dh);
    topBox.addMouseMotionListener(dh);
    middleBox.addMouseListener(dh);
    middleBox.addMouseMotionListener(dh);
    bottomBox.addMouseListener(dh2);
    bottomBox.addMouseMotionListener(dh2);
    
    box.addMouseListener(dh2);
    box.addMouseMotionListener(dh2);

    int idx = 0;
    
    topBox.add(createToolbarButton(idx++, new JLabel("<html>111<br>11<br>11")));
    topBox.add(createToolbarButton(idx++, new JButton("2")));
    middleBox.add(createToolbarButton(idx++, new JLabel("hello world")));
    middleBox.add(createToolbarButton(idx++, new JButton("35")));
    bottomBox.add(createToolbarButton(idx++, new JCheckBox("3")));
    bottomBox.add(createToolbarButton(idx++, new JTextField(14)));
    bottomBox.add(createToolbarButton(idx++, new JButton("76")));
    
 
    JPanel p = new JPanel(new BorderLayout());
    p.add(box, BorderLayout.NORTH);
    return p;
  }

  private static JComponent createToolbarButton(int i, JComponent c) {
    JLabel l = new JLabel(String.format(" %04d ", i));
    l.setOpaque(true);
    l.setBackground(Color.RED);
    JPanel p = new JPanel(new BorderLayout());
    p.setBorder(BorderFactory.createCompoundBorder(
                  BorderFactory.createEmptyBorder(5, 5, 5, 5),
                  BorderFactory.createLineBorder(Color.BLUE, 2)));
    p.add(l, BorderLayout.WEST);
    p.add(c);
    p.setOpaque(false);
    return p;
  }

  public static void main(String... args) {
    EventQueue.invokeLater(new Runnable() {
      @Override public void run() {
        createAndShowGUI();
      }
    });
  }

  public static void createAndShowGUI() {
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    f.getContentPane().add(new RearrangeOrderOfPanelsTest2().makeUI());
    f.setSize(320, 240);
    f.setLocationRelativeTo(null);
    f.setVisible(true);
  }
}

class DragMouseAdapter extends MouseAdapter {
  private static final int xoffset = 16;
  private static final Rectangle R1 = new Rectangle();
  private static final Rectangle R2 = new Rectangle();
  private static Rectangle prevRect;
  private final JWindow window = new JWindow();
  private Component draggingComonent;
  private int index = -1;
  private Component gap;
  private Point startPt;
  private Point dragOffset;
  private final int gestureMotionThreshold = DragSource.getDragThreshold();

  public DragMouseAdapter() {
    super();
    window.setBackground(new Color(0, true));
  }

  @Override public void mousePressed(MouseEvent e) {
    JComponent parent = (JComponent) e.getComponent();
    if (parent.getComponentCount() <= 1) {
      startPt = null;
      return;
    }
    startPt = e.getPoint();
  }

  private void startDragging(JComponent parent, Point pt) {
    //get a dragging panel
    Component c = parent.getComponentAt(pt);
    index = parent.getComponentZOrder(c);
    if (Objects.equals(c, parent) || index < 0) {
      return;
    }
    draggingComonent = c;
    Dimension d = draggingComonent.getSize();

    Point dp = draggingComonent.getLocation();
    dragOffset = new Point(pt.x - dp.x, pt.y - dp.y);

    //make a dummy filler
    gap = Box.createRigidArea(d);
    swapComponentLocation(parent, c, gap, index);

    //make a cursor window
    window.add(draggingComonent);
    window.pack();

    updateWindowLocation(pt, parent);
    window.setVisible(true);
  }

  private void updateWindowLocation(Point pt, JComponent parent) {
    Point p = new Point(pt.x - dragOffset.x, pt.y - dragOffset.y);
    SwingUtilities.convertPointToScreen(p, parent);
    window.setLocation(p);
  }

  private static int getTargetIndex(Rectangle r, Point pt, int i) {
    int ht2 = (int)(.5 + r.height * .5);
    R1.setBounds(r.x, r.y,       r.width, ht2);
    R2.setBounds(r.x, r.y + ht2, r.width, ht2);
    if (R1.contains(pt)) {
      prevRect = R1;
      return i - 1 > 0 ? i : 0;
    } else if (R2.contains(pt)) {
      prevRect = R2;
      return i;
    }
    return -1;
  }
  private static void swapComponentLocation(
      Container parent, Component remove, Component add, int idx) {
    parent.remove(remove);
    parent.add(add, idx);
    parent.revalidate();
    parent.repaint();
  }

  @Override public void mouseDragged(MouseEvent e) {
    Point pt = e.getPoint();
    JComponent parent = (JComponent) e.getComponent();

    //MotionThreshold
    double a = Math.pow(pt.x - startPt.x, 2);
    double b = Math.pow(pt.y - startPt.y, 2);
    if (draggingComonent == null &&
        Math.sqrt(a + b) > gestureMotionThreshold) {
      startDragging(parent, pt);
      return;
    }
    if (!window.isVisible() || draggingComonent == null) {
      return;
    }

    //update the cursor window location
    updateWindowLocation(pt, parent);
    if (prevRect != null && prevRect.contains(pt)) {
      return;
    }

    //change the dummy filler location
    for (int i = 0; i < parent.getComponentCount(); i++) {
      Component c = parent.getComponent(i);
      Rectangle r = c.getBounds();
      if (Objects.equals(c, gap) && r.contains(pt)) {
        return;
      }
      int tgt = getTargetIndex(r, pt, i);
      if (tgt >= 0) {
        swapComponentLocation(parent, gap, gap, tgt);
        return;
      }
    }
    parent.remove(gap);
    parent.revalidate();
  }

  @Override public void mouseReleased(MouseEvent e) {
    startPt = null;
    if (!window.isVisible() || draggingComonent == null) {
      return;
    }
    Point pt = e.getPoint();
    JComponent parent = (JComponent) e.getComponent();

    //close the cursor window
    Component cmp = draggingComonent;
    draggingComonent = null;
    prevRect = null;
    startPt = null;
    dragOffset = null;
    window.setVisible(false);

    //swap the dragging panel and the dummy filler
    for (int i = 0; i < parent.getComponentCount(); i++) {
      Component c = parent.getComponent(i);
      if (Objects.equals(c, gap)) {
        swapComponentLocation(parent, gap, cmp, i);
        return;
      }
      int tgt = getTargetIndex(c.getBounds(), pt, i);
      if (tgt >= 0) {
        swapComponentLocation(parent, gap, cmp, tgt);
        return;
      }
    }
    if (parent.getParent().getBounds().contains(pt)) {
      swapComponentLocation(parent, gap, cmp, parent.getComponentCount());
    } else {
      swapComponentLocation(parent, gap, cmp, index);
    }
  }
}
