package requests;

import requests.SearchCredentialByName;
import requests.PatchCredentialById;
import requests.GetCredentialStoreByName;
import requests.GetCredentialStoreProviderById;

import flexagon.ff.common.core.exceptions.FlexCheckedException;
import flexagon.ff.common.core.rest.FlexRESTClientResponse;
import flexagon.ff.common.core.utils.FlexJsonUtils;
 
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.logging.*;

public class CredentialAPI
  extends BaseAPI
{
  private static final String CLZ_NAM = CredentialAPI.class.getName();
  private static final Logger LOGGER = Logger.getGlobal();

  public CredentialAPI(String pBaseUrl, String pUsername, String pPassword)
    throws FlexCheckedException
  {
    super(pBaseUrl, pUsername, pPassword);
  }

  public JSONArray findCredentialByName(String pCredentialName)
    throws FlexCheckedException
  {
    final String methodName = "findCredentialByName";
    LOGGER.entering(CLZ_NAM, methodName, pCredentialName);

    SearchCredentialByName sc = new SearchCredentialByName();
    sc.setName(pCredentialName);

    JSONObject jsonObject = FlexJsonUtils.getJSON(getClient().get(sc));
    JSONArray items = jsonObject.getJSONArray("items");
    
    LOGGER.exiting(CLZ_NAM, methodName, items);
    return items;
  }

  public JSONArray getLocalCredentialStore()
    throws FlexCheckedException
  {
    final String methodName = "getLocalCredentialStore";
    LOGGER.entering(CLZ_NAM, methodName);

    GetCredentialStoreByName gcs = new GetCredentialStoreByName();
    gcs.setName("Local");

    JSONObject jsonObject = FlexJsonUtils.getJSON(getClient().get(gcs));
    JSONArray items = jsonObject.getJSONArray("items");
    
    LOGGER.exiting(CLZ_NAM, methodName, items);
    return items;
  }

  public JSONObject getLocalCredentialStoreProvider(String pCredentialStoreDefId)
    throws FlexCheckedException
  {
    final String methodName = "getLocalCredentialStoreProvider";
    LOGGER.entering(CLZ_NAM, methodName, pCredentialStoreDefId);

    GetCredentialStoreProviderById gcsp = new GetCredentialStoreProviderById();
    gcsp.setId(pCredentialStoreDefId);

    JSONObject jsonObject = FlexJsonUtils.getJSON(getClient().get(gcsp));
    
    LOGGER.exiting(CLZ_NAM, methodName, jsonObject);
    return jsonObject;
  }

  public JSONObject createCredential(String pJSONRequestBody)
    throws FlexCheckedException
  {
    final String methodName = "createCredential";
    LOGGER.entering(CLZ_NAM, methodName, pJSONRequestBody);

    CreateCredential cc = new CreateCredential();
    cc.setJson(pJSONRequestBody);

    JSONObject jsonObject = FlexJsonUtils.getJSON(getClient().post(cc));

    LOGGER.exiting(CLZ_NAM, methodName, jsonObject);
    return jsonObject;
  }

  public void patchCredentialById(String pCredentialId, String pJSONRequestBody)
    throws FlexCheckedException
  {
    final String methodName = "patchCredentialById";
    LOGGER.entering(CLZ_NAM, methodName, new Object[]{pCredentialId, pJSONRequestBody});

    PatchCredentialById pc = new PatchCredentialById();
    pc.setId(pCredentialId);
    pc.setJson(pJSONRequestBody);

    JSONObject jsonObject = FlexJsonUtils.getJSON(getClient().patch(pc));

    LOGGER.exiting(CLZ_NAM, methodName, jsonObject);
  }
}