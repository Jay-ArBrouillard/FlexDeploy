package requests;

import requests.GetTargetGroupById;
import requests.GetTargetGroupByCode;
import requests.UpdateTargetGroupById;
import requests.PatchTargetById;

import flexagon.ff.common.core.exceptions.FlexCheckedException;
import flexagon.ff.common.core.rest.FlexRESTClientResponse;
import flexagon.ff.common.core.utils.FlexJsonUtils;
 
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.logging.*;

public class TargetAPI
  extends BaseAPI
{
  private static final String CLZ_NAM = TargetAPI.class.getName();
  private static final Logger LOGGER = Logger.getGlobal();

  public TargetAPI(String pBaseUrl, String pUsername, String pPassword)
    throws FlexCheckedException
  {
    super(pBaseUrl, pUsername, pPassword);
  }

  public JSONObject getTargetGroupById(String pTargetGroupId)
    throws FlexCheckedException
  {
    final String methodName = "getTargetGroupById";
    LOGGER.entering(CLZ_NAM, methodName, pTargetGroupId);

    GetTargetGroupById gt = new GetTargetGroupById();
    gt.setId(pTargetGroupId);

    JSONObject jsonObject = FlexJsonUtils.getJSON(getClient().get(gt));
    
    LOGGER.exiting(CLZ_NAM, methodName, jsonObject);
    return jsonObject;
  }

  public JSONArray findTargetGroupByCode(String pTargetGroupCode)
    throws FlexCheckedException
  {
    final String methodName = "findTargetGroupByCode";
    LOGGER.entering(CLZ_NAM, methodName, pTargetGroupCode);

    GetTargetGroupByCode gt = new GetTargetGroupByCode();
    gt.setCode(pTargetGroupCode);

    JSONObject jsonObject = FlexJsonUtils.getJSON(getClient().get(gt));
    JSONArray items = jsonObject.getJSONArray("items");
    
    LOGGER.exiting(CLZ_NAM, methodName, items);
    return items;
  }

  public void updateTargetGroupById(String pTargetGroupId, String pJSONRequestBody)
    throws FlexCheckedException
  {
    final String methodName = "updateTargetGroupById";
    LOGGER.entering(CLZ_NAM, methodName, new Object[]{pTargetGroupId, pJSONRequestBody});

    UpdateTargetGroupById ut = new UpdateTargetGroupById();
    ut.setId(pTargetGroupId);
    ut.setJson(pJSONRequestBody);

    JSONObject jsonObject = FlexJsonUtils.getJSON(getClient().put(ut));

    LOGGER.exiting(CLZ_NAM, methodName, jsonObject);
  }

  public void patchTargetById(String pEnvironmentId, String pTargetGroupId, String pJSONRequestBody)
    throws FlexCheckedException
  {
    final String methodName = "patchTargetById";
    LOGGER.entering(CLZ_NAM, methodName, new Object[]{pEnvironmentId, pTargetGroupId, pJSONRequestBody});

    PatchTargetById pt = new PatchTargetById();
    pt.setEnvironmentId(pEnvironmentId);
    pt.setTargetGroupId(pTargetGroupId);
    pt.setJson(pJSONRequestBody);

    JSONObject jsonObject = FlexJsonUtils.getJSON(getClient().patch(pt));

    LOGGER.exiting(CLZ_NAM, methodName, jsonObject);
  }
}