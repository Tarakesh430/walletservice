package com.crypto.wallet.kafka.postprocess;

public interface BasePostProcess<T>  {
    void postEventProcess(T event);

}
