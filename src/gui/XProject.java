package gui;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by Administrator on 2016/6/7.
 */
public class XProject extends JTree {
    JTextArea textArea = new JTextArea(){{
        setFont(new Font("Consolas", Font.PLAIN, 24));
    }};
    HashSet fileHashSet = new HashSet();
    private static DefaultTreeModel defaultTreeModel;
    private DefaultTreeModel thisTreeModel;
    private DefaultMutableTreeNode root;
    boolean check = false;
    public XProject(DefaultMutableTreeNode root) throws IOException {
        super(defaultTreeModel = new DefaultTreeModel(root, true));
        thisTreeModel = defaultTreeModel;
        this.root = root;
        setShowsRootHandles(true);
        addDir(root);
//        addTreeWillExpandListener(new TreeWillExpandListener() {
//            @Override
//            public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
//                TreePath treePath = event.getPath();
//                DefaultMutableTreeNode selectionNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
//                Object obj = selectionNode.getUserObject();
//                Object o = selectionNode;
//                if (!(obj instanceof File)) {
//                    obj = ((DefaultMutableTreeNode) obj).getUserObject();
//                    o = selectionNode.getUserObject();
//                }
//                File file = (File) obj;
//                if (file.isDirectory()) {
//                    File[] files = file.listFiles();
//                    for (File f : files) {
//                        DefaultMutableTreeNode newNode = null;
//                        try {
//                            File file0 = new SFile(f.getCanonicalPath());
//                            if (fileHashSet.add(file0)) {
//                                newNode = new DefaultMutableTreeNode(file0, file0.isDirectory());
//                                thisTreeModel.insertNodeInto(newNode, selectionNode, selectionNode.getChildCount());
//                            }
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    return;
//                }
//                if (!file.exists()) {
//                    thisTreeModel.removeNodeFromParent((DefaultMutableTreeNode) o);
//                    removeFile((File) (selectionNode).getUserObject());
//                }
//            }
//
//            @Override
//            public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
//
//            }
//        });

        addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) getLastSelectedPathComponent();//返回最后选定的节点
                if (selectedNode != null && selectedNode.getUserObject() instanceof File) {

                    setNewFile(((File) selectedNode.getUserObject()));
                }
            }
        });
    }

    private void removeFile(File file) {
        fileHashSet.remove();
    }


    public void setNewFile(File file) {
        if (!file.isDirectory() && file.exists()) {
            textArea.setText("");
            try (Scanner scanner = new Scanner(file)) {
                try {
                    while (true) {
                        textArea.append(scanner.nextLine() + "\n");
                    }
                } catch (NoSuchElementException e) {
//                    e.printStackTrace();
                    //ignore.
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                textArea.setText(e.getMessage());
            }
        }
    }

    public void addDir(DefaultMutableTreeNode top) throws IOException {
        File file = (File) top.getUserObject();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(new SFile(f.getCanonicalPath()), f.isDirectory());
                thisTreeModel.insertNodeInto(node, top, top.getChildCount());
                addDir(node);
            }
        }
    }

    public JTextArea getTextArea() {
        return textArea;
    }
    JPopupMenu popup = new JPopupMenu();
    JMenuItem rename = new JMenuItem("rename");

    {
        rename.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                node = (DefaultMutableTreeNode) getLastSelectedPathComponent();
                if (node != null) {
                    File file = ((File) node.getUserObject());
                    if (file.isDirectory()) {
                        JOptionPane.showMessageDialog(XProject.this, "Directory can not be rename.");
                        return;
                    }
                    String newName = JOptionPane.showInputDialog(XProject.this, "File new name:", "Rename", JOptionPane.NO_OPTION);
                    if (newName != null) {
                        file.renameTo(new SFile(new File(file.getParent(), newName).getAbsolutePath()));
//                        node.setUserObject(file);
                    }
                }
            }
        });
        popup.add(rename);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == e.BUTTON3) {
                    if (getLastSelectedPathComponent() != null) {
                        popup.show(e.getComponent(), e.getX(), e.getY());
                    }

                }
            }
        });
    }

    DefaultMutableTreeNode node = null;

//    public void jTree1_mousePressed(MouseEvent e) {
//        TreePath tp=tree.getPathForLocation(e.getX(),e.getY());
//        if (tp != null) {
//            tree.setSelectionPath(tp);
//            DefaultMutableTreeNode node =  (DefaultMutableTreeNode)tp.getLastPathComponent()
//        }
//        pmn.show(e.getComponent(),e.getX(),e.getY());
//    }
}
