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
package org.pneditor.petrinet;

import org.pneditor.util.ListModel;

/**
 * Represents list of roles associated with the Petri net
 *
 * @author Martin Riesz <riesz.martin at gmail.com>
 */
public class Roles extends ListModel<Role> {

    private int uniqueSuffix = 1;

    @Override
    public void addNew() {
        Role role = new Role();
        role.id = uniqueSuffix;
        role.name = "role" + uniqueSuffix++;
        elements.add(role);
        fireIntervalAdded(this, elements.indexOf(role), elements.indexOf(role));
    }

}
