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

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import org.pneditor.editor.Root;
import org.pneditor.editor.commands.SetDelayCommand;
import org.pneditor.editor.commands.SetLabelCommand;
import org.pneditor.editor.time.TimingPolicyType;
import org.pneditor.petrinet.Node;
import org.pneditor.petrinet.Transition;
import org.pneditor.util.GraphicsTools;

/**
 *
 * @author Martin Riesz <riesz.martin at gmail.com>
 */
public class SetDelayAction extends AbstractAction {

    private Root root;

    public SetDelayAction(Root root) {
        this.root = root;
        String name = "Set delay";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/delay.gif"));
        putValue(SHORT_DESCRIPTION, name);
//		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
//		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("R"));
        setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (root.getClickedElement() != null
                && root.getClickedElement() instanceof Transition) {
            Transition transition = (Transition) root.getClickedElement();

            if (root.getGlobalTimer().getType() == TimingPolicyType.Stochastic) { 
                Integer oldEarliestFiringTime = transition.getEarliestFiringTime();
                Integer oldLatestFiringTime = transition.getLatestFiringTime();
                String earliestFiringTimeValue = "";
                String latestFiringTimeValue = "";
                
                if(oldEarliestFiringTime != null) {
                    earliestFiringTimeValue = oldEarliestFiringTime.toString();
                }
                if(oldLatestFiringTime != null) {
                    latestFiringTimeValue = oldLatestFiringTime.toString();
                }
                
                JTextField earliestFiringTimeField = new JTextField(earliestFiringTimeValue);
                JTextField latestFiringTimeField = new JTextField(latestFiringTimeValue);
                Object[] message = {
                    "New earliest firing time:", earliestFiringTimeField,
                    "New latest firing time:", latestFiringTimeField
                };
                int option = JOptionPane.showConfirmDialog(root.getParentFrame(), message, "Set firing times", JOptionPane.OK_CANCEL_OPTION);
                String newEarliestFiringTimeStr = "";
                String newLatestFiringTimeStr = "";
                if (option == JOptionPane.OK_OPTION) {
                    newEarliestFiringTimeStr = earliestFiringTimeField.getText();
                    newLatestFiringTimeStr = latestFiringTimeField.getText();
                } else {
                    return;
                }

                Integer newEarliestFiringTime;
                Integer newLatestFiringTime;
                try {
                    newEarliestFiringTime = Integer.parseInt(newEarliestFiringTimeStr);
                    newLatestFiringTime = Integer.parseInt(newLatestFiringTimeStr);
                } catch (NumberFormatException ex) {
                    newEarliestFiringTime = 0;
                    newLatestFiringTime = 0;
                }

                if (newEarliestFiringTime != null && !newEarliestFiringTimeStr.equals(transition.getEarliestFiringTime())
                        && newLatestFiringTime != null && !newLatestFiringTimeStr.equals(transition.getLatestFiringTime())
                        && newLatestFiringTime >= newEarliestFiringTime) {
                    root.getUndoManager().executeCommand(new SetDelayCommand(transition, newEarliestFiringTime, newLatestFiringTime));
                } else {
                    //error
                    JOptionPane.showMessageDialog(root.getParentFrame(), "Firing times weren't changed!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                

            } else {
                String newDelayStr = JOptionPane.showInputDialog(root.getParentFrame(), "New delay:", transition.getEarliestFiringTime());

                Integer newDelay;
                try {
                    newDelay = Integer.parseInt(newDelayStr);
                } catch (NumberFormatException ex) {
                    newDelay = 0;
                }

                if (newDelayStr != null && !newDelayStr.equals(transition.getEarliestFiringTime())) {
                    root.getUndoManager().executeCommand(new SetDelayCommand(transition, newDelay, newDelay)); //set earliest and latest firing time to one value
                } else {
                    //error
                    JOptionPane.showMessageDialog(root.getParentFrame(), "Delay wasn't changed!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            }

        }
    }
}
