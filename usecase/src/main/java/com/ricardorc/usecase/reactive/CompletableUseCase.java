package com.ricardorc.usecase.reactive;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;

public abstract class CompletableUseCase<T, Params> {
    private CompositeDisposable compositeDisposable;
    private final Scheduler executorThread;
    private final Scheduler uiThread;

    public CompletableUseCase(Scheduler executorThread, Scheduler uiThread) {
        this.compositeDisposable = new CompositeDisposable();
        this.executorThread = executorThread;
        this.uiThread = uiThread;
    }

    protected abstract Completable buildUseCase(Params params);

    public void execute(DisposableCompletableObserver disposableCompletableObserver, Params params) {
        Completable completable = buildUseCase(params)
                .subscribeOn(this.executorThread)
                .observeOn(this.uiThread);
        compositeDisposable.add(completable.subscribeWith(disposableCompletableObserver));
    }

    public void addDisposable(Disposable disposable) {
        this.compositeDisposable.add(disposable);
    }

    public void dispose() {
        if (!this.compositeDisposable.isDisposed()) {
            this.compositeDisposable.dispose();
        }

    }
}
