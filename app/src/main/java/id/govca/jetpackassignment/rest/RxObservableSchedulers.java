package id.govca.jetpackassignment.rest;

import io.reactivex.ObservableTransformer;
import io.reactivex.schedulers.Schedulers;

public interface RxObservableSchedulers {

    RxObservableSchedulers TEST_SCHEDULER = new RxObservableSchedulers() {
        @Override
        public <T> ObservableTransformer<T, T> applySchedulers() {
            return observable -> observable
                    .subscribeOn(Schedulers.trampoline())
                    .observeOn(Schedulers.trampoline());
        }
    };

    <T> ObservableTransformer<T, T> applySchedulers();
}
