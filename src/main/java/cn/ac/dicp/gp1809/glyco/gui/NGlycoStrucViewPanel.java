/*
 ******************************************************************************
 * File: GlycoStrucViewPanel.java * * * Created on 2012-5-17
 *
 * Copyright (c) 2010 Kai Cheng cksakuraever@msn.com
 *
 * All right reserved. Use is subject to license terms.
 *
 *******************************************************************************
 */
package cn.ac.dicp.gp1809.glyco.gui;

import cn.ac.dicp.gp1809.glyco.Iden.*;
import cn.ac.dicp.gp1809.glyco.drawjf.GlycoSpecMatchDataset;
import cn.ac.dicp.gp1809.glyco.structure.NGlycoSSM;
import cn.ac.dicp.gp1809.util.beans.gui.SelectablePagedTable;
import cn.ac.dicp.gp1809.util.gui.MyJFileChooser;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

//VS4E -- DO NOT REMOVE THIS LINE!
public class NGlycoStrucViewPanel extends JPanel implements ActionListener, ListSelectionListener
{

    private static final long serialVersionUID = 1L;

    private GlycoIdenPagedRowGetter getter;
    private SelectablePagedTable selectablePagedTable;
    private JLabel jLabelTitle;
    private JButton jButtonOutput;
    private JComboBox<Object> jComboBox0;
    private JButton jButtonClose;
    private JPanel jPanelOutput;
    private NGlycoStrucPicPanel glycoPicPanel;
    private NGlycoPeakMatchPanel glycoMatchedPeakPanel;
    private GlycoIdenObject obj;

    private File parent;
    private MyJFileChooser xlsChooser;
    private MyJFileChooser pdfChooser;
    private JProgressBar bar;

    public NGlycoStrucViewPanel()
    {
        initComponents();
    }

    public NGlycoStrucViewPanel(GlycoIdenPagedRowGetter getter)
    {
        this.getter = getter;
        this.parent = getter.getParentFile();
        initComponents();
    }

    private void initComponents()
    {
        setLayout(new GroupLayout());
        if (getter != null) {

            add(addJLabelTitle(), new Constraints(new Bilateral(0, 0, 280), new Leading(0, 20, 6, 6)));
            add(getSelectablePagedTable(), new Constraints(new Bilateral(0, 0, 280), new Leading(20, 400, 6, 6)));
            add(getGlycoPicPanel(), new Constraints(new Leading(0, 560, 6, 6), new Leading(418, 350, 6, 6)));
            add(getGlycoMatchedPeakPanel(), new Constraints(new Leading(565, 535, 12, 12), new Leading(418, 350, 6, 6)));
        }
        add(getOutputPanel(), new Constraints(new Leading(1137, 200, 6, 6), new Leading(435, 200, 10, 10)));
        add(getJProgressBar(), new Constraints(new Leading(1137, 200, 6, 6), new Leading(660, 10, 10)));
        setSize(1200, 800);
    }

    private JProgressBar getJProgressBar()
    {
        if (bar == null) {
            bar = new JProgressBar();
        }
        return bar;
    }

    private JLabel addJLabelTitle()
    {
        if (jLabelTitle == null) {
            jLabelTitle = new JLabel("    " + getter.getFileName());
            Font myFont = new Font("Serif", Font.BOLD, 12);
            jLabelTitle.setFont(myFont);
        }
        return jLabelTitle;
    }

    private SelectablePagedTable getSelectablePagedTable()
    {
        if (selectablePagedTable == null) {
            selectablePagedTable = new SelectablePagedTable(getter);
            selectablePagedTable.setBorder(BorderFactory.createCompoundBorder(null, null));
            selectablePagedTable.setMinimumSize(new Dimension(300, 200));
            selectablePagedTable.setPreferredSize(new Dimension(300, 200));
            selectablePagedTable.addListSelectionListener(this);
        }
        return selectablePagedTable;
    }

    private NGlycoStrucPicPanel getGlycoPicPanel()
    {
        if (glycoPicPanel == null) {
            glycoPicPanel = new NGlycoStrucPicPanel();
            glycoPicPanel.setBorder(BorderFactory.createTitledBorder(null, null, TitledBorder.LEADING, TitledBorder.CENTER, new Font("Dialog",
                    Font.BOLD, 12), new Color(51, 51, 51)));
        }
        return glycoPicPanel;
    }

