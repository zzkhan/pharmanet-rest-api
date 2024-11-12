package com.pharma.interceptor;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.ForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingInterceptor implements ClientInterceptor {
  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
          MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {

    return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
            next.newCall(method, callOptions)) {

      @Override
      public void start(Listener<RespT> responseListener, Metadata headers) {
        log.info("Calling gRPC method: " + method.getFullMethodName());
        log.info("Headers: " + headers);

        super.start(new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
          @Override
          public void onMessage(RespT message) {
            log.info("Received response: " + message);
            super.onMessage(message);
          }

          @Override
          public void onClose(Status status, Metadata trailers) {
            log.info("Call closed with status: " + status);
            super.onClose(status, trailers);
          }
        }, headers);
      }

      @Override
      public void sendMessage(ReqT message) {
        log.info("Sending request: " + message);
        super.sendMessage(message);
      }
    };
  }
}

