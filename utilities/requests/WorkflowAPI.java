package requests;

import requests.SearchWorkflowByName;
import requests.UpdateWorkflowById;

import flexagon.ff.common.core.exceptions.FlexCheckedException;
import flexagon.ff.common.core.rest.FlexRESTClientResponse;
 
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.logging.*;

public class WorkflowAPI
  extends BaseAPI
{
  private static final String CLZ_NAM = WorkflowAPI.class.getName();
  private static final Logger LOGGER = Logger.getGlobal();

  public WorkflowAPI(String pBaseUrl, String pUsername, String pPassword)
    throws FlexCheckedException
  {
    super(pBaseUrl, pUsername, pPassword);
  }

  public JSONArray findWorkflowByName(String pWorkflowName)
    throws FlexCheckedException
  {
    final String methodName = "findWorkflowByName";
    LOGGER.entering(CLZ_NAM, methodName, pWorkflowName);

    SearchWorkflowByName sw = new SearchWorkflowByName();
    sw.setWorkflowName(pWorkflowName);
    FlexRESTClientResponse response = getClient().get(sw);

    JSONArray jsonArray = new JSONArray(response.getResponseObject(String.class));
    
    LOGGER.exiting(CLZ_NAM, methodName, jsonArray);
    return jsonArray;
  }

  public void updateWorkflowById(String pWorkflowId, String pJSONRequestBody)
    throws FlexCheckedException
  {
    final String methodName = "updateWorkflowById";
    LOGGER.entering(CLZ_NAM, methodName, new Object[]{pWorkflowId, pJSONRequestBody});

    UpdateWorkflowById uw = new UpdateWorkflowById();
    uw.setId(pWorkflowId);
    uw.setJson(pJSONRequestBody);
    FlexRESTClientResponse response = getClient().put(uw);

    LOGGER.exiting(CLZ_NAM, methodName);
  }
}