    private NGlycoPeakMatchPanel getGlycoMatchedPeakPanel()
    {
        if (glycoMatchedPeakPanel == null) {
            glycoMatchedPeakPanel = new NGlycoPeakMatchPanel();
            glycoMatchedPeakPanel.setBorder(BorderFactory.createTitledBorder(null, null, TitledBorder.LEADING, TitledBorder.CENTER, new Font("Dialog",
                    Font.BOLD, 12), new Color(51, 51, 51)));
        }
        return glycoMatchedPeakPanel;
    }

    private JPanel getOutputPanel()
    {
        if (jPanelOutput == null) {
            jPanelOutput = new JPanel();
            jPanelOutput.setBorder(BorderFactory.createTitledBorder(null, null, TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new Font("Dialog",
                    Font.BOLD, 12), new Color(51, 51, 51)));
            jPanelOutput.setLayout(new GroupLayout());
            jPanelOutput.add(getJButtonClose(), new Constraints(new Leading(10, 10, 10), new Leading(120, 10, 10)));
            jPanelOutput.add(getJButtonOutput(), new Constraints(new Leading(10, 12, 12), new Leading(70, 10, 10)));
            jPanelOutput.add(getJComboBox0(), new Constraints(new Leading(10, 150, 6, 6), new Leading(20, 10, 10)));
        }
        return jPanelOutput;
    }

    private JComboBox<Object> getJComboBox0()
    {

        if (jComboBox0 == null) {
            jComboBox0 = new JComboBox<Object>();

            jComboBox0.setModel(new DefaultComboBoxModel(new Object[]{"GlycoQuant result",
                    "Glyco Spectra (pdf)", "Glyco Spectra (html)"}));

            jComboBox0.setDoubleBuffered(false);
            jComboBox0.setBorder(null);
        }
        return jComboBox0;
    }

    private JButton getJButtonOutput()
    {
        if (jButtonOutput == null) {
            jButtonOutput = new JButton();
            jButtonOutput.setText("Output");
            jButtonOutput.addActionListener(this);
        }
        return jButtonOutput;
    }

    public JButton getJButtonClose()
    {
        if (jButtonClose == null) {
            jButtonClose = new JButton();
            jButtonClose.setText("Close");
        }
        return jButtonClose;
    }

    private MyJFileChooser getXlsChooser()
    {
        if (xlsChooser == null) {
            xlsChooser = new MyJFileChooser(parent);
            xlsChooser.setFileFilter(new String[]{"xls"}, "GlycoQuant result");
        }
        return xlsChooser;
    }

    private MyJFileChooser getPdfChooser()
    {
        if (pdfChooser == null) {
            pdfChooser = new MyJFileChooser(parent);
            pdfChooser.setFileFilter(new String[]{"pdf"}, "Glyco Spectra");
        }
        return pdfChooser;
    }

