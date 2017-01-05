package net.java.swingfx.waitwithstyle;

import java.awt.event.ActionListener;

/**
 * A progress panel that is cancelable instead of infinite.  The work is done by the CancelableAdaptee.
 * Allows programatic cancelling (maybe useful for timeouts), and adding a listener to the user's cancel
 * so that the action the user is waiting on is stopped or ignored.
 *
 * @author Michael Bushe michael@bushe.com
 */
public class CancelableProgressPanel extends InfiniteProgressPanel {
    public CancelableProgressPanel() {
        super();
        setupAdapter();
    }

    public CancelableProgressPanel(String text) {
        super(text);
        setupAdapter();
    }

    public CancelableProgressPanel(String text, int barsCount) {
        super(text, barsCount);
        setupAdapter();
    }

    public CancelableProgressPanel(String text, int barsCount, float shield) {
        super(text, barsCount, shield);
        setupAdapter();
    }

    public CancelableProgressPanel(String text, int barsCount, float shield, float fps) {
        super(text, barsCount, shield, fps);
        setupAdapter();
    }

    public CancelableProgressPanel(String text, int barsCount, float shield, float fps, int rampDelay) {
        super(text, barsCount, shield, fps, rampDelay);
        setupAdapter();
    }

    public CancelableProgressPanel(CancelableProgessAdapter adapter) {
        super();
        infiniteProgressAdapter = adapter;
    }

    public CancelableProgressPanel(String text, CancelableProgessAdapter adapter) {
        super(text);
        infiniteProgressAdapter = adapter;
    }

    public CancelableProgressPanel(String text, int barsCount, CancelableProgessAdapter adapter) {
        super(text, barsCount);
        infiniteProgressAdapter = adapter;
    }

    public CancelableProgressPanel(String text, int barsCount, float shield, CancelableProgessAdapter adapter) {
        super(text, barsCount, shield);
        infiniteProgressAdapter = adapter;
    }

    public CancelableProgressPanel(String text, int barsCount, float shield, float fps, CancelableProgessAdapter adapter) {
        super(text, barsCount, shield, fps);
        infiniteProgressAdapter = adapter;
    }

    public CancelableProgressPanel(String text, int barsCount, float shield, float fps, int rampDelay, CancelableProgessAdapter adapter) {
        super(text, barsCount, shield, fps, rampDelay);
        infiniteProgressAdapter = adapter;
    }

    /**
     * When not constructed with an adapter, this method is called during construction to create the defaule adapter.
     */
    protected void setupAdapter() {
        infiniteProgressAdapter = new CancelableProgessAdapter(this);
    }

    /**
     * Add a cancel listener to be called back when the user cancels the progress.
     * @param listener some listener that wants to take action on cancel (like stop whatever was being waited for)
     */
    public void addCancelListener(ActionListener listener) {
        ((CancelableProgessAdapter)infiniteProgressAdapter).addCancelListener(listener);
    }

    /**
     * Remove a cancel listener that would be called back when the user cancels the progress.
     * @param listener some listener that wants to take action on cancel (like stop whatever was being waited for)
     */
    public void removeCancelListener(ActionListener listener) {
        ((CancelableProgessAdapter)infiniteProgressAdapter).removeCancelListener(listener);
    }

    /**
     * Programmaticlly click the cancel button.  Can be called from any thread.
     */
    public void doCancel() {
        ((CancelableProgessAdapter)infiniteProgressAdapter).doCancel();
    }
}
