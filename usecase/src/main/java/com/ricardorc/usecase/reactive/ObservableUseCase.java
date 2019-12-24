package com.ricardorc.usecase.reactive;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public abstract class ObservableUseCase<T, Params> {
    private CompositeDisposable compositeDisposable;
    private final Scheduler executorThread;
    private final Scheduler uiThread;

    public ObservableUseCase(Scheduler executorThread, Scheduler uiThread) {
        this.compositeDisposable = new CompositeDisposable();
        this.executorThread = executorThread;
        this.uiThread = uiThread;
    }

    protected abstract Observable<T> buildUseCase(Params params);

    public void execute(DisposableObserver<T> disposableObserver, Params params) {
        Observable<T> observable = buildUseCase(params)
                .subscribeOn(this.executorThread)
                .observeOn(this.uiThread);
        compositeDisposable.add(observable.subscribeWith(disposableObserver));
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
