package com.suncorp.sintegration.buildlight.domain;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;

import com.suncorp.sintegration.buildlight.domain.Job.BuildStatus;


public class BuildParserJenkinsImpl implements BuildParser {

	@Override
    public List<Job> checkStatus(String url, String jobName) throws IOException {
        String status = getJobStatus(url, jobName);
        System.out.println("status = " + status);
        BuildStatus buildStatus = 
        (status.equals("blue_anime")) ?  BuildStatus.PROGRESS:
        (status.equals("red_anime")) ? BuildStatus.PROGRESS:
        (status.equals("red")) ? BuildStatus.FAILURE:
        (status.equals("yellow")) ? BuildStatus.FAILURE:
        (status.equals("blue")) ? BuildStatus.SUCCESS:
        BuildStatus.UNKNOWN;
        return Arrays.asList(new Job(jobName, buildStatus));
    }

    private String getJobStatus(String url, String jobName) throws IOException {
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod("http://"+url+"/api/xml");
        NameValuePair xpath= new NameValuePair("xpath","/hudson/job[name='"+jobName+"']/color/text()");
        method.setQueryString(new NameValuePair[]{xpath});
        client.executeMethod(method);
        return method.getResponseBodyAsString();
    }

}
