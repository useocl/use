/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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

// $Id$

package org.tzi.use.gui.views.diagrams.behavior.shared;

import com.google.common.eventbus.Subscribe;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.events.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Carsten Schlobohm
 */
public class VisibleDataManager {
    private VisibleData data;

    private MSystem mSystem;

    public static VisibleDataManager createVisibleDataManager(MSystem system) {
        VisibleDataManager manager = new VisibleDataManager(system);
        system.getEventBus().register(manager);
        return manager;
    }

    private VisibleDataManager(MSystem system) {
        mSystem = system;
        data = new VisibleData();
        List<Class<? extends Event>> list = new ArrayList<>();
        list.add(ObjectCreatedEvent.class);
        list.add(ObjectDestroyedEvent.class);
        list.add(LinkInsertedEvent.class);
        list.add(LinkDeletedEvent.class);
        list.add(AttributeAssignedEvent.class);

        data.initData(mSystem.getAllEvents(),
                list,
                mSystem.state().allObjects(),
                mSystem.state().allLinks());
    }

    public VisibleData getData() {
        return data;
    }

    public interface VisibleDataObserver {
        void onObservableChanged();
        void onStatement(Event event);
        void onEventExecuted(StatementExecutedEvent event);
    }

    private final Set<VisibleDataObserver> mObservers = Collections.newSetFromMap(
            new ConcurrentHashMap<VisibleDataObserver, Boolean>(0));

    /**
     * Adds a new observer
     */
    public void registerObserver(VisibleDataObserver observer) {
        if (observer != null) {
            mObservers.add(observer);
        }
    }

    /**
     * Removes a registered observer
     */
    public void unregisterObserver(VisibleDataObserver observer) {
        if (observer != null) {
            mObservers.remove(observer);
        }
    }

    /**
     * Notifies registered observers about changes
     */
    public void notifyObservers(VisibleDataObserver sender) {
        for (VisibleDataObserver observer : mObservers) {
            // observer dont want their own message
            if (!observer.equals(sender)) {
                observer.onObservableChanged();
            }
        }
    }

    @Subscribe
    public void onStateChanged(Event event) {
        data.update(mSystem.getAllEvents(), mSystem.state().allObjects(), mSystem.state().allLinks());
        for (VisibleDataObserver observer : mObservers) {
            observer.onStatement(event);
        }
    }

    @Subscribe
    public void onStatementExecuted(StatementExecutedEvent event) {
        data.update(mSystem.getAllEvents(), mSystem.state().allObjects(), mSystem.state().allLinks());
        for (VisibleDataObserver observer : mObservers) {
            observer.onEventExecuted(event);
        }
    }

    public void secureTermination() {
        if (mObservers.isEmpty()) {
            mSystem.getEventBus().unregister(this);
        }
    }
}
