package requests;

import requests.SearchPropertySetByName;
import requests.UpdatePropertySetById;
import requests.SearchPropertyKeyDefinitionByName;
import requests.PatchPropertyKeyDefinitionById;
import requests.GetPropertyKeyDefinitionById;
import requests.CreatePropertyKeyDefinition;
import requests.SearchPropertyKeyDefinitionByParams;

import flexagon.ff.common.core.exceptions.FlexCheckedException;
import flexagon.ff.common.core.rest.FlexRESTClientResponse;
import flexagon.ff.common.core.utils.FlexJsonUtils;
 
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.logging.*;
import java.util.Map;

public class PropertyAPI
  extends BaseAPI
{
  private static final String CLZ_NAM = PropertyAPI.class.getName();
  private static final Logger LOGGER = Logger.getGlobal();

  public PropertyAPI(String pBaseUrl, String pUsername, String pPassword)
    throws FlexCheckedException
  {
    super(pBaseUrl, pUsername, pPassword);
  }

  public JSONArray findPropertySetByName(String pWorkflowName)
    throws FlexCheckedException
  {
    final String methodName = "findPropertySetByName";
    LOGGER.entering(CLZ_NAM, methodName, pWorkflowName);

    SearchPropertySetByName sps = new SearchPropertySetByName();
    sps.setName(pWorkflowName);
    FlexRESTClientResponse response = getClient().get(sps);

    JSONObject jsonObject = new JSONObject(response.getResponseObject(String.class));
    JSONArray items = jsonObject.getJSONArray("items");
    
    LOGGER.exiting(CLZ_NAM, methodName, items);
    return items;
  }

  public JSONObject searchPropertyKeyDefinitionByParams(Map<String, Object> pParams)
    throws FlexCheckedException
  {
    final String methodName = "searchPropertyKeyDefinitionByParams";
    LOGGER.entering(CLZ_NAM, methodName, pParams);

    SearchPropertyKeyDefinitionByParams spk = new SearchPropertyKeyDefinitionByParams();
    spk.setQueryParams(pParams);

    JSONObject jsonObject = FlexJsonUtils.getJSON(getClient().get(spk));
    
    LOGGER.exiting(CLZ_NAM, methodName, jsonObject);
    return jsonObject;
  }

  public JSONArray findPropertyKeyDefinitionByName(String pPropertyKeyName)
    throws FlexCheckedException
  {
    final String methodName = "findPropertyKeyDefinitionByName";
    LOGGER.entering(CLZ_NAM, methodName, pPropertyKeyName);

    SearchPropertyKeyDefinitionByName spkd = new SearchPropertyKeyDefinitionByName();
    spkd.setName(pPropertyKeyName);

    JSONObject jsonObject = FlexJsonUtils.getJSON(getClient().get(spkd));
    JSONArray items = jsonObject.getJSONArray("items");
    
    LOGGER.exiting(CLZ_NAM, methodName, items);
    return items;
  }

  public void updatePropertySetById(String pPropertySetId, String pJSONRequestBody)
    throws FlexCheckedException
  {
    final String methodName = "updatePropertySetById";
    LOGGER.entering(CLZ_NAM, methodName, new Object[]{pPropertySetId, pJSONRequestBody});

    UpdatePropertySetById ups = new UpdatePropertySetById();
    ups.setId(pPropertySetId);
    ups.setJson(pJSONRequestBody);
    FlexRESTClientResponse response = getClient().put(ups);

    LOGGER.exiting(CLZ_NAM, methodName);
  }

  public JSONObject getPropertyKeyDefinitionById(String pPropertyKeyDefinitionId)
    throws FlexCheckedException
  {
    final String methodName = "getPropertyKeyDefinitionById";
    LOGGER.entering(CLZ_NAM, methodName, pPropertyKeyDefinitionId);

    GetPropertyKeyDefinitionById gpkd = new GetPropertyKeyDefinitionById();
    gpkd.setId(pPropertyKeyDefinitionId);
    
    JSONObject jsonObject = FlexJsonUtils.getJSON(getClient().get(gpkd));

    LOGGER.exiting(CLZ_NAM, methodName, jsonObject);
    return jsonObject;
  }

  public void patchPropertyKeyDefinitionById(String pPropertyKeyDefinitionId, String pJSONRequestBody)
    throws FlexCheckedException
  {
    final String methodName = "patchPropertyKeyDefinitionById";
    LOGGER.entering(CLZ_NAM, methodName, new Object[]{pPropertyKeyDefinitionId, pJSONRequestBody});

    PatchPropertyKeyDefinitionById pkd = new PatchPropertyKeyDefinitionById();
    pkd.setId(pPropertyKeyDefinitionId);
    pkd.setJson(pJSONRequestBody);
    FlexRESTClientResponse response = getClient().put(pkd);

    LOGGER.exiting(CLZ_NAM, methodName);
  }

  public JSONObject createPropertyKeyDefinition(String pJSONRequestBody)
    throws FlexCheckedException
  {
    final String methodName = "createPropertyKeyDefinition";
    LOGGER.entering(CLZ_NAM, methodName, pJSONRequestBody);

    CreatePropertyKeyDefinition cpkd = new CreatePropertyKeyDefinition();
    cpkd.setJson(pJSONRequestBody);

    JSONObject jsonObject = FlexJsonUtils.getJSON(getClient().post(cpkd));

    LOGGER.exiting(CLZ_NAM, methodName, jsonObject);
    return jsonObject;
  }
}