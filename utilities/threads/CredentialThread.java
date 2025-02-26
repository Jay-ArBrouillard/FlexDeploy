package threads;

import requests.CredentialAPI;

import pojo.*;

import flexagon.ff.common.core.exceptions.FlexCheckedException;
 
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.logging.*;
import java.util.*;

/**
 * Thread to Create/Update Credentials
 */
public class CredentialThread extends Thread
{
  private final String CLZ_NAM = CredentialThread.class.getName();
  private final Logger LOGGER = Logger.getGlobal();

  // in
  private CredentialAPI credAPI;
  private String localCredStoreId;
  private String localCredStoreInputDefId;
  private Map<String, String> credentialNameToValue;

  // out
  public Exception exception;
  public Map<String, String> credentialNameToId = new HashMap<>();

  public CredentialThread(CredentialAPI credAPI, String localCredStoreId, String localCredStoreInputDefId, Map<String, String> credentialNameToValue)
  {
    this.credAPI = credAPI;
    this.localCredStoreId = localCredStoreId;
    this.localCredStoreInputDefId = localCredStoreInputDefId;
    this.credentialNameToValue = credentialNameToValue;
  }

  public void run()
  {
    try
    {
      if (credentialNameToValue.size() == 0)
      {
        LOGGER.info("No credentials found to create/update");
      }
      else 
      {
        LOGGER.info(credentialNameToValue.size() + " to create/update...");
        LOGGER.fine("credentialNameToValue map: " + credentialNameToValue);
      }

      int index = 1;
      int total = credentialNameToValue.size();
      for (String credentialName: credentialNameToValue.keySet())
      {
        JSONArray searchResult = credAPI.findCredentialByName(credentialName);
        JSONObject credentialObject = validateCredentialArray(credentialName, searchResult);
        String credentialValue = credentialNameToValue.get(credentialName);
        String credentialId;

        LOGGER.info("Creating/updating credential " + credentialName + " " + (index++) + " of " + total);
        if (credentialObject == null)
        {
          // create
          JSONObject postCredentialRequestBody = new JSONObject();
          postCredentialRequestBody.put("credentialName", credentialName);
          postCredentialRequestBody.put("isActive", true); // defaulting
          postCredentialRequestBody.put("credentialScope", CredentialScopeEnum.PROPERTY); // defaulting to PROPERTY ("ENVINST" same difference)
          postCredentialRequestBody.put("credentialStoreId", localCredStoreId);

          JSONArray inputs = new JSONArray();
          JSONObject input = new JSONObject();
          input.put("inputValue", credentialValue);
          input.put("credentialStoreInputDefId", localCredStoreInputDefId);
          inputs.put(input);
          postCredentialRequestBody.put("credentialInputs", inputs);

          credentialId = credAPI.createCredential(postCredentialRequestBody.toString()).get("credentialId").toString();
        }
        else
        {
          // update - override inputValue only
          credentialId = credentialObject.get("credentialId").toString();
          credentialObject.getJSONArray("credentialInputs").getJSONObject(0).put("inputValue", credentialValue);
          credAPI.patchCredentialById(credentialId, credentialObject.toString());
        }

        credentialNameToId.put(credentialName, credentialId);
      }

      LOGGER.info("credentialNameToId map: " + credentialNameToId);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      exception = ex;
    }

    LOGGER.info(CLZ_NAM + " completed successfully");
  }

  /**
   * Validates pJsonArray contains zero or more than one JSONObject(s) and return the JSONObject or null
   * pJsonArray - Array of JSONObject containing Credentials
   */
  private JSONObject validateCredentialArray(String pCredentialName, JSONArray pJsonArray)
    throws FlexCheckedException
  {
    final String methodName = "validateCredentialArray";
    LOGGER.entering(CLZ_NAM, methodName, new Object[]{pCredentialName, pJsonArray});

    if (pJsonArray.length() > 1)
    {
      throw new FlexCheckedException("More than one credential found with name " + pCredentialName + ". Credential Name must be unique.");
    }

    if (pJsonArray.length() == 0)
    {
      LOGGER.exiting(CLZ_NAM, methodName);
      return null;
    }

    JSONObject wfObject = pJsonArray.getJSONObject(0);
    LOGGER.exiting(CLZ_NAM, methodName, wfObject);
    return wfObject;
  }
}