    public void dispose()
    {
        this.getter = null;
        System.gc();
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        // TODO Auto-generated method stub

        Object obj = e.getSource();

        if (obj == this.getJButtonOutput()) {

            int idx = this.getJComboBox0().getSelectedIndex();

            if (idx == 0) {

                int value = this.getXlsChooser().showOpenDialog(this);
                if (value == JFileChooser.APPROVE_OPTION) {

                    File out = this.getXlsChooser().getSelectedFile();
                    XlsOutputThread thread = new XlsOutputThread(out.getAbsolutePath(), this.getJProgressBar());
                    thread.start();

                    return;
                }

            } else if (idx == 1) {

                int value = this.getPdfChooser().showOpenDialog(this);
                if (value == JFileChooser.APPROVE_OPTION) {

                    String file = this.getPdfChooser().getSelectedFile().getAbsolutePath();
                    PdfOutputThread thread = new PdfOutputThread(file, this.getJProgressBar());
                    thread.start();

                    return;
                }

            } else {

                int value = this.getPdfChooser().showOpenDialog(this);
                if (value == JFileChooser.APPROVE_OPTION) {

                    String file = this.getPdfChooser().getSelectedFile().getAbsolutePath();
                    HtmlOutputThread thread = new HtmlOutputThread(file, this.getJProgressBar());
                    thread.start();

                    return;
                }
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e)
    {

        ListSelectionModel model = (ListSelectionModel) e.getSource();
        int first = e.getFirstIndex();
        int last = e.getLastIndex();

        if (model.isSelectedIndex(first)) {

            this.obj = this.getter.getRow(first);

            NGlycoSSM ssm = obj.getGlycoMatch();
            GlycoSpecMatchDataset dataset = new GlycoSpecMatchDataset(ssm.getScanNum());
            dataset.createDataset(ssm);
            this.glycoMatchedPeakPanel.draw(dataset);

            this.glycoPicPanel.draw(ssm.getGlycoTree());

            this.repaint();
            this.updateUI();

        } else if (model.isSelectedIndex(last)) {

            this.obj = this.getter.getRow(last);

            NGlycoSSM ssm = obj.getGlycoMatch();
            GlycoSpecMatchDataset dataset = new GlycoSpecMatchDataset(ssm.getScanNum());
            dataset.createDataset(ssm);
            this.glycoMatchedPeakPanel.draw(dataset);

            this.glycoPicPanel.draw(ssm.getGlycoTree());

            this.repaint();
            this.updateUI();
        }
    }

    private class XlsOutputThread extends Thread
    {

        private String output;
        private JProgressBar jProgressBar0;

        private XlsOutputThread(String output, JProgressBar bar)
        {
            this.output = output;
            this.jProgressBar0 = bar;
        }

        public void run()
        {

            jProgressBar0.setStringPainted(true);
            jProgressBar0.setString("Processing...");
            jProgressBar0.setIndeterminate(true);
            jButtonOutput.setEnabled(false);

            GlycoIdenXlsWriter writer = null;
            try {
                writer = new GlycoIdenXlsWriter(output);
            } catch (RowsExceededException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (WriteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                writer.write(getter.getAllSelectedMatches());
                writer.close();

            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            jProgressBar0.setString("Complete");
            jProgressBar0.setIndeterminate(false);
            jButtonOutput.setEnabled(true);
        }

    }

    private class PdfOutputThread extends Thread
    {

        private String output;
        private JProgressBar jProgressBar0;

        private PdfOutputThread(String output, JProgressBar jProgressBar0)
        {
            this.output = output;
            this.jProgressBar0 = jProgressBar0;
        }

        public void run()
        {

            jProgressBar0.setStringPainted(true);
            jProgressBar0.setString("Processing...");
            jProgressBar0.setIndeterminate(true);
            jButtonOutput.setEnabled(false);

            GlycoIdenSpecPdfWriter writer = null;
            try {
                writer = new GlycoIdenSpecPdfWriter(output);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            NGlycoSSM[] ssms = getter.getAllSelectedMatches();
            for (int i = 0; i < ssms.length; i++) {
                try {
                    writer.write(ssms[i]);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            writer.close();

            jProgressBar0.setString("Complete");
            jProgressBar0.setIndeterminate(false);
            jButtonOutput.setEnabled(true);
        }

    }

    private class HtmlOutputThread extends Thread
    {

        private String output;
        private JProgressBar jProgressBar0;

        private HtmlOutputThread(String output, JProgressBar jProgressBar0)
        {
            this.output = output;
            this.jProgressBar0 = jProgressBar0;
        }

        public void run()
        {

            jProgressBar0.setStringPainted(true);
            jProgressBar0.setString("Processing...");
            jProgressBar0.setIndeterminate(true);
            jButtonOutput.setEnabled(false);

            GlycoIdenSpecHtmlWriter writer = null;
            try {
                writer = new GlycoIdenSpecHtmlWriter(output);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            NGlycoSSM[] ssms = getter.getAllSelectedMatches();
            for (int i = 0; i < ssms.length; i++) {
                try {
                    writer.write(ssms[i]);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            writer.close();

            jProgressBar0.setString("Complete");
            jProgressBar0.setIndeterminate(false);
            jButtonOutput.setEnabled(true);
        }
    }

}
