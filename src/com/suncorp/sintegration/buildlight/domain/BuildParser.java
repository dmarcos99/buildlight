package com.suncorp.sintegration.buildlight.domain;

import java.io.IOException;
import java.util.List;


public interface BuildParser {

    public List<Job> checkStatus(String url, String jobName) throws IOException;
}
