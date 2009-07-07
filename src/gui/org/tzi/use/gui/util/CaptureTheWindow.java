/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package org.tzi.use.gui.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileFilter;

/**
 * Thread for capturing the Eval browser window to a image-file
 */
public class CaptureTheWindow extends Thread {
    Component fComponent;

    public CaptureTheWindow(Component comp) {
        fComponent = comp;
    }

    public void run() {

        try {
            Rectangle rec = fComponent.getBounds();
            sleep(100);
            BufferedImage img = new Robot().createScreenCapture(rec);
            // get file dialog
            JFileChooser chooser = new JFileChooser(".");
            chooser.addChoosableFileFilter(new PngFilter());
            chooser.addChoosableFileFilter(new BmpFilter());
            // chooser.addChoosableFileFilter(new EpsFilter());
            chooser.addChoosableFileFilter(new JpgFilter());
            // set "All files" to the default file filter
            FileFilter[] filters = chooser.getChoosableFileFilters();
            chooser.setFileFilter(filters[0]);
            // no gif filter supported by java because of license reasons
            int returnVal = chooser.showSaveDialog(fComponent);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                if (!selectedFile.exists())
                    selectedFile.createNewFile();
                FileFilter ff = chooser.getFileFilter();
                // if the choosen file has an valid extention
                String format = getExtention(selectedFile);
                if (format.equals("jpg") || format.equals("jpeg")
                        || format.equals("png") || format.equals("bmp")) {
                    if (selectedFile.canWrite())
                        ImageIO.write(img, format, selectedFile
                                .getAbsoluteFile());
                    else
                        new ErrorFrame("IO Error on File "
                                + selectedFile.getAbsoluteFile() + " occured");
                } else if (getExtention(selectedFile).equals("eps")) {
                    if (selectedFile.canWrite())
                        ImageIO.write(img, "eps", selectedFile
                                .getAbsoluteFile());
                    else
                        new ErrorFrame("IO Error on File "
                                + selectedFile.getAbsoluteFile() + " occured");

                }// if the file has no valid extention
                else if (ff.getDescription().equals("All Files")) {
                    // if no image format is choosen save img to the default PNG
                    // format
                    if (selectedFile.canWrite())
                        ImageIO.write(img, "png", new File(selectedFile
                                .getAbsolutePath()
                                + ".png"));
                    else
                        new ErrorFrame("IO Error on File "
                                + selectedFile.getAbsoluteFile() + " occured");

                } else {
                    if (selectedFile.canWrite()) {
                        format = ff.getDescription().substring(2);
                        ImageIO.write(img, format, new File(selectedFile
                                .getAbsoluteFile()
                                + "." + format));
                    } else
                        new ErrorFrame("IO Error on File "
                                + selectedFile.getAbsoluteFile() + " occurred");
                }

            }
        } catch (Exception e) {
            new ErrorFrame("IO Error occurred while capturing the screen");
        }
    }

    // the filefilters for the filechooser png,jpg,eps,bmp, no gif
    class PngFilter extends FileFilter {
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String extention = getExtention(f);
            if (extention != null && (extention.equals("png")))
                return true;
            else
                return false;
        }

        public String getDescription() {
            return "*.png";
        }
    }

    class JpgFilter extends FileFilter {
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String extention = getExtention(f);
            if (extention != null
                    && (extention.equals("jpg") || extention.equals("jpeg")))
                return true;
            else
                return false;
        }

        public String getDescription() {
            return "*.jpg";
        }
    }

    class EpsFilter extends FileFilter {
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String extention = getExtention(f);
            if (extention != null && (extention.equals("eps")))
                return true;
            else
                return false;
        }

        public String getDescription() {
            return "*.eps";
        }
    }

    class BmpFilter extends FileFilter {
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String extention = getExtention(f);
            if (extention != null && (extention.equals("bmp")))
                return true;
            else
                return false;
        }

        public String getDescription() {
            return "*.bmp";
        }
    }

    public String getExtention(File f) {
        String ext = "";
        String s = f.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    /**
     * shows an error Frame for the user
     */
    @SuppressWarnings("serial")
	public class ErrorFrame extends JFrame {
        public ErrorFrame(String labelTxt) {
            super("Error message");
            JLabel label = new JLabel(labelTxt);
            label.setHorizontalAlignment(JLabel.CENTER);
            getContentPane().add(label);
            setSize(300, 100);
            setVisible(true);
            adjustTopWidth(label, labelTxt, labelTxt);
        }

        public void adjustTopWidth(JLabel label, String text, String htmlText) {
            // get the Display Size
            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension displaySize = tk.getScreenSize();
            int maxWidth = (int) displaySize.getWidth();

            // Dimension windowSize = getSize();
            int windowSize = getWidth();

            if (windowSize < maxWidth)
                maxWidth = windowSize;
            // calculate the Dimension for the title
            FontMetrics fm = label.getFontMetrics(label.getFont());
            int topWidth = 0;
            int topHeight = fm.getHeight();
            Pattern p = Pattern.compile("\n");
            String s[] = p.split(text);
            for (int i = 0; i < s.length; i++) {
                // if the current string is wider
                if (topWidth < fm.stringWidth(s[i]))
                    if (fm.stringWidth(s[i]) < maxWidth)
                        // string is between width so far and maxWidth
                        topWidth = fm.stringWidth(s[i]);
                    else
                        topWidth = maxWidth;
                // increment the height with 1 line or more if needed
                topHeight += (fm.getHeight() * (1 + (fm.stringWidth(s[i]) / maxWidth)));
            }
            // set the Size of the South Frame
            label.setPreferredSize(new Dimension(topWidth, topHeight));
            // make changes visible
            label.setText(htmlText);
            label.setVisible(false);
            label.setVisible(true);
        }
    }
}
