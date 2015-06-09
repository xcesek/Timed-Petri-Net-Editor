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
package org.pneditor.editor.commands;

import org.pneditor.petrinet.Transition;
import org.pneditor.util.Command;

/**
 * Set label to clicked element
 *
 * @author Martin Riesz <riesz.martin at gmail.com>
 */
public class SetDelayCommand implements Command {

    private Transition transition;
    private Integer newEarliestFiringTime;
    private Integer newLatestFiringTime;
    private Integer oldEarliestFiringTime;
    private Integer oldLatestFiringTime;

    public SetDelayCommand(Transition transition, Integer newEarliestFiringTime, Integer newLatestFiringTime) {
        this.transition = transition;
        this.newEarliestFiringTime = newEarliestFiringTime;
        this.newLatestFiringTime = newLatestFiringTime;
    }

    public void execute() {
        this.oldEarliestFiringTime = transition.getEarliestFiringTime();
        this.oldLatestFiringTime = transition.getLatestFiringTime();
        transition.setEarliestFiringTime(newEarliestFiringTime);
        transition.setLatestFiringTime(newLatestFiringTime);
    }

    public void undo() {
        transition.setEarliestFiringTime(oldEarliestFiringTime);
        transition.setLatestFiringTime(oldLatestFiringTime);
    }

    public void redo() {
        execute();
    }

    @Override
    public String toString() {
        return "Set earliest firing time to " + newEarliestFiringTime.toString() + " and latest firing time to " + newLatestFiringTime.toString();
    }

}
