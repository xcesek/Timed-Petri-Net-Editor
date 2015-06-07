/*
 * Copyright (C) 2008-2010 Martin Riesz <riesz.martin at gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.pneditor.editor.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.pneditor.petrinet.Document;
import org.pneditor.editor.Root;
import org.pneditor.editor.filechooser.FileChooserDialog;
import org.pneditor.editor.filechooser.FileType;
import org.pneditor.editor.filechooser.FileTypeException;
import org.pneditor.util.GraphicsTools;

/**
 *
 * @author Martin Riesz <riesz.martin at gmail.com>
 */
public class ImportAction extends AbstractAction {

    private Root root;
    private List<FileType> fileTypes;

    public ImportAction(Root root, List<FileType> fileTypes) {
        this.root = root;
        this.fileTypes = fileTypes;
        String name = "Import...";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/import.gif"));
        putValue(SHORT_DESCRIPTION, name);
    }

    public void actionPerformed(ActionEvent e) {
        if (!root.isModified() || JOptionPane.showOptionDialog(
                root.getParentFrame(),
                "Any unsaved changes will be lost. Continue?",
                "Import...",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                new String[]{"Import...", "Cancel"},
                "Cancel") == JOptionPane.YES_OPTION) {
            FileChooserDialog chooser = new FileChooserDialog();

            for (FileType fileType : fileTypes) {
                chooser.addChoosableFileFilter(fileType);
            }
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setCurrentDirectory(root.getCurrentDirectory());

            if (chooser.showDialog(root.getParentFrame(), "Import") == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                FileType chosenFileType = (FileType) chooser.getFileFilter();
                try {
                    Document document = chosenFileType.load(file);
                    root.setDocument(document);
                } catch (FileTypeException ex) {
                    JOptionPane.showMessageDialog(root.getParentFrame(), ex.getMessage());
                }
                root.setCurrentFile(null);
                root.setCurrentDirectory(chooser.getCurrentDirectory());
            }
        }
    }
}
