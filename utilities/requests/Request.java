package requests;

import workflow.BulkWorkflowPropertiesAndValues;
import flexagon.ff.common.core.logging.FlexLogger;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;
import java.net.URLDecoder;

public abstract class Request
{
  private static final String CLZ_NAM = Request.class.getName();
  private static final Logger LOGGER = Logger.getGlobal();

  public Request()
  {
    super();
  }

  public abstract Map<String, Object> getQueryParams();

  public abstract String getResourceUri();

  /**
   * Only called for post/put/patch requests
   *
   * @return Entity object representing the body
   */
  public abstract Entity getBody();

  public abstract Map<String, Object> getHeaders();

  public String encodeValue(String value) 
  {
    final String methodName = "encodeValue";
    LOGGER.entering(CLZ_NAM, methodName, value);

    try 
    {
      LOGGER.exiting(CLZ_NAM, methodName);
      return URLEncoder.encode(value, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
    } 
    catch(UnsupportedEncodingException uee)
    {
      throw new RuntimeException(uee);
    }
  }

  public String decodeValue(String value)
  {
    final String methodName = "decodeValue";
    LOGGER.entering(CLZ_NAM, methodName, value);

    try 
    {
      LOGGER.exiting(CLZ_NAM, methodName);
      return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
    } 
    catch(UnsupportedEncodingException uee)
    {
      throw new RuntimeException(uee);
    }
  }
}