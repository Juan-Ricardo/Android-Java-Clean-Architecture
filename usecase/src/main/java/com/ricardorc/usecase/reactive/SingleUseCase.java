package com.ricardorc.usecase.reactive;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;

public abstract class SingleUseCase<T, Params> {
    private CompositeDisposable compositeDisposable;
    private final Scheduler executorThread;
    private final Scheduler uiThread;

    public SingleUseCase(Scheduler executorThread, Scheduler uiThread) {
        this.compositeDisposable = new CompositeDisposable();
        this.executorThread = executorThread;
        this.uiThread = uiThread;
    }

    protected abstract Single<T> buildUseCase(Params params);

    public void execute(DisposableSingleObserver<T> singleObserver, Params params) {
        Single<T> single = buildUseCase(params)
                .subscribeOn(this.executorThread)
                .observeOn(this.uiThread);
        this.compositeDisposable.add(single.subscribeWith(singleObserver));
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
