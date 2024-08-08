package com.pharma.service;

import com.pharma.adapter.DrugShipmentAdapter;
import com.pharma.adapter.PharmaEventAdapter;
import com.pharma.rest.model.DrugVerificationSubmissionEvent;
import com.pharma.rest.model.Shipment;
import io.grpc.Status;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.client.ChaincodeEvent;
import org.hyperledger.fabric.client.CloseableIterator;
import org.hyperledger.fabric.client.Gateway;
import org.hyperledger.fabric.client.GatewayRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class ChaincodeEventListener {

  private final ExecutorService executorService;

  @Autowired
  final ManufacturerFacade manufacturerFacade;

  public ChaincodeEventListener(ManufacturerFacade manufacturerFacade) {
    this.manufacturerFacade = manufacturerFacade;
    this.executorService = Executors.newSingleThreadExecutor();
  }

  @PostConstruct
  public void startListening() {
    var eventIter = manufacturerFacade.gateway().getNetwork(Constants.CHANNEL_NAME).getChaincodeEvents(Constants.CHAINCODE_NAME);
    executorService.execute(() -> readEvents(eventIter));
    log.info("*** Started chaincode event listening...");
  }

  private void readEvents(final CloseableIterator<ChaincodeEvent> eventIter) {
    try {
      eventIter.forEachRemaining(event -> {
        log.info("<-- Chaincode event received: {} - {}", event.getEventName(), new String(event.getPayload()));
        processEvent(event);
      });
    } catch (GatewayRuntimeException e) {
      if (e.getStatus().getCode() != Status.Code.CANCELLED) {
        throw e;
      }
    }
  }

  private void processEvent(ChaincodeEvent event) {
    try {
      if (event.getEventName().equals("DRUG-SHIPMENT-DELIVERED")) {
        Shipment shipment = DrugShipmentAdapter.fromBytes(event.getPayload());
        manufacturerFacade.assignCrps(shipment.getDrugName(), shipment.getLineItems().get(0), shipment.getBuyer());
      } else if (event.getEventName().equals("DRUG-VERIFICATION-CRP-SUBMITTED")) {
        DrugVerificationSubmissionEvent verificationSubmissionEvent = PharmaEventAdapter.fromBytes(event.getPayload(), DrugVerificationSubmissionEvent.class);
        manufacturerFacade.shareAssignedCrps(verificationSubmissionEvent.getDrugName(), verificationSubmissionEvent.getTagId(), verificationSubmissionEvent.getSubmitter());
      } else {
        log.info("NO OP on event {}", event.getEventName());
      }
    } catch (Exception e) {
      log.error("error occurred during event processing", e);
    }
  }
}
