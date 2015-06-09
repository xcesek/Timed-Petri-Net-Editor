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
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.pneditor.editor.Root;
import org.pneditor.editor.time.GlobalTimer;
import org.pneditor.editor.time.TimingPolicyType;
import org.pneditor.util.GraphicsTools;

/**
 *
 * @author Martin Riesz <riesz.martin at gmail.com>
 */
public class SetTimingPolicyAction extends AbstractAction {

    private Root root;

    public SetTimingPolicyAction(Root root) {
        this.root = root;
        String name = "Set Timing Policy";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/setTimingPolicy.gif"));
        putValue(SHORT_DESCRIPTION, name);
//		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
//		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("R"));
        setEnabled(true);
    }

    public void actionPerformed(ActionEvent e) {
        GlobalTimer globalTimer = root.getGlobalTimer();
        Object[] timingPolicyNames = TimingPolicyType.values();
        TimingPolicyType answer = (TimingPolicyType) JOptionPane.showInputDialog(
                            root.getParentFrame(),
                            "Select Timing Policy:",
                            "Select Timing Policy:",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            timingPolicyNames,
                            globalTimer.getType());
        if ((answer != null)) {
            globalTimer.setType(answer);
        } 
        
         return;
    }

           
}
