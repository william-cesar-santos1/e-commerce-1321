package br.ada.ecommerce.application.usecases;

public interface INotifierUseCase<T> {

    void registered(T object);

    void updated(T object);

    void deleted(T object);

}
