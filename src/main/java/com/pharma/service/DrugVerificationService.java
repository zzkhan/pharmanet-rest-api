package com.pharma.service;

import com.pharma.adapter.DrugChallengeAdapter;
import com.pharma.adapter.DrugCrpAdapter;
import com.pharma.adapter.DrugVerificationOutcomeAdapter;
import com.pharma.rest.model.Challenges;
import com.pharma.rest.model.Crp;
import com.pharma.rest.model.DrugVerificationOutcome;
import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.CommitStatusException;
import org.hyperledger.fabric.client.EndorseException;
import org.hyperledger.fabric.client.Gateway;
import org.hyperledger.fabric.client.GatewayException;
import org.hyperledger.fabric.client.SubmitException;

import java.util.List;

public class DrugVerificationService extends BlockChainService {
  public DrugVerificationService(Gateway gateway) {
    super(gateway);
  }

  public static DrugVerificationService instance(Gateway gateway) {
    return new DrugVerificationService(gateway);
  }

  public List<Challenges> getDrugChallenges(String drugName, String tagId) {
    try {
      byte[] bytes = getContract()
              .evaluateTransaction("drug-verification:getDrugChallenges", drugName, tagId);
      return DrugChallengeAdapter.fromBytes(bytes);
    } catch (GatewayException e) {
      throw new RuntimeException(e);
    }
  }

  public void submitVerificationCrps(String drugName, String tagId, List<Crp> crps) {
    try {
      getContract().newProposal("drug-verification:submitDrugVerificationCrps")
              .addArguments(drugName, tagId)
              .putTransient("verification-crps", DrugCrpAdapter.toBytes(crps))
              .build()
              .endorse()
              .submit();
    } catch (SubmitException e) {
      throw new RuntimeException(e);
    } catch (CommitStatusException e) {
      throw new RuntimeException(e);
    } catch (CommitException e) {
      throw new RuntimeException(e);
    } catch (EndorseException e) {
      throw new RuntimeException(e);
    }
  }

  public void shareAssignedCrps(String drugName, String tagId, String submitter, List<Crp> assignedCrps) {
    try {
      getContract().newProposal("drug-verification:shareAssignedCrps")
              .addArguments(drugName, tagId, submitter)
              .putTransient("assigned-crps", DrugCrpAdapter.toBytes(assignedCrps))
              .build()
              .endorse()
              .submit();
    } catch (SubmitException e) {
      throw new RuntimeException(e);
    } catch (CommitStatusException e) {
      throw new RuntimeException(e);
    } catch (CommitException e) {
      throw new RuntimeException(e);
    } catch (EndorseException e) {
      throw new RuntimeException(e);
    }
  }

  public DrugVerificationOutcome verifyCrps(String drugName, String tagId, List<Crp> crps) {
    try {
      byte[] bytes = getContract().newProposal("drug-verification:verifyDrugCrps")
              .addArguments(drugName, tagId)
              .putTransient("verification-crps", DrugCrpAdapter.toBytes(crps))
              .build()
              .endorse()
              .submit();
      return DrugVerificationOutcomeAdapter.fromBytes(bytes);
    } catch (SubmitException e) {
      throw new RuntimeException(e);
    } catch (CommitStatusException e) {
      throw new RuntimeException(e);
    } catch (CommitException e) {
      throw new RuntimeException(e);
    } catch (EndorseException e) {
      throw new RuntimeException(e);
    }
  }
}
