/*
 * Copyright (C) 2012 Jan Pokorsky
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.cas.lib.proarc.common.imports;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Dispatcher controls scheduling of {@link ImportProcess import processes}.
 *
 * For now it runs processes in single thread to preserve memory resources.
 *
 * @author Jan Pokorsky
 */
public final class ImportDispatcher {

    private static final Logger LOG = Logger.getLogger(ImportDispatcher.class.getName());
    private static ImportDispatcher INSTANCE = new ImportDispatcher();

    private ExecutorService pool;
    private final int threadCount;

    public ImportDispatcher() {
        this(1);
    }

    ImportDispatcher(int threadCount) {
        if (threadCount < 1) {
            throw new IllegalArgumentException("threadCount: " + threadCount);
        }
        this.threadCount = threadCount;
    }

    public static ImportDispatcher getDefault() {
        return INSTANCE;
    }

    public static void setDefault(ImportDispatcher dispatcher) {
        INSTANCE = dispatcher;
    }

    public void init() {
        pool = Executors.newFixedThreadPool(threadCount, new ImportDispatcherThreadFactory());
    }

    public void stop() {
        stop(60, TimeUnit.SECONDS);
    }

    public void stop(long timeout, TimeUnit unit) {
        if (pool == null) {
            return ;
        }
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(timeout, unit)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(timeout, unit)) {
                    LOG.severe("ImportDispatcher thread pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    public Future<ImportProcess> addImport(ImportProcess task) {
        return addTask(task);
    }

    <T extends Runnable> Future<T> addTask(T task) {
        checkRunning();
        return pool.submit(new ExceptionHandlingTask(task), task);
    }

    private void checkRunning() {
        if (pool == null) {
            throw new IllegalStateException("needs init");
        }
        if (pool.isShutdown()) {
            throw new IllegalStateException("needs restart");
        }
    }

    private static final class ExceptionHandlingTask implements Runnable {

        private final Runnable delegate;

        public ExceptionHandlingTask(Runnable delegate) {
            this.delegate = delegate;
        }

        @Override
        public void run() {
            try {
                delegate.run();
            } catch (Throwable t) {
                Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), t);
            }
        }

    }

    private static final class ImportDispatcherThreadFactory implements ThreadFactory {

        private final ThreadFactory factory;

        public ImportDispatcherThreadFactory() {
            factory = Executors.defaultThreadFactory();
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = factory.newThread(r);
            String name = ImportDispatcher.class.getSimpleName() + '-' + thread.getName();
            thread.setName(name);
            UncaughtExceptionHandler uncaughtExceptionHandler = thread.getUncaughtExceptionHandler();
            thread.setUncaughtExceptionHandler(new ImportDispatcherExceptionHandler(uncaughtExceptionHandler));
            return thread;
        }

    }

    private static final class ImportDispatcherExceptionHandler implements UncaughtExceptionHandler {

        private final UncaughtExceptionHandler delegate;

        public ImportDispatcherExceptionHandler(UncaughtExceptionHandler delegate) {
            this.delegate = delegate;
        }

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            LOG.log(Level.SEVERE, t.getName(), e);
//            if (delegate != null) {
//                delegate.uncaughtException(t, e);
//            }
        }

    }

}
