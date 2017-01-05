/*
 * Copyright (c) 2005, romain guy (romain.guy@jext.org) and craig wickesser (craig@codecraig.com)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <ORGANIZATION> nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package net.java.swingfx.waitwithstyle.demo;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import net.java.swingfx.waitwithstyle.CancelableAdaptee;
import net.java.swingfx.waitwithstyle.CancelableProgressPanel;
import net.java.swingfx.waitwithstyle.PerformanceCancelableProgressPanel;

/**
 * A simple class with a main method to try out the different rubber band's.
 * The idea is to make the rubber bands flexible and easy to use.
 * Currently, in order for it to work you need to do three things:
 * 
 * 1) Create a JComponent which implements the RubberBandCanvas interface
 *
 * 2) Override the canvas' paintComponent(Graphics g) method so that it calls back
 *    to RubberBand.draw(Graphics g)
 *    
 * 3) Create a {@link net.java.swingfx.rubberband.rubberbands.RubberBand}
 * 
 * @author rwickesser
 * $Revision: 1.1 $
 */
public class Main {
    private static final Dimension SIZE = new Dimension(600, 600);

    public static void main(String[] args) {
        MyPanel panel = new MyPanel();

        // Setup demo frame
        JFrame f = new JFrame("Cancelable Progress Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(SIZE);
        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (scrSize.width / 2) - (SIZE.width / 2);
        int y = (scrSize.height / 2) - (SIZE.height / 2);
        f.setLocation(x, y);
        // add canvas to content pane
        f.getContentPane().add(panel);
        //Enables enter in text fields to be like a "Go"
        f.getRootPane().setDefaultButton(panel.getGoButton());
        f.setVisible(true);
    }

    /**
     * A demonstration on how to create a {@link net.java.swingfx.waitwithstyle.CancelableProgressPanel}.
     *
     * @author Michael Bushe
     * $Revision: 1.1 $
     */
    private static class MyPanel extends JPanel implements ActionListener {
        private JCheckBox useFastGraphicsCheckBox;
        private JTextField urlTextField;
        private JButton goButton;
        private JEditorPane htmlPane;
        private JTextField delayTextField;


        public MyPanel() {
            super(new BorderLayout());
            useFastGraphicsCheckBox = new JCheckBox("Best Performance");
            useFastGraphicsCheckBox.setToolTipText("When selected the PerformanceInfiniteProgressPanel is used instead of InfiniteProgressPanel.  It's not perfect yet - mousing over cancel draws two cancel buttons.");
            urlTextField = new JTextField("http://www.google.com/              ");
            goButton = new JButton("Go");
            goButton.addActionListener(this);

            htmlPane = new JEditorPane();
            htmlPane.setEditable(false);
            delayTextField = new JTextField("10000");
            delayTextField.setToolTipText("Set how long a delay is made before the page is loaded.  (Note: Unfortunately, actual page loading is on the EDT)");
            //Integers only, please.
            ((AbstractDocument)delayTextField.getDocument()).setDocumentFilter(new DocumentFilter() {
                public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                    if (string != null && string.length() > 0) {
                        try {
                            Integer.parseInt(string);
                        } catch (NumberFormatException ex) {
                            return;
                        }
                    }
                    super.insertString(fb, offset, string, attr);
                }
            });

            JPanel topPanel = new JPanel();
            topPanel.add(goButton);
            topPanel.add(urlTextField);
            JPanel bottomPanel = new JPanel();
            bottomPanel.add(useFastGraphicsCheckBox);
            bottomPanel.add(new JLabel("Total Delay:"));
            bottomPanel.add(delayTextField);
            add(topPanel, BorderLayout.NORTH);
            add(new JScrollPane(htmlPane), BorderLayout.CENTER);
            add(bottomPanel, BorderLayout.SOUTH);
        }

        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            String sym = urlTextField.getText();

            //Set up the veil
            CancelableAdaptee veil = null;
            if (useFastGraphicsCheckBox.isSelected()) {
                veil = new PerformanceCancelableProgressPanel();
                //THIS DOESN'T WORK YET!
                veil.setText("Starting Lookup of " + sym);
            } else {
                veil = new CancelableProgressPanel("Starting Lookup of " + urlTextField.getText());
            }
            /*
                Set up the runnable, your code may want something like...
                if (comp instanceof JComponent) {
                    rootPane = ((JComponent)comp).getRootPane();
                } else if (comp instanceof RootPaneContainer) {
                    rootPane = ((RootPaneContainer)comp).getRootPane();
                } else {
                   //probably throw an exception, or get the app's main window.
                }
            */
            JRootPane rootPane = getRootPane();
            //When using the glass pane approach, restore the old one (jgoodies or something elst may be using it)
            Component oldGlassPane = rootPane.getGlassPane();
            LookupWorkerRunnable runnable = new LookupWorkerRunnable(sym, veil, rootPane, oldGlassPane);
            rootPane.setGlassPane(veil.getComponent());
            rootPane.revalidate();
            final CancelableAdaptee veil1 = veil;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    veil1.start();
                }
            });
            veil.setText("Waking up the page loader...");
            Thread backgroundWorker = new Thread(runnable);
            backgroundWorker.start();
        }

        public JButton getGoButton() {
            return goButton;
        }

        class LookupWorkerRunnable implements Runnable, ActionListener {
            private String request;
            private CancelableAdaptee veil;
            private JRootPane rootPane;
            private Component oldGlassPane;
            private boolean canceled;
            private boolean beyondCancelation;

            LookupWorkerRunnable(String request, CancelableAdaptee veil, JRootPane rootPane, Component oldGlassPane) {
                this.request = request;
                this.veil = veil;
                this.rootPane = rootPane;
                this.oldGlassPane = oldGlassPane;
                veil.addCancelListener(this);
            }

            public void run() {
                try {
                    Thread.sleep(Integer.parseInt(delayTextField.getText())/4);
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            veil.setText("Yawn! Are you sure you want to load " + request.trim() + "?");
                        }
                    });
                    Thread.sleep(Integer.parseInt(delayTextField.getText()) / 4);
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            veil.setText("Alright, alright, I'll do it in a bit!");
                        }
                    });
                    Thread.sleep(Integer.parseInt(delayTextField.getText()) / 4);
                    synchronized(this) {
                        if (!canceled) {
                            //A real implementation would read the request into an HTMLDocument
                            //checking for cancel periodically.  This is just a demo, so we'll
                            //do it cheap and easy, the downside you can't cancel once the
                            //lookup is in progress.
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    try {
                                        tearDown();
                                        htmlPane.setPage(request);
                                    } catch (IOException e) {
                                        showException(e);
                                    }
                                }
                            });
                            beyondCancelation = true;
                        }
                    }
                } catch (Throwable t) {
                    tearDown();
                    showException(t);
                }
            }

            private void showException(Throwable t) {
                JOptionPane.showMessageDialog(rootPane, "Exception:"+t);
            }

            private void tearDown() {
                veil.stop();
                rootPane.setGlassPane(oldGlassPane);
            }

            public void actionPerformed(ActionEvent e) {
                synchronized (this) {
                    if (!beyondCancelation) {
                        canceled = true;
                    } else {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                JOptionPane.showMessageDialog(rootPane, "Sorry, you were just a bit late in cancelling the page, fetching the page already.");
                            }
                        });
                    }
                }
                tearDown();
            }
        }
    }

}