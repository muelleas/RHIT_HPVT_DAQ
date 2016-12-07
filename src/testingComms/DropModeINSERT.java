package testingComms;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.TransferHandler;

public class DropModeINSERT {
  public static void main(String[] args) {
    JPanel north = new JPanel();
    north.add(new JLabel("Drag from here:"));
    JTextField field = new JTextField(10);
    field.setDragEnabled(true); 
    north.add(field);

    final DefaultListModel listModel = new DefaultListModel();
    listModel.addElement("first");
    listModel.addElement("second");
    final JList list = new JList(listModel);
    list.setDragEnabled(true);

    list.setTransferHandler(new TransferHandler() {
      public boolean canImport(TransferHandler.TransferSupport support) {
        if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
          return false;
        }
        JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
        if (dl.getIndex() == -1) {
          return false;
        } else {
          return true;
        }
      }

      public boolean importData(TransferHandler.TransferSupport support) {
        if (!canImport(support)) {
          return false;
        }

        Transferable transferable = support.getTransferable();
        String data;
        try {
          data = (String) transferable.getTransferData(DataFlavor.stringFlavor);
        } catch (Exception e) {
          return false;
        }

        JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
        int index = dl.getIndex();
        if (dl.isInsert()) {
          listModel.add(index, data);
        } else {
          listModel.set(index, data);
        }

        // Scroll to display the element that was dropped
        Rectangle r = list.getCellBounds(index, index);
        list.scrollRectToVisible(r);
        return true;
      }
    });
    JScrollPane center = new JScrollPane();
    center.setViewportView(list);

    list.setDropMode(DropMode.INSERT);
    JPanel cp = new JPanel();
    cp.setLayout(new BorderLayout());
    cp.add(north, BorderLayout.NORTH);
    cp.add(center, BorderLayout.CENTER);
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(cp);
    frame.pack();
    frame.setVisible(true);
  }